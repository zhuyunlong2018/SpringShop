import React, { Component } from 'react';
import { Layout, Menu, Tooltip, Button } from 'antd';
import { getClass, delClass } from '@/api/image'
import ClassEdit from './ClassEdit'
import './style.less'

const { Sider } = Layout

export default class LeftSider extends Component {

    state = {
        menuList: [], //分类列表
        visible: false, //编辑框显示隐藏
        data: {}, //表单数据
        selectedKeys: [],//被选中的分类菜单
    }

    componentWillMount() {
        this.fetchList()
    }


    /**
     * 获取分类列表
     */
    fetchList() {
        getClass().then(res => {
            const { selectedKeys } = this.state
            if (res && res.length > 0 && selectedKeys.length === 0) {
                //默认选中第一个分类菜单
                this.setState({ selectedKeys: [res[0].id.toString()] })
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
        const { selectedKeys, menuList } = this.state
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
        const { selectedKeys, menuList } = this.state
        if (selectedKeys.length === 0) return;
        const id = selectedKeys[0]
        delClass({ id }).then(res => {
            let newList =  [...menuList]
            menuList.forEach((e,i) => {
                if (e.id.toString() === id) {
                   newList.splice(i, 1)
                }
            });
            if (newList.length > 0) {
                this.setState({
                    selectedKeys: [newList[0].id.toString()],
                    menuList: newList
                })
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
        const { menuList, visible, data, selectedKeys } = this.state

        const renderMenus = menuList.map(menu => {
            return (
                <Menu.Item key={menu.id}>
                    <span>{menu.title}</span>
                </Menu.Item>
            )
        })
        return (
            <Sider trigger={null} collapsible width={120} >
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
                        <Button type="danger" shape="circle" icon="close" size="small"
                            onClick={this.handleDel} />
                    </Tooltip>
                </div>
                <Menu theme="light" mode="inline"
                    // defaultSelectedKeys={[menuList[0].id.toString()]}
                    selectedKeys={selectedKeys}
                    onSelect={item => this.setState({ selectedKeys: [item.key] })}
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
        )
    }
}