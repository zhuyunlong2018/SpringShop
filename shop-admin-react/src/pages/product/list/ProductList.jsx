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
import { all } from '@/api/brand'
import { listByLevels, fetchWithParamsByPid } from '@/api/category';

const statusOptions = [
    { label: "正常", value: 1 },
    { label: "下架", value: 2 },
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
        brandOptions: [], //商品品牌列表
        categoryTree: [], //商品类目的node tree
        categoryChildrenAlreadyLoaded: [], //商品类目分三级，初步只加载一二级，此处记录已加载了第三级的第二级类目id
        categoryIdWithInfo: {}, //分类ID=>对应法分类对象，example： {categoryId: {...category}},此处存储第三极类目的ID对应对象，只需要通过this.state.categoryIdWithInfo[categoryId]就可以获取到对应的分类对象
    };

    // TODO 查询条件
    queryItems = [
        [
            {
                collapsedShow: true, // 收起时显示
                type: 'input',
                field: 'title',
                label: '商品标题',
                placeholder: '请输入商品标题',
                itemStyle: { flex: '0 0 300px' }, // 固定宽度
            },
            {
                collapsedShow: true, // 收起时显示
                type: 'select-tree',
                field: 'categoryId',
                label: '所属类目',
                showSearch: true,
                treeDataSimpleMode: {},
                treeNodeFilterProp: "title",
                dropdownStyle: { maxHeight: 400, overflow: 'auto' },
                searchPlaceholder: "搜索仅支持一、二级类目",
                loadData: (node) => this.onLoadData(node),
                placeholder: '请输入类目名称',
                itemStyle: { flex: '0 0 300px' }, // 固定宽度
            },
        ],
        [
            {
                type: 'select',
                field: 'brandId',
                label: '所属品牌',
                showSearch: true,
                optionFilterProp: 'label',
                itemStyle: { flex: '0 0 300px' }, // 固定宽度
            },
            {
                type: 'select',
                field: 'status',
                options: statusOptions,
                label: '商品状态',
                itemStyle: { flex: '0 0 300px' }, // 固定宽度
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
        { title: '所属类目', dataIndex: 'category', render: value => value ? value.title : "" },
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
        this.handleSearch()
        this.fetchBrands()
        this.fetchFirstAndSecondCategories()

    }

    /**
     * 获取品牌列表
     */
    fetchBrands() {
        all().then(res => {
            const brandOptions = res.map(brand => {
                const { id, name } = brand;
                return { value: id, label: name }
            })
            this.setState({ brandOptions }, this.queryItem.setData({ brandId: brandOptions }))
        })
    }

    /**
     * 获取所有第一级和第二级类目列表并整理为treeNode
     */
    fetchFirstAndSecondCategories() {
        listByLevels({ levels: [1, 2] }).then(res => {
            const newNodes = res.map(item => {
                const { id, pid, title, level } = item
                return { id, pId: pid, title, value: id, level, selectable: false }
            })
            this.setState({ categoryTree: newNodes }, this.queryItem.setData({ categoryId: newNodes }))
        })
    }


    /**
     * 根据id获取第二级分类下的所有第三级类目子集
     * @param {Number} id 
     */
    fetchThirdCategories(id) {
        return fetchWithParamsByPid({ pid: id }).then(res => {
            //建立临时存储第三类目分类变量
            const newCategoryIdWithInfo = {}
            const newNodes = res.map(item => {
                const { id, pid, title, level } = item
                //对象存入临时变量
                newCategoryIdWithInfo[id] = item
                //此处返回给可以渲染tree-node的新对象数组
                return { id, pId: pid, title, value: id, level, isLeaf: true }
            })
            const otherTree = this.state.categoryTree.concat(newNodes)
            //取出目标state
            const { categoryChildrenAlreadyLoaded, categoryIdWithInfo } = this.state
            //更新合并
            this.setState({
                categoryTree: otherTree,
                categoryIdWithInfo: { ...categoryIdWithInfo, ...newCategoryIdWithInfo },
                categoryChildrenAlreadyLoaded: [...categoryChildrenAlreadyLoaded, id]
            }, this.queryItem.setData({ categoryId: otherTree }));
        })
    }


    /**
     * select-tree异步获取第三级类目数据
     */
    onLoadData = treeNode =>
        new Promise(resolve => {
            const { id, level } = treeNode.props;
            const { categoryChildrenAlreadyLoaded } = this.state
            if (level === 2 && categoryChildrenAlreadyLoaded.indexOf(id) === -1) {
                this.fetchThirdCategories(id).finally(() => resolve())
            } else {
                resolve()
            }
        });

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
        //注意，商品只能绑定在第三级类目，此处判断要编辑的第三级类目是否已经异步加载好
        const { category: { pid } } = formData
        if (this.state.categoryChildrenAlreadyLoaded.indexOf(pid) === -1) {
            //加载商品所在类目上一级类目的所有子集
            this.fetchThirdCategories(pid)
        }
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
            brandOptions,
            categoryTree,
            categoryIdWithInfo,
        } = this.state;

        return (
            <PageContent loading={loading}>
                <QueryBar>
                    <QueryItem
                        onRef={ref => this.queryItem = ref}
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
                    categoryIdWithInfo={categoryIdWithInfo}
                    brandOptions={brandOptions}
                    categoryTree={categoryTree}
                    onLoadData={this.onLoadData}
                    onOk={this.handleOk.bind(this)}
                    onCancel={() => this.setState({ visible: false })}
                />
            </PageContent>
        );
    }
}
