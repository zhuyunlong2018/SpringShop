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
import { hasPermission, fileSrc } from '@/commons';
import ProductEdit from './ProductEdit';
import { list, del } from '@/api/product'

const statusOptions = [
    { label: "正常", value: 1 },
    { label: "下架", value: 2 },
    { label: "删除", value: 3 },
]

@config({
    path: '/products/list',
})
export default class ProductList extends Component {
    state = {
        loading: false,
        dataSource: [],//列表数据
        total: 0, //数据总条数
        pageSize: 10, //每页数量
        pageNum: 1, //当前页码
        params: {},
        formData: {},//添加、编辑表单数据
        visible: false,
    };

    // TODO 查询条件
    queryItems = [
        [
            {
                collapsedShow: true, // 收起时显示
                type: 'input',
                field: 'title',
                label: '商品标题',
                placeholder: '请选择级别',
                itemStyle: { flex: '0 0 200px' }, // 固定宽度
            },
            {
                collapsedShow: true, // 收起时显示
                type: 'number',
                field: 'categoryId',
                label: '所属类目',
                placeholder: '请输入类目名称',
                itemStyle: { flex: '0 0 200px' }, // 固定宽度
            },
        ],
        [
            {
                type: 'number',
                field: 'brandId',
                label: '所属品牌',
            },
            {
                type: 'select',
                field: 'status',
                label: '商品状态',
            },
        ],

    ];

    // TODO 顶部工具条
    toolItems = [
        {
            type: 'primary',
            text: '添加',
            icon: 'plus',
            visible: hasPermission('admin:products:add'),
            onClick: this.handleAdd,
        },
    ];

    //列表数据
    columns = [
        { title: '商品标题', dataIndex: 'title', width: '40%' },
        { title: '价格区间', dataIndex: 'priceRange' },
        {
            title: '商品图片', dataIndex: 'mainImage',
            render: (value) => <Avatar shape="square" src={fileSrc(value)}
                onClick={() => {
                    this.props.action.global.showPreviewForFile(value)
                }} />
        },
        { title: '所属类目', dataIndex: 'category', render: value => value?value.title: ""},
        { title: '所属品牌', dataIndex: 'brand', render: (value) => value ? value.name : "" },
        {
            title: '商品状态', dataIndex: 'status',
            render: (text, record) => {
                const options = statusOptions.filter(item => text === item.value)
                return options[0].label
            }
        },
        {
            title: '操作',
            key: 'operator',
            render: (text, record) => {
                const { id, title } = record;
                const items = [
                    {
                        label: '修改',
                        visible: hasPermission('admin:products:edit'),
                        onClick: () => {
                            this.handleEdit(record);
                        },
                    },
                    {
                        label: '删除',
                        color: 'red',
                        visible: hasPermission('admin:products:del'),
                        confirm: {
                            title: `您确定要删除“${title}”？`,
                            onConfirm: () => { this.handleDel(id) },
                        },
                    },
                ];

                return (<Operator items={items} />);
            },
        },
    ];

    componentDidMount() {
        this.handleSearch();
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
        this.setState({ formData: {}, visible: true });
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
            total,
            pageNum,
            pageSize,
            visible,
            formData,
        } = this.state;

        return (
            <PageContent loading={loading}>
                <QueryBar>
                    <QueryItem
                        loadOptions={this.fetchOptions}
                        items={this.queryItems}
                        onSubmit={params => this.setState({ params }, this.handleSearch)}
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

                <ProductEdit
                    formData={formData}
                    visible={visible}
                    onOk={this.handleOk.bind(this)}
                    onCancel={() => this.setState({ visible: false })}
                />
            </PageContent>
        );
    }
}
