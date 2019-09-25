import React, { Component } from 'react';
import { Layout, Tooltip, Icon } from 'antd';
import { Pagination } from "@/library/antd";

import './style.less'

const { Content } = Layout;

export default class RightContent extends Component {


    render() {
        const { fileList, total, pageNum, pageSize, onPageNumChange, onPageSizeChange, 
            selectList, handleSelect, fileSrc, handlePreview } = this.props;
        const text = <span>预览</span>;
        //图片容器
        const renderContent = fileList.map(file => {
            return (
                <div key={file.id}
                    styleName={selectList.indexOf(file) > -1 ? 'img-box selected' : 'img-box'}
                    onClick={() => handleSelect(file)}>
                    <div styleName='img' >
                        <img src={fileSrc(file)} alt={file.keywords} />
                        <span styleName="priview">
                            <Tooltip placement="bottom" title={text}>
                                <Icon type="search" onClick={(e) => handlePreview(e, fileSrc(file))}/>
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