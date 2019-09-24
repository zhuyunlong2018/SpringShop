import React, { Component } from 'react';
import { connect } from '@/models/index';
import { Modal } from 'antd';

/**
 * 全局图片预览
 */
@connect(state => ({ global: state.global }))
export default class ImagePreview extends Component {

    render() {

        const {
            global: { previewVisible, previewImage, previewAlt },
            action: { global: {hidePreviewVisible }}
        } = this.props
        return (
            <Modal visible={previewVisible} footer={null} onCancel={hidePreviewVisible} zIndex={1010} >
                <img alt={previewAlt} style={{ width: '100%' }} src={previewImage} />
            </Modal>
        )

    }
}