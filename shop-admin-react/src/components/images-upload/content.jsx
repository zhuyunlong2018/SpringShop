import React, { Component } from 'react';
import { Layout, Tooltip, Icon } from 'antd';
import { connect } from '@/models/index';
import { Pagination } from "@/library/antd";
import config from '@/commons/config-hoc';

import './style.less'

const { Content } = Layout;

@config({ properties: true })
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
        if (file.origin === 1) {
            const { properties } = this.props
            //服务器本地图片，需要加上域名等前缀
            return properties.upload + file.src
        }
        return file.src
    }

    render() {

        const { selectList } = this.state;
        const { fileList, total, pageNum, pageSize, onPageNumChange, onPageSizeChange } = this.props;
        const text = <span>预览</span>;
        //图片容器
        const renderContent = fileList.map(file => {
            return (
                <div key={file.id}
                    styleName={selectList.indexOf(file) > -1 ? 'img-box selected' : 'img-box'}
                    onClick={() => this.handleSelect(file)}>
                    <div styleName='img' >
                        <img src={this.fileSrc(file)} alt={file.keywords} />
                        <span styleName="priview">
                            <Tooltip placement="bottom" title={text}>
                                <Icon type="search" onClick={(e) => this.handlePreview(e, this.fileSrc(file))}/>
                            </Tooltip>
                            <Tooltip placement="bottom" title={file.keywords}>
                                <Icon type="info-circle" style={{marginLeft: 10}}/>
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