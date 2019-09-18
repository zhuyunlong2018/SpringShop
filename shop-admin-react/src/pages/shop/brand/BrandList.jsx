import React, { Component } from 'react';
import { Table } from 'antd';
import {
    Pagination,
    Operator,
    ToolBar,
} from "@/library/antd";
import {hasPermission} from '@/commons';
import PageContent from '@/layouts/page-content';
import config from '@/commons/config-hoc';
import { getList, del } from "@/api/brand"
import BrandEdit from './BrandEdit';

@config({
    path: '/brand/list',
})
export default class BrandList extends Component {
    state = {
        loading: false,
        dataSource: [],
        total: 0,
        pageSize: 10,
        pageNum: 1,
        params: {},
        visible: false,
        brand: {},
    };

    columns = [
        { title: '品牌名称', dataIndex: 'name' },
        { title: '品牌描述', dataIndex: 'description' },
        {
            title: '操作',
            key: 'operator',
            render: (text, record) => {
                const { id, name } = record;
                const items = [
                    {
                        label: '修改',
                        visible: hasPermission('admin:brands:edit'),
                        onClick: () => {
                            this.handleEdit(record);
                        },
                    },
                    {
                        label: '删除',
                        visible: hasPermission('admin:brands:del'),
                        color: 'red',
                        confirm: {
                            title: `您确定要删除“${name}”？`,
                            onConfirm: () => {
                                this.setState({ loading: true });
                                del({ id }).then(() => {
                                    let dataSource = [...this.state.dataSource]
                                    dataSource.forEach((e, i) => {
                                        if (id === e.id) {
                                            dataSource.splice(i, 1)
                                        }
                                    });
                                    this.setState({ dataSource })
                                })
                                    .finally(() => this.setState({ loading: false }));
                            },
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

    handleSearch = () => {
        const { params, pageNum, pageSize } = this.state;

        this.setState({ loading: true });
        getList({ ...params, pageNum, pageSize }).then(res => {
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

    handleAdd = () => {
        this.setState({ brand: {}, visible: true });
    };

    handleEdit = (brand) => {
        this.setState({ brand, visible: true });
    };

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
            brand,
        } = this.state;

        return (
            <PageContent loading={loading}>
                <ToolBar
                    items={[{ 
                            type: 'primary', 
                            text: '添加品牌',
                            icon: 'plus',
                            onClick: this.handleAdd, 
                            visible: hasPermission('admin:brands:add'), }
                    ]}
                />
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

                <BrandEdit
                    brand={brand}
                    visible={visible}
                    onOk={this.handleOk.bind(this)}
                    onCancel={() => this.setState({ visible: false })}
                />
            </PageContent>
        );
    }
}
