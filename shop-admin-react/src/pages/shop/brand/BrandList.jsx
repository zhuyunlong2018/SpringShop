import React, {Component} from 'react';
import {Table} from 'antd';
import {
    Pagination,
    Operator,
} from "@/library/antd";
import PageContent from '@/layouts/page-content';
import config from '@/commons/config-hoc';
import BrandEdit from './BrandEdit';

@config({
    path: '/brand/list',
    ajax: true,
})
export default class BrandList extends Component {
    state = {
        loading: false,
        dataSource: [],
        total: 0,
        pageSize: 10,
        pageNum: 1,
        params: {},
        id: void 0,
        visible: false,
        brand: {},
    };

    columns = [
        {title: '品牌名称', dataIndex: 'name'},
        {title: '品牌描述', dataIndex: 'description'},
        {
            title: '操作',
            key: 'operator',
            render: (text, record) => {
                const {id, name} = record;
                const successTip = `删除“${name}”成功！`;
                const items = [
                    {
                        label: '修改',
                        onClick: () => {
                            this.handleEdit(record);
                        },
                    },
                    {
                        label: '删除',
                        color: 'red',
                        confirm: {
                            title: `您确定要删除“${name}”？`,
                            onConfirm: () => {
                                this.setState({loading: true});
                                this.props.ajax
                                    .del(`/admins/brands/del/${id}`, null, {successTip})
                                    .then(() => this.handleSearch())
                                    .finally(() => this.setState({loading: false}));
                            },
                        },
                    },
                ];

                return (<Operator items={items}/>);
            },
        },
    ];

    componentDidMount() {
        this.handleSearch();
    }

    handleSearch = () => {
        const {params, pageNum, pageSize} = this.state;

        this.setState({loading: true});
        this.props.ajax
            .get('/admin/brands/list', {...params, pageNum, pageSize})
            .then(res => {
                if (res) {
                    const {records: dataSource, total} = res;
                    this.setState({
                        dataSource,
                        total,
                    });
                }
            })
            .finally(() => this.setState({loading: false}));
    };

    handleAdd = () => {
        this.setState({id: void 0, visible: true});
    };

    handleEdit = (brand) => {
        this.setState({brand, visible: true});
    };

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
                    onPageNumChange={pageNum => this.setState({pageNum}, this.handleSearch)}
                    onPageSizeChange={pageSize => this.setState({pageSize, pageNum: 1}, this.handleSearch)}
                />

                <BrandEdit
                    brand={brand}
                    visible={visible}
                    onOk={() => this.setState({visible: false})}
                    onCancel={() => this.setState({visible: false})}
                />
            </PageContent>
        );
    }
}
