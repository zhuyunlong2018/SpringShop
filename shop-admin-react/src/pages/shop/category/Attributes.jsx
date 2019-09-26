
import React, { Component } from 'react';
import uuid from 'uuid/v4';
import { TableEditable, rowDraggable, Operator } from '@/library/antd';
import { Modal, Collapse, Input, Popconfirm } from 'antd';

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

export default class Attributes extends Component {
    state = {
        dataSource: [
            {
                id: "111111111",
                group: "基本属性",
                params: []
            },
        ],
        loading: false,
        inputGroup: "",    //添加属性组的输入框
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
                        { required: true, message: '请输入默认值!' }
                    ],
                },
            }
        },
        {
            title: '类型', dataIndex: 'age', key: 'age',
            props: {
                type: 'input',
                placeholder: '请输入年龄',
                onPressEnter: e => {
                    const currentTr = e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
                    const nextTr = currentTr.nextSibling;
                    if (!nextTr) {
                        nextTr.getElementsByTagName('input')[0].focus();
                    }
                },
                decorator: {
                    rules: [
                        { required: true, message: '请输入年龄!' }
                    ],
                },
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

    componentDidUpdate(prevProps) {
        const { formData:{params}, visible } = this.props;

        // 打开弹框
        if (!prevProps.visible && visible && params) {
            
            // 填充数据
            this.setState({ dataSource: JSON.parse(params.params) })
        }
    }


    /**
     * 处理添加参数组
     */
    handleAddGroup(value) {
        console.log(value)
        if (value && value !== "") {
            const group = { id: uuid(), group: value, params: [] }
            this.setState({ dataSource: [...this.state.dataSource, group], inputGroup: ""})
        }
    }

    /**
     * 处理取消操作
     */
    handleCancel = () => {
        const { onCancel } = this.props;
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


    preventDefault(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    render() {
        const { dataSource, loading, inputGroup } = this.state;
        const { visible, } = this.props
        let defaultActiveKey = []
        if (dataSource.length > 0) {
            defaultActiveKey = [dataSource[0].id]
        }
        const title = (
            <div styleName="attr-header">
                <span>编辑属性</span>
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
            >
                <Collapse defaultActiveKey={defaultActiveKey} accordion={true}>
                    {
                        dataSource.map((data, index) => {
                            const { id, group, params } = data
                            return (
                                <Panel key={id}
                                    header={(
                                        <span>
                                            {group}
                                            <Popconfirm title="您确认移除该组吗？" onCancel={this.preventDefault}
                                                onConfirm={e => {
                                                    this.preventDefault(e)
                                                    const dataSource = [...this.state.dataSource]
                                                    dataSource.splice(index, 1)
                                                    this.setState({dataSource})
                                                }}>
                                                <a style={{ marginLeft: 16 }} onClick={this.preventDefault}>移除该组</a>
                                            </Popconfirm>
                                        </span>

                                    )}
                                >
                                    <Table
                                        size="small"
                                        showAddButton
                                        rowKey="id"
                                        id={id}
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
