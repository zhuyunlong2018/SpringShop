
import React, { Component } from 'react';
import uuid from 'uuid/v4';
import { TableEditable, rowDraggable, Operator } from '@/library/antd';
import { Modal, Collapse, Input, Popconfirm, Tooltip, Icon, message } from 'antd';
import { saveAttributes } from '@/api/category'
import './style.less'

const { Panel } = Collapse;
const Table = rowDraggable(TableEditable);
const { Search } = Input;

/**
 * 分类属性编辑框
 */

function focusAndSelect(e, index) {
    // 获取父级tr
    let currentTr = e.target;
    while (currentTr && currentTr.tagName !== 'TR') {
        currentTr = currentTr.parentNode;
    }
    const nextInput = currentTr.getElementsByTagName('input')[index];
    if (nextInput) {
        nextInput.focus();
        nextInput.select();
    }
}

const typeOptions = [
    { label: '单一属性', value: 'single' },
    { label: '复合属性', value: 'mutiple' },
];

export default class Attributes extends Component {

    state = {
        dataSource: [],
        loading: false,
        inputGroup: "",    //添加属性组的输入框
        activeKey: [],   //默认展开项
        submitForm: {},     //提交后端的form
    };
    columns = [
        {
            title: '属性名', dataIndex: 'key', key: 'name',
            props: {
                type: 'input',
                placeholder: '请输入属性名称',
                onPressEnter: (e) => focusAndSelect(e, 1),
                decorator: {
                    rules: [
                        { required: true, message: '请输入属性名称!' }
                    ],
                },
            }
        },
        {
            title: '默认值', dataIndex: 'defaultValue', key: 'defaultValue',
            props: {
                type: 'input',
                placeholder: '请输入默认值',
                onPressEnter: (e) => focusAndSelect(e, 2),
                decorator: {
                    rules: [
                        // { required: true, message: '请输入默认值!' }
                    ],
                },
            }
        },
        {
            title: '类型', dataIndex: 'type', key: 'type',
            width: '180px',
            props: {
                type: 'select',
                placeholder: '请选择属性类型',
                onPressEnter: e => {
                    const currentTr = e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
                    const nextTr = currentTr.nextSibling;
                    if (!nextTr) {
                        nextTr.getElementsByTagName('input')[0].focus();
                    }
                },
                decorator: {
                    rules: [
                        { required: true, message: '请选择属性类型!' }
                    ],
                },
                getValue: e => e,
                options: typeOptions,
            }
        },
        {
            title: '操作',
            width: '50',
            dataIndex: 'operator',
            render: (text, record) => {
                const { id, key } = record;
                const deleteItem = {
                    label: '删除',
                    confirm: {
                        title: `您确定要删除"${key || ''}"吗？`,
                        onConfirm: () => {
                            const dataSource = [...this.state.dataSource]
                            dataSource.forEach((e, i) => {
                                const newValue = e.params.filter(item => item.id !== id);
                                if (e.params.length !== newValue.length) {
                                    dataSource.splice(i, 1, { ...e, params: newValue })
                                    this.setState({ dataSource })
                                }
                            });
                        },
                    },
                };
                const items = [
                    deleteItem,
                ];
                return <Operator items={items} />;
            },
        },
    ];

    componentWillMount() {
        this.resetDataSource()
    }

    componentDidUpdate(prevProps) {
        const { formData, visible } = this.props;

        // 打开弹框
        if (!prevProps.visible && visible) {
            const { id, params } = formData
            let submitForm = { categoryId: id }
            if (params) {
                const attr = JSON.parse(params.params)
                // 填充数据
                const defaultKey = attr.length > 0 ?[attr[0].id] : [];
                this.setState({ dataSource: JSON.parse(params.params), activeKey: defaultKey })
                submitForm.id = params.id
            }
            this.setState({ submitForm })
        }
    }


    /**
     * 处理添加参数组
     */
    handleAddGroup(value) {
        if (value && value !== "") {
            const group = { id: uuid(), group: value, params: [] }
            this.setState({ dataSource: [...this.state.dataSource, group], inputGroup: "" })
        }
    }

    /**
     * 重置属性数据
     */
    resetDataSource() {
        const id = uuid()
        const dataSource = [
            {
                id,
                group: "基本属性",
                params: []
            },
        ];
        this.setState({ dataSource, activeKey: [id] })
    }

    /**
     * 处理取消操作
     */
    handleCancel = () => {
        const { onCancel } = this.props;
        this.resetDataSource()
        if (onCancel) onCancel();
    };

    /**
     * 子属性排序处理
     */
    handleSubSortEnd = ({ oldIndex, newIndex, index }) => {
        const dataSource = [...this.state.dataSource];
        const { params } = dataSource[index]
        params.splice(newIndex, 0, params.splice(oldIndex, 1)[0]);
        dataSource.splice(index, 1, { ...dataSource[index], params })
        this.setState({ dataSource })
    };

    /**
     * 处理提交给后台保存属性
     */
    handleSubmit = () => {
        const { dataSource, submitForm } = this.state
        let hasErr = false
        const transData = dataSource.map((item, index) => {
            const form = this["form" + item.id]
            form && form.validateFieldsAndScroll((err, values) => {
                if (err) {
                    hasErr = true
                }
            });
            const newParams = item.params.map(e => {
                const { id, key, type, defaultValue } = e
                return { id, key, type, defaultValue }
            })
            return {
                ...item,
                params: newParams
            }
        });
        if (hasErr) return message.error('有必填项为空，请检查');
        this.setState({ loading: true })
        saveAttributes({ ...submitForm, params: JSON.stringify(transData) }).then(res => {
            this.props.onOk(submitForm.categoryId, { ...this.props.formData, params: res })
        }).finally(() => this.setState({ loading: false }))
    }

    preventDefault(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    render() {
        const { dataSource, loading, inputGroup, activeKey } = this.state;
        const { visible, formData } = this.props
        
        const title = (
            <div styleName="attr-header">
                <span>{formData.title} -- 属性编辑</span>
                <div styleName='input'>
                    <Search
                        value={inputGroup}
                        onChange={e => { this.setState({ inputGroup: e.target.value }) }}
                        placeholder="请输入分组名称"
                        enterButton="添加分组"
                        onSearch={value => this.handleAddGroup(value)}
                    />
                </div>
            </div>
        )
        return (
            <Modal
                destroyOnClose
                confirmLoading={loading}
                visible={visible}
                width={800}
                title={title}
                onCancel={this.handleCancel}
                onOk={this.handleSubmit}
            >
                <Collapse activeKey={activeKey} accordion={true} bordered={false}
                    onChange={key => this.setState({ activeKey: [key] })}>
                    {
                        dataSource.map((data, index) => {
                            const { id, group, params } = data
                            return (
                                <Panel key={id}
                                    forceRender={false}
                                    header={(
                                        <div>
                                            {group}
                                            <Tooltip title="属性默认值多项时用逗号隔开">
                                                <Icon type="info-circle" />
                                            </Tooltip>
                                            <Popconfirm title="您确认移除该组吗？" onCancel={this.preventDefault}
                                                onConfirm={e => {
                                                    this.preventDefault(e)
                                                    const dataSource = [...this.state.dataSource]
                                                    dataSource.splice(index, 1)
                                                    this.setState({ dataSource })
                                                }}>
                                                <a style={{ marginLeft: 16 }} onClick={this.preventDefault}>移除该组</a>
                                            </Popconfirm>
                                        </div>

                                    )}
                                >
                                    <Table
                                        size="small"
                                        showAddButton
                                        rowKey="id"
                                        id={id}
                                        formRef={form => this["form" + id] = form}
                                        title={this.renderTableTitle}
                                        onChange={params => {
                                            const newData = { ...data, params }
                                            const source = [...dataSource]
                                            source.splice(index, 1, newData)
                                            this.setState({ dataSource: source })
                                        }}
                                        columns={this.columns}
                                        dataSource={params}
                                        helperClass="generator-helper-element"
                                        onSortEnd={({ oldIndex, newIndex }) => this.handleSubSortEnd({ oldIndex, newIndex, index })}
                                    />
                                </Panel>
                            )
                        })
                    }
                </Collapse>

            </Modal>
        );
    }
}
