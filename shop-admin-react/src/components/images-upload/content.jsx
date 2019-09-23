import React, { Component } from 'react';
import { Layout, Tooltip } from 'antd';
import { connect } from '@/models/index';
import { Pagination } from "@/library/antd";

import './style.less'

const { Content } = Layout;

@connect(state => ({ global: state.global }))
export default class RightContent extends Component {

    state = {
        selectList: [],//已经选中的图片
    };

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

    fileSrc(file) {
        const preUrl = "http://localhost:8083/images/"
        if (file.origin ===1) {
            //服务器本地图片，需要加上域名等前缀
            return preUrl + file.src
        }
        return file.src
    }

    render() {

        const { selectList } = this.state;
        const { fileList, total, pageNum, pageSize, onPageNumChange, onPageSizeChange } = this.props;
        
        //图片容器
        const renderContent = fileList.map(file => {
            return (
                <div key={file.id}
                    styleName={selectList.indexOf(file) > -1 ? 'img-box selected' : 'img-box'}
                    onClick={() => this.handleSelect(file)}>
                    <div styleName='img' >
                        <img src={this.fileSrc(file)} alt="" />
                        <span styleName="priview" onClick={(e) => this.handlePreview(e, this.fileSrc(file))}>
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
            <Content style={{ margin: '10px' }}>
                <div styleName='content'>
                    <ul styleName='image-list'>
                        {renderContent}
                    </ul>
                    <Pagination
                        total={total}
                        pageNum={pageNum}
                        pageSize={pageSize}
                        onPageNumChange={onPageNumChange}
                        onPageSizeChange={onPageSizeChange}
                    />
                </div>
            </Content>
        )
    }
}