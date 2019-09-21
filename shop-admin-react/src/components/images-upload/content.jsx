import React, { Component } from 'react';
import { Upload, Icon, Layout, Tooltip } from 'antd';
import { connect } from '@/models/index';
import { list } from '@/api/image'
import { Pagination } from "@/library/antd";

import './style.less'

function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

const { Content } = Layout;

@connect(state => ({ global: state.global }))
export default class RightContent extends Component {

    state = {

        fileList: [],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        selectList: [],//已经选中的图片
        params: {},
    };

    componentWillMount() {
        this.fetchList()
    }


    /**
     * 获取图片列表
     */
    fetchList() {
        const { params, pageNum, pageSize } = this.state;
        list({ ...params, pageNum, pageSize }).then(res => {
            console.log(res)
            this.setState({ total: res.total, fileList: res.records })
        })
    }

    /**
     * 处理预览图片
     */
    handlePreview = async (e, url) => {
        e.stopPropagation();
        e.preventDefault();
        this.props.action.global.showPreviewVisible(url)
    };

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

    handleUplaod = () => {
        console.log('handle upload')
    }

    render() {

        const { fileList, selectList, total, pageNum, pageSize } = this.state;
        const uploadButton = (
            <div>
                <Icon type="plus" />
                <div className="ant-upload-text">Upload</div>
            </div>
        );

        //图片容器
        const renderContent = fileList.map(file => {
            return (
                <div key={file.id}
                    styleName={selectList.indexOf(file) > -1 ? 'img-box selected' : 'img-box'}
                    onClick={() => this.handleSelect(file)}>
                    <div styleName='img'>
                        <img src={file.src} alt="" />
                        <span styleName="priview" onClick={(e) => this.handlePreview(e, file.src)}>
                            <Tooltip placement="bottom" title="预览">
                                <svg viewBox="64 64 896 896" data-icon="eye" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M942.2 486.2C847.4 286.5 704.1 186 512 186c-192.2 0-335.4 100.5-430.2 300.3a60.3 60.3 0 0 0 0 51.5C176.6 737.5 319.9 838 512 838c192.2 0 335.4-100.5 430.2-300.3 7.7-16.2 7.7-35 0-51.5zM512 766c-161.3 0-279.4-81.8-362.7-254C232.6 339.8 350.7 258 512 258c161.3 0 279.4 81.8 362.7 254C791.5 684.2 673.4 766 512 766zm-4-430c-97.2 0-176 78.8-176 176s78.8 176 176 176 176-78.8 176-176-78.8-176-176-176zm0 288c-61.9 0-112-50.1-112-112s50.1-112 112-112 112 50.1 112 112-50.1 112-112 112z"></path>
                                </svg>
                            </Tooltip>
                        </span>
                    </div>

                </div>
            )
        })

        return (
            <Content style={{ margin: '0 16px' }}>
                <div styleName='content'>
                    <ul styleName='image-list'>
                        {renderContent}
                    </ul>
                    <div className="clearfix">
                        <Upload
                            showUploadList={false}
                            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                            listType="picture-card"
                            onRemove={() => false}
                            customRequest={this.handleUplaod}
                        >
                            {uploadButton}
                        </Upload>
                    </div>

                    <Pagination
                        total={total}
                        pageNum={pageNum}
                        pageSize={pageSize}
                        onPageNumChange={pageNum => this.setState({ pageNum }, this.fetchList)}
                        onPageSizeChange={pageSize => this.setState({ pageSize, pageNum: 1 }, this.fetchList)}
                    />
                </div>
            </Content>
        )
    }
}