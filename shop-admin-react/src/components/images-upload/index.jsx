import React, { Component } from 'react';
import { Modal, Layout, Input, message, Upload, Icon, Button, Avatar, Tooltip } from 'antd';
import config from '@/commons/config-hoc';
import { connect } from '@/models/index';
import LeftSider from './leftSider'
import RightContent from './content'
import Permission from '../permission'
import { fileSrc } from '@/commons'
import { add, list } from '@/api/image'

import './style.less'

/**
 * 图片资源上传管理模块
 */
const { Header } = Layout;
const { Search } = Input;

@config({ properties: true })
@connect(state => ({ global: state.global }))
export default class ImagesUpload extends Component {
    state = {
        loading: false, //模态框加载图标
        uploadLoading: false,   //图片上传加载图标
        uploadMessage: "",  //上传图片关键词描述
        searchKey: "",      //检索图片关键词
        selectedKeys: [],   //侧边栏分类，被选中的分类菜单，example: ['1']
        fileList: [],   //内容图片列表
        pageSize: 21,   //内容每页数量
        pageNum: 1,     //内容页码
        total: 0,       //总图片数
        selectList: [],//已经选中的图片
    };

    /**
     * 获取图片列表
     */
    fetchList() {
        this.setState({ loading: true })
        const { searchKey, pageNum, pageSize, selectedKeys } = this.state;
        const classification = selectedKeys.length === 0 ? 0 : selectedKeys[0]
        list({ searchKey, classification, pageNum, pageSize }).then(res => {

            this.setState({ total: res.total, fileList: res.records, selectList: [] })
        }).finally(() => this.setState({ loading: false }))
    }

    //处理自定义上传图片
    handleUpload = ({
        data,
        file,
        filename,
        onError,
        onSuccess,
    }) => {
        this.setState({ uploadLoading: true })
        const formData = new FormData();
        if (data) {
            Object.keys(data).forEach(key => {
                formData.append(key, data[key]);
            });
        }
        formData.append(filename, file);

        add(formData)
            .then((res) => {
                const { pageSize, fileList } = this.state
                //上传成功后，将图片push到下方content中,移除list最后一个元素，并且将返回图片推入第一个元素
                const list = [...fileList]
                //todo 如果本页满了
                if (fileList.length === pageSize) {
                    list.splice(list.length - 1, 1)
                }
                this.setState({ fileList: [res, ...list] })
                onSuccess(res, file);
            })
            .catch(onError)
            .finally(() => {
                this.setState({
                    uploadLoading: false,
                    uploadMessage: ""
                })
            });

        return {
            abort() {
                console.log('upload progress is aborted.');
            },
        };
    }

    /**
     * 处理选中图片
     */
    handleSelect = (file) => {
        let { selectList } = this.state
        const index = selectList.indexOf(file)
        if (index > -1) {
            selectList.splice(index, 1)
        } else {
            selectList = [...selectList, file]
        }
        this.setState({ selectList })
    }

    /**
     * 处理预览图片
     */
    handlePreview = async (e, url) => {
        e.stopPropagation();
        e.preventDefault();
        this.props.action.global.showPreviewVisible(url)
    };

    render() {
        const {
            uploadLoading, uploadMessage, searchKey, selectedKeys, total, fileList,
            pageNum, pageSize, loading, selectList
        } = this.state;
        const { visible, handleCancel, handelSelectImages } = this.props;
        const classification = selectedKeys.length === 0 ? 0 : selectedKeys[0]
        const uploadProps = {
            name: 'file',
            customRequest: this.handleUpload,
            showUploadList: false,
            multiple: true,
            disabled: uploadLoading || uploadMessage === "",
            data: { keywords: uploadMessage, classification },
            onChange(info) {
                if (info.file.status !== 'uploading') {
                    // console.log(info.file, info.fileList);
                }
                if (info.file.status === 'done') {
                    message.success(`${info.file.name} 上传成功`, 1);
                } else if (info.file.status === 'error') {
                    message.error(`${info.file.name} 上传失败.`, 1);
                }
            },
        };

        const footer = (
            <div styleName='footer'>
                <ul styleName="selected">
                    {
                        selectList.map(file => {
                            return (<li styleName="icon" key={file.id}>
                                <Tooltip placement="top" title={file.keywords} >
                                    <Avatar src={fileSrc(file)} onClick={e => this.handlePreview(e, fileSrc(file))} />
                                </Tooltip>
                            </li>)
                        })
                    }
                </ul>
                <Button type="primary"
                    onClick={() => {
                        handelSelectImages([...selectList])
                        this.setState({ selectList: [], searchKey: '' })
                    }}>选择({selectList.length}张)</Button>
            </div>
        )
        return (
            <div>
                <Modal visible={visible}
                    confirmLoading={loading}
                    onCancel={() => {
                        this.setState({ selectList: [], searchKey: '' })
                        handleCancel()
                    }}
                    width={970}
                    zIndex={1001}
                    footer={footer}
                >
                    <Layout>
                        <LeftSider selectedKeys={selectedKeys}
                            setKeys={(selectedKeys) => {
                                this.setState({ selectedKeys, pageNum: 1 }, this.fetchList)
                            }} />
                        <Layout>
                            <Header style={{ background: '#fff', padding: 0 }}>
                                <ul styleName="header">
                                    <li styleName="list">
                                        <Search placeholder="通过关键词检索"
                                            value={searchKey}
                                            onSearch={() => this.setState({ pageNum: 1 }, this.fetchList)}
                                            enterButton
                                            onChange={e => {
                                                this.setState({ searchKey: e.target.value })
                                            }} />
                                    </li>
                                    <li styleName="list">
                                        <Permission code="admin:images:list">
                                            <Upload {...uploadProps}>
                                                <Input addonAfter={<Icon type={uploadLoading ? 'loading' : "upload"}
                                                    onClick={() => {
                                                        if (uploadLoading) {
                                                            return message.error("正在上传中，请耐心等待……", 1)
                                                        }
                                                        if (uploadMessage === "") {
                                                            return message.error("请填写要上传图片关键词!", 1);
                                                        }
                                                    }} />}
                                                    placeholder="请输入上传图片描述！"
                                                    onChange={(e) => {
                                                        this.setState({ uploadMessage: e.target.value })
                                                    }}
                                                    value={uploadMessage} onClick={e => {
                                                        e.preventDefault();
                                                        e.stopPropagation();
                                                    }} />
                                            </Upload>
                                        </Permission>

                                    </li>
                                </ul>
                            </Header>
                            <RightContent total={total} fileList={fileList} pageNum={pageNum} pageSize={pageSize}
                                selectList={selectList}
                                onPageNumChange={(pageNum) => {
                                    this.setState({ pageNum }, this.fetchList)
                                }}
                                onPageSizeChange={(pageSize) => {
                                    this.setState({ pageSize, pageNum: 1 }, this.fetchList)
                                }}
                                handleSelect={(file) => this.handleSelect(file)}
                                fileSrc={(file) => fileSrc(file)}
                                handlePreview={(e, url) => this.handlePreview(e, url)} />
                        </Layout>
                    </Layout>
                </Modal>
            </div>
        );
    }
}