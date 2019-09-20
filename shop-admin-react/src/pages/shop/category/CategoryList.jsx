import React, { Component } from 'react';
import { Table, Avatar } from 'antd';
import {
    QueryBar,
    QueryItem,
    Pagination,
    Operator,
    ToolBar,
} from "@/library/antd";
import PageContent from '@/layouts/page-content';
import config from '@/commons/config-hoc';
import { hasPermission } from '@/commons';
import CategoryEdit from './CategoryEdit';
import { list, del, listByLevels } from '@/api/category';
import { makeChildren } from '@/library/utils/tree-utils'

@config({
    path: '/categories',
})
export default class CategoryList extends Component {
    state = {
        loading: false,
        dataSource: [],//列表数据
        total: 0, //数据总条数
        pageSize: 10, //每页数量
        pageNum: 1, //当前页码
        collapsed: true,    // 是否收起
        params: {},
        formData: {},//添加、编辑表单数据
        visible: false,
        levels: {}, //类目级别
        selectOptions: [
            { value: 1, label: '一级类目' },
            { value: 2, label: '二级类目' },
            { value: 3, label: '三级类目' }
        ],
        firstAndSecondTree: [],
    };

    // TODO 顶部工具条
    toolItems = [
        {
            type: 'primary',
            text: '添加',
            icon: 'plus',
            visible: hasPermission('admin:categories:add'),
            onClick: () => {
                this.handleAdd()
            },
        },
    ];

    //顶部搜索栏目
    queryItems = [
        [
            {
                collapsedShow: true, // 收起时显示
                type: 'select',
                field: 'level',
                label: '类目级别',
                placeholder: '请选择级别',
                itemStyle: { flex: '0 0 200px' }, // 固定宽度
            },
            {
                collapsedShow: true, // 收起时显示
                type: 'input',
                field: 'title',
                label: '检索名称',
                placeholder: '请输入名称',
                itemStyle: { flex: '0 0 200px' }, // 固定宽度
            },
        ],
    ];

    //列表数据
    columns = [
        // {title: '父类目', dataIndex: 'pid'},
        { title: '类目名称', dataIndex: 'title' },
        { title: '描述', dataIndex: 'description' },
        { title: '图片', dataIndex: 'img', render: (value) => <Avatar  shape="square" src={value} onClick={() => {

            this.props.action.global.showPreviewVisible(value, '')
        }} /> },
        { title: '排序', dataIndex: 'sortOrder' },
        {
            title: '级别', dataIndex: 'level',
            render: (value) => {
                return this.state.levels[value]
            }
        },
        {
            title: '状态', dataIndex: 'status',
            render: (value) => {
                if (value === 2) return <span>删除</span>;
                return "正常";
            }
        },
        {
            title: '操作',
            key: 'operator',
            render: (record) => {
                const { id, pid } = record;
                const items = [
                    {
                        label: '修改',
                        visible: hasPermission('admin:categories:edit'),
                        onClick: () => {
                            this.handleEdit(record);
                        },
                    },
                    {
                        label: '删除',
                        color: 'red',
                        visible: hasPermission('admin:categories:del'),
                        confirm: {
                            title: `您确定要删除“${pid}”？`,
                            onConfirm: () => { this.handleDel(id) },
                        },
                    },
                ];

                return (<Operator items={items} />);
            },
        },
    ];

    componentDidMount() {
        let stateLevels = {};
        this.state.selectOptions.forEach(e => {
            stateLevels[e.value] = e.label
        });
        this.setState({ levels: stateLevels });

        this.handleSearch();

        this.fetchFirstAndSecondCategories()
    }

    /**
     * 获取列表
     */
    handleSearch = () => {
        const { params, pageNum, pageSize } = this.state;

        this.setState({ loading: true });
        list({ ...params, pageNum, pageSize })
            .then(res => {
                if (res) {
                    const { records: dataSource, total } = res;
                    this.setState({
                        dataSource,
                        total,
                    });
                }
            })
            .finally(() => this.setState({ loading: false }));
    };

    /**
     * 获取所有第一级和第二级类目列表并整理为treeNode
     */
    fetchFirstAndSecondCategories() {
        listByLevels({levels: [1,2]}).then(res => {
            const nodes = makeChildren(res, 0, (data) => {
                data.key = data.id
                data.value = data.id
            });
            nodes.unshift({
                key: "topper",
                value: '0',
                title: "顶级类目",
                level: 1,
            })
            this.setState({firstAndSecondTree: nodes})
        })
    }

    /**
     * 处理queryItem的options
     */
    fetchOptions = () => {
        return Promise.resolve({ level: this.state.selectOptions })
    };

    /**
     * 处理删除数据
     */
    handleDel(id) {
        this.setState({ loading: true });
        del({ id })
            .then(() => {
                let dataSource = [...this.state.dataSource]
                dataSource.forEach((e, i) => {
                    if (id === e.id) {
                        dataSource.splice(i, 1)
                    }
                });
                this.setState({ dataSource })
            })
            .finally(() => this.setState({ loading: false }));
    }

    /**
     * 处理添加
     */
    handleAdd = () => {
        this.setState({ formData: { level: 1, status: 1 }, visible: true });
    };

    /**
     * 处理编辑
     */
    handleEdit = (formData) => {
        this.setState({ formData, visible: true });
    };

    /**
     * 添加、编辑成功后回调
     */
    handleOk(id, data) {
        let dataSource = []
        if (id) {
            dataSource = [...this.state.dataSource]
            dataSource.forEach((e, i) => {
                if (e.id === id) {
                    dataSource[i] = data
                }
            });
        } else {
            dataSource = [data, ...this.state.dataSource]
        }
        this.setState({ visible: false, dataSource })
    }

    render() {
        const {
            loading,
            dataSource,
            collapsed,
            total,
            pageNum,
            pageSize,
            visible,
            formData,
            firstAndSecondTree,
        } = this.state;

        return (
            <PageContent loading={loading}>
                <QueryBar
                    showCollapsed
                    collapsed={collapsed}
                    onCollapsedChange={collapsed => this.setState({ collapsed })}
                >
                    <QueryItem
                        collapsed={collapsed}
                        loadOptions={this.fetchOptions}
                        items={this.queryItems}
                        onSubmit={params => this.setState({ params, pageNum: 1 }, this.handleSearch)}
                    // extra={<Button type="primary" icon="user-add" onClick={this.handleAdd}>添加用户</Button>}
                    />
                </QueryBar>

                <ToolBar items={this.toolItems} />
                <Table
                    columns={this.columns}
                    dataSource={dataSource}
                    rowKey="id"
                    pagination={false}
                />

                <Pagination
                    total={total}
                    pageNum={pageNum}
                    pageSize={pageSize}
                    onPageNumChange={pageNum => this.setState({ pageNum }, this.handleSearch)}
                    onPageSizeChange={pageSize => this.setState({ pageSize, pageNum: 1 }, this.handleSearch)}
                />

                <CategoryEdit
                    formData={formData}
                    treeNode={firstAndSecondTree}
                    visible={visible}
                    onOk={this.handleOk.bind(this)}
                    onCancel={() => this.setState({ visible: false })}
                />
            </PageContent>
        );
    }
}
