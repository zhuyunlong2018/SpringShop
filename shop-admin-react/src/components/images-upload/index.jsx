import React, { Component } from 'react';
import { Modal, Layout } from 'antd';
import LeftSider from './leftSider'
import RightContent from './content'

// import './style.less'

/**
 * 图片资源上传管理模块
 */


const { Header } = Layout;


export default class ImagesUpload extends Component {
    state = {
        
        visible: true,
    
    };

    componentDidMount() {
    }


    handleCancel = () => {
        this.setState({ visible: false })
    }


    render() {
        const { visible } = this.state;


        return (
            <div>
                <Modal visible={visible}
                    onCancel={this.handleCancel}
                    width={960}
                    footer={null}
                >
                    <Layout>
                        <LeftSider />
                        <Layout>
                            <Header style={{ background: '#fff', padding: 0 }}>

                            </Header>
                            <RightContent />
                        </Layout>
                    </Layout>
                </Modal>
            </div>
        );
    }
}