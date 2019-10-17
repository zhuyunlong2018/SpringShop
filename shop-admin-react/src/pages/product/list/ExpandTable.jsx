import React, { Component } from 'react'
import { Table, Tag, Input, Icon } from 'antd';
import { Operator } from '@/library/antd';


/**
 * 商品属性编辑---属性扩展表
 */
export default class ExpandTable extends Component {

    state = {
        data: {},   //编辑属性行的数据
        index: -1, //编辑属性行在分组的index
        inputValue: "",
    }

    componentDidMount() {
        const { record, index } = this.props
        console.log(record)
        this.setState({ data: record, index })
    }

    /**
     * mutiple: 显示input，添加属性的默认值分类
     * @param {*} record 
     * @param {*} paramsIndex 
     */
    showTagInput(record, paramsIndex) {
        record.inputVisible = true
        const { index, handleChangeGroupAttributes } = this.props

        //先清空input的值
        this.setState({ inputValue: "" }, () => {
            //修改状态显示input
            handleChangeGroupAttributes(record, index, paramsIndex, () => this.input.focus())
        })

    }

    /**
     * mutiple: 修改input的值
     */
    handleTagInputChange = e => {
        this.setState({ inputValue: e.target.value });
    };

    /**
     * mutiple: 多选项的属性值，input失去焦点时，为确认添加选项
     */
    handleTagInputConfirm(record, paramsIndex) {
        const { inputValue } = this.state
        let {defaultValue} = record

        if (inputValue !== "" && defaultValue.indexOf(inputValue) !== -1) {
            //input有值却已存在属性选项中，
            return false
        }
        if (inputValue !== "") {
            //值不为空才进行添加
            defaultValue = [...defaultValue, inputValue]
        }
        //选项不存在才进行添加
        const { index, handleChangeGroupAttributes } = this.props

        //修改状态隐藏input,更新该属性值
        const newRecord = {...record, inputVisible: false, defaultValue }
        handleChangeGroupAttributes(newRecord, index, paramsIndex, () => {
            //清空input的值
            this.setState({ inputValue: "" })
        })
    };

    /**
     * mutiple: 移除多项属性组的某个选项值
     */
    handleTagClose(tagIndex, record, paramsIndex) {
        let {defaultValue} = record
        defaultValue.splice(tagIndex, 1)
        const { index, handleChangeGroupAttributes } = this.props
        handleChangeGroupAttributes({...record, defaultValue}, index, paramsIndex)
    }

    /**
     * single: 修改单项属性值
     */
    handleInputChange = (e, record, index) => {
        const value = e.target.value
        const { data: {params}} = this.state
        record.defaultValue = value
        params.splice(index, 1, record)
        this.setState({data: {...this.state.data, params}})
    }

    /**
     * single: 单项属性值，input框失去焦点，赋值给父组件state
     */
    handleInputConfirm(record, paramsIndex) {
        const { index, handleChangeGroupAttributes } = this.props
        handleChangeGroupAttributes(record, index, paramsIndex)
    }

    /**
     * mutiple: 生成tag标签，表示一个属性有多个可选值
     */
    tagsForMap(tag, index, record, paramsIndex) {
        return (
            <span key={tag+index} style={{ display: 'inline-block' }}>
                <Tag closable
                    onClose={e => {
                        e.preventDefault();
                        this.handleTagClose(index, record, paramsIndex);
                    }}
                >{tag}</Tag>
            </span>
        );
    };

    /**
     * 删除某一个属性值
     */
    handleRemoveParams(paramsIndex) {
        const {data: {params}} = this.state
        params.splice(paramsIndex, 1)
        const { index, handleRemoveParams } = this.props
        const newRecord = {...this.state.data, params}
        handleRemoveParams(newRecord, index)
    }


    columns = [
        { title: '属性名', width: 50, dataIndex: 'key', key: 'key' },
        {
            title: '属性值', width: 200, dataIndex: 'value', key: 'value',
            render: (text, record, index) => {
                const { type, inputVisible, defaultValue } = record
                if (type === "single") {
                    return <Input value={defaultValue} 
                                onChange={e => this.handleInputChange(e, record, index)}
                                onBlur={() => this.handleInputConfirm(record, index)}
                                onPressEnter={() => this.handleInputConfirm(record, index)}
                            />
                }
                return (
                    <div>
                        {defaultValue.map((tag, i) => this.tagsForMap(tag, i, record, index))}
                        {inputVisible && (
                            <Input
                                ref={input => this.input = input}
                                type="text"
                                size="small"
                                value={this.state.inputValue}
                                style={{ width: 78 }}
                                onChange={this.handleTagInputChange}
                                onBlur={() => this.handleTagInputConfirm(record, index)}
                                onPressEnter={() => this.handleTagInputConfirm(record, index)}
                            />
                        )}
                        {!inputVisible && (
                            <Tag onClick={() => this.showTagInput(record, index)} 
                                style={{ background: '#fff', borderStyle: 'dashed' }}>
                                <Icon type="plus" /> add
                            </Tag>
                        )}
                    </div>
                )
            }
        },
        {
            title: '操作',
            width: 20,
            dataIndex: 'operator',
            render: (text, record, index) => {
                const { key } = record;
                const deleteItem = {
                    label: '删除',
                    confirm: {
                        title: `您确定要删除"${key || ''}"吗？`,
                        onConfirm: () => this.handleRemoveParams(index),
                    },
                };
                const items = [deleteItem]
                return <Operator items={items} />
            },
        },
    ]


    //商品属性表格，扩展表
    render() {
        const { data: { params } } = this.state

        return <Table
            pagination={false}
            columns={this.columns}
            dataSource={params}
            rowKey="id"
        />
    };

}