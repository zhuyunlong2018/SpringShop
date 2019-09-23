import React, { Component } from 'react';
import { Layout, Menu, Tooltip, Button, Popconfirm } from 'antd';
import { getClass, delClass } from '@/api/image'
import ClassEdit from './ClassEdit'
import './style.less'

const { Sider } = Layout

export default class LeftSider extends Component {

    state = {
        menuList: [], //分类列表
        visible: false, //编辑框显示隐藏
        data: {}, //表单数据
    }

    componentWillMount() {
        this.fetchList()
    }


    /**
     * 获取分类列表
     */
    fetchList() {
        getClass().then(res => {
            const { selectedKeys, setKeys } = this.props
            if (res && res.length > 0 && selectedKeys.length === 0) {
                //默认选中第一个分类菜单
                setKeys([res[0].id.toString()])
            }
            this.setState({ menuList: res })
        })
    }

    /**
     * 处理添加分类
     */
    handleAdd = () => {
        this.setState({ data: {}, visible: true });
    };

    /**
     * 处理编辑分类
     */
    handleEdit = () => {
        const { menuList } = this.state
        const { selectedKeys } = this.props
        if (selectedKeys.length === 0) return;
        menuList.forEach(e => {
            if (e.id.toString() === selectedKeys[0]) {
                this.setState({ data: e, visible: true });
            }
        });
    };


    /**
     * 处理删除
     */
    handleDel = () => {
        const { menuList } = this.state
        const { selectedKeys, setKeys } = this.props
        if (selectedKeys.length === 0) return;
        const id = selectedKeys[0]
        delClass({ id }).then(res => {
            let newList = [...menuList]
            menuList.forEach((e, i) => {
                if (e.id.toString() === id) {
                    newList.splice(i, 1)
                }
            });
            if (newList.length > 0) {
                this.setState({ menuList: newList })
                setKeys([newList[0].id.toString()])
            }
        })

    }

    /**
     * 处理提交成功后回调
     * @param {Number}} id 
     * @param {Object} data 
     */
    handleOk(id, data) {
        let menuList = []
        if (id) {
            menuList = [...this.state.menuList]
            menuList.forEach((e, i) => {
                if (e.id === id) {
                    menuList[i] = data
                }
            });
        } else {
            menuList = [...this.state.menuList, data]
        }
        this.setState({ visible: false, menuList })
    }

    render() {
        const { menuList, visible, data } = this.state
        const { selectedKeys, setKeys } = this.props
        const renderMenus = menuList.map(menu => {
            return (
                <Menu.Item key={menu.id}>
                    <span>{menu.title}</span>
                </Menu.Item>
            )
        })
        return (
            <div styleName="left-sider">
                <Sider trigger={null} collapsible width={120} style={{ height: '100%' }} >
                    <div styleName="operation-icon">
                        <Tooltip placement="bottomLeft" title="添加分类">
                            <Button shape="circle" icon="form" size="small" styleName="button"
                                onClick={this.handleAdd} />
                        </Tooltip>
                        <Tooltip placement="bottomLeft" title="编辑分类">
                            <Button type="dashed" shape="circle" icon="edit" size="small" styleName="button"
                                onClick={this.handleEdit} />
                        </Tooltip>
                        <Tooltip placement="bottomLeft" title="删除分类">
                            <Popconfirm
                                placement="rightTop"
                                title="确认要删除该分类吗？"
                                onConfirm={this.handleDel}
                                okText="Yes"
                                cancelText="No"
                            >
                                <Button type="danger" shape="circle" icon="close" size="small" />
                            </Popconfirm>

                        </Tooltip>
                    </div>
                    <Menu theme="light" mode="inline"
                        // defaultSelectedKeys={[menuList[0].id.toString()]}
                        selectedKeys={selectedKeys}
                        onSelect={item => setKeys([item.key])}
                        style={{ height: "calc(100% - 64px)" }}>
                        {renderMenus}
                    </Menu>
                    <ClassEdit
                        formData={data}
                        visible={visible}
                        onOk={this.handleOk.bind(this)}
                        onCancel={() => this.setState({ visible: false })}
                    />
                </Sider>
            </div>

        )
    }
}