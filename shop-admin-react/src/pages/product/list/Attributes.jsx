import React, { Component } from 'react'
import { Collapse, Modal, Table, Popconfirm, Form } from 'antd';
import ExpandTable from './ExpandTable'
import { Operator, FormElement } from '@/library/antd';
import uuid from 'uuid/v4';

/**
 * 商品属性编辑页
 */

const { Panel } = Collapse;

@Form.create()
export default class Attributes extends Component {

    state = {
        categoryAttributes: [], //从分类继承过来的属性组
        modalVisible: false,   //模态框显示隐藏
        modalTitle: "",    //模态框标题
        addIndex: -1,   //添加属性参数对应的属性组的index
        skuList: [],    //商品sku列表
    }

    componentDidMount() {
        const { data, onRef } = this.props
        if (onRef) onRef(this)
        console.log(data)
        //todo 将商品的attributes和skuList设置到state中
    }

    /**
     * 属性编辑完成处理
     */
    handleComplete() {
        const { skuList } = this.state
        const { data: { id } } = this.props
        const skuData = skuList.map(item => {
            let description = ""
            for (const attr in item) {
                if (attr !== "id") {
                    description += item[attr] + " "
                }
            }
            const attributes = JSON.stringify(item)
            return { productId: id, description, attributes }
        })

        return skuData
    }

    /**
     * 从分类属性中继承到商品
     */
    extendsAttributes() {
        const { data: { categoryAttributes: { params } } } = this.props
        if (params) {
            let categoryAttributes = JSON.parse(params)
            if (categoryAttributes instanceof Array) {
                categoryAttributes = categoryAttributes.map(group => {
                    const { params } = group
                    return {
                        ...group, params: params.map(param => {
                            let { defaultValue, type } = param
                            if (type === "mutiple") {
                                if (defaultValue === "") {
                                    defaultValue = []
                                } else {
                                    //属性值为多选，且存在默认值，将默认值用逗号分隔解析为数组
                                    defaultValue = defaultValue.split(",")
                                }
                                //添加inputVisible属性
                                param.inputVisible = false
                            }
                            return { ...param, defaultValue }
                        })
                    }
                })
            }
            this.setState({ categoryAttributes })
        }
    }

    /**
     * 移除分组属性
     * @param {Number}} index 
     */
    handleRemoveGroup(index) {
        const categoryAttributes = [...this.state.categoryAttributes]
        categoryAttributes.splice(index, 1)
        this.setState({ categoryAttributes })
    }

    /**
     * 添加某个分组的属性
     * @param {*} index 
     */
    handleAddParamsNode(index) {
        this.setState({ modalVisible: true, addIndex: index, modalTitle: "添加属性到分组" }, () => {
            const { form: { setFieldsValue } } = this.props;
            setFieldsValue({ title: "", type: "single" })
        })
    }

    /**
     * 模态框确定操作，添加属性或属性组
     */
    handleAddSave() {
        const { form: { validateFieldsAndScroll } } = this.props;
        const { addIndex, categoryAttributes } = this.state
        //表单验证
        validateFieldsAndScroll((err, values) => {
            if (!err) {
                const id = uuid()
                if (addIndex > -1) {
                    const { title: key, type } = values
                    //添加属性到分组
                    let params = { id, key, type, defaultValue: "" }
                    if (type === "mutiple") {
                        params = { ...params, inputVisible: false, defaultValue: [] }
                    }
                    const group = { ...categoryAttributes[addIndex] }
                    group.params.push(params)
                    categoryAttributes[addIndex] = group
                    this.setState({ categoryAttributes, modalVisible: false })
                } else {
                    //添加属性组
                    const { title: group } = values
                    this.setState({
                        categoryAttributes: [...categoryAttributes, { id, group, params: [] }],
                        modalVisible: false
                    })
                }
            }
        });
    }

    FormElement = (props) => <FormElement form={this.props.form} {...props} labelWidth={100} />;

    //属性组表单字段
    columns = [
        { title: '分组名', dataIndex: 'group', key: 'group' },
        {
            title: '操作',
            width: 200,
            dataIndex: 'operator',
            render: (text, record, index) => {
                const { key } = record;
                const items = [
                    {
                        label: '添加子属性',
                        onClick: () => this.handleAddParamsNode(index)
                    },
                    {
                        label: '删除',
                        confirm: {
                            title: `您确定要删除"${key || ''}"吗？`,
                            onConfirm: () => this.handleRemoveGroup(index),
                        },
                    }
                ]
                return <Operator items={items} />
            },
        },
    ];

    //商品sku表单字段
    skuColumns = [

    ]

    /**
     * 处理属性组修改
     */
    handleChangeGroupAttributes = (record, groupIndex, paramsIndex, callback) => {
        //拷贝
        const categoryAttributes = [...this.state.categoryAttributes]
        //获取分组
        const group = categoryAttributes[groupIndex]
        //替换分组中的参数组
        group.params.splice(paramsIndex, 1, record)
        //替换修改后的分组
        categoryAttributes.splice(groupIndex, 1, group)
        this.setState({ categoryAttributes }, () => {
            if (callback instanceof Function) callback()
        })
    }

    /**
     * 移除属性组中的某个属性
     */
    handleRemoveParams = (record, index, callback) => {
        //拷贝
        const categoryAttributes = [...this.state.categoryAttributes]
        categoryAttributes.splice(index, 1, record)
        this.setState({ categoryAttributes }, () => {
            if (callback instanceof Function) callback()
        })
    }

    /**
     * 添加属性分组操作
     * @param {*} e 
     */
    handleAddGroup() {
        this.setState({ modalVisible: true, addIndex: -1, modalTitle: "添加属性组" }, () => {
            const { form: { setFieldsValue } } = this.props;
            setFieldsValue({ title: "" })
        })
    }

    /**
     * 生成商品的sku列表，生成策略：从attributes中抽取mutiple(多个选项值)的属性组合
     */
    generatorSkuList() {
        const { categoryAttributes } = this.state
        let skuAttributes = []
        let skuColumns = []
        categoryAttributes.forEach(e => {
            const { params } = e
            //从属性中提取多选项类型的属性，获得类二维数组[attr:[...defaultValue], ...]
            skuAttributes = skuAttributes.concat(
                //过滤掉非多项属性
                params.filter(item => item.type === "mutiple").map(item => {
                    const { defaultValue, key } = item
                    skuColumns.push({
                        title: key,
                        dataIndex: key,
                        key
                    })
                    //返回用于计算笛卡尔积的二维数组
                    return defaultValue.map(value => {
                        const res = {}
                        res[key] = value
                        return res
                    })
                })
            )
        });
        //进行生成table表的列
        this.generatorSkuTableColumns(skuColumns)

        //通过计算笛卡尔积得到所有sku的属性组合
        const skuList = this.calcDescartes(skuAttributes).map(item => ({ ...item, id: uuid() }))
        this.setState({ skuList })
    }

    /**
     * 生成sku的table列
     */
    generatorSkuTableColumns(skuColumns) {
        const otherColumns = [
            {
                title: 'sku编号',
                dataIndex: 'id',
            },
            {
                title: '操作',
                dataIndex: 'operator',
                render: (text, record, index) => {
                    const items = [
                        {
                            label: '删除',
                            confirm: {
                                title: `您确定要删除该sku商品吗？`,
                                onConfirm: () => {
                                    const skuList = [...this.state.skuList]
                                    skuList.splice(index, 1)
                                    this.setState({ skuList })
                                },
                            },
                        }
                    ]
                    return <Operator items={items} />
                },
            }
        ]

        //整理成表单形式
        this.skuColumns = skuColumns.concat(otherColumns)
    }

    /**
     * 笛卡尔积算出商品多种属性组合
     * @param {}} array 
     */
    calcDescartes(array) {
        if (array.length < 2) return array[0] || [];
        return array.reduce((col, set) => {
            let res = []
            col.forEach(c => {
                set.forEach(s => {
                    var t = { ...c, ...s }
                    res.push(t)
                })
            });
            return res;
        });
    }

    /**
     * 阻止冒泡
     * @param {*} e 
     */
    preventDefault(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    render() {
        const { categoryAttributes, modalVisible, addIndex, modalTitle, skuList } = this.state
        const FormElement = this.FormElement;
        return (
            <div>
                <Collapse bordered={false} defaultActiveKey={['1']}>
                    <Panel key="1"
                        header={(
                            <div>
                                商品属性组
                                <a style={{ marginLeft: 16 }} onClick={e => {
                                    this.preventDefault(e)
                                    this.handleAddGroup()
                                }}>添加属性组</a>
                                <Popconfirm title="继承分类属性将移除下方已设置商品属性，确认要继承？"
                                    onCancel={this.preventDefault}
                                    onConfirm={e => {
                                        this.preventDefault(e)
                                        this.extendsAttributes()
                                    }}>
                                    <a style={{ marginLeft: 16 }} onClick={this.preventDefault}>继承分类的属性</a>
                                </Popconfirm>

                            </div>
                        )}>
                        <Table
                            className="components-table-demo-nested"
                            pagination={false}
                            showHeader={false}
                            columns={this.columns}
                            rowKey="id"
                            size="small"
                            expandedRowRender={(record, index) => <ExpandTable
                                record={record}
                                index={index}
                                handleChangeGroupAttributes={this.handleChangeGroupAttributes}
                                handleRemoveParams={this.handleRemoveParams}
                            />}
                            dataSource={categoryAttributes}
                        />
                    </Panel>
                    <Panel key="2"
                        header={(
                            <div>
                                商品SKU列表
                                <Popconfirm title="从属性生成会清空现有sku列表，确认要生成？"
                                    onCancel={this.preventDefault}
                                    onConfirm={e => {
                                        this.preventDefault(e)
                                        this.generatorSkuList()
                                    }}>
                                    <a style={{ marginLeft: 16 }} onClick={this.preventDefault}>从属性中生成sku</a>
                                </Popconfirm>
                            </div>
                        )}>
                        <Table
                            className="components-table-demo-nested"
                            pagination={false}
                            columns={this.skuColumns}
                            rowKey="id"
                            size="small"
                            dataSource={skuList}
                        />
                    </Panel>
                </Collapse>
                <Modal
                    title={modalTitle}
                    visible={modalVisible}
                    forceRender={true}
                    onOk={() => this.handleAddSave()}
                    onCancel={() => this.setState({ modalVisible: false })}
                    okText="确认"
                    cancelText="取消"
                >
                    <Form>
                        <FormElement
                            label="属性名"
                            type="input"
                            field="title"
                            decorator={{
                                rules: [
                                    { required: true, message: '属性名不能为空！' },
                                    { max: 100, message: '最多100个字符！' },
                                ],
                            }}
                        />
                        {addIndex > -1 ?
                            <FormElement
                                label="属性类型"
                                type="select"
                                field="type"
                                optionFilterProp="label"
                                options={[
                                    { label: "单一值", value: 'single' },
                                    { label: "多选值", value: 'mutiple' },
                                ]}
                                decorator={{
                                    rules: [
                                        { required: true, message: '属性类型不能为空！' },
                                    ],
                                }}
                            />
                            : null}
                    </Form>
                </Modal>
            </div>
        )
    }
}