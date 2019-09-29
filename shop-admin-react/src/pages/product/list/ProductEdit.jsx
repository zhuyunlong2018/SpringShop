import React, { Component } from 'react';
import { Modal, Spin, Steps, Button } from 'antd';
import { add, edit } from '@/api/product'
import ProductInfo from './ProductInfo'
import DetailEditor from './DetailEditor'
import './style.less'

const { Step } = Steps;

export default class ProductEdit extends Component {
    state = {
        loading: false,
        data: {},
        infoData: {},
        current: 0,
    };

    /**
     * 下一步操作
     */
    next() {
        const { loading, current } = this.state;
        if (loading) return;
        let next = false
        if (current === 0) {
            //完成商品信息
            const { handleSubmit } = this.infoForm
            next = !handleSubmit(infoData => { this.setState({ infoData })})

            //todo 获取商品属性
        } else if (current === 1) {
            //完成商品属性
            console.log(this.state.infoData)
            next = true
            //todo 获取商品详情
        }
        console.log(next)
        if (next) this.setState({ current: current + 1 });
    }

    /**
     * 返回上一步
     */
    prev() {
        const current = this.state.current - 1;
        this.setState({ current });
    }

    componentDidUpdate(prevProps) {
        const { formData, visible } = this.props;

        // 打开弹框
        if (!prevProps.visible && visible) {
            // 填充数据
            this.setState({ data: formData, current: 0 })
        }
    }

    saveInfo() {
        const { infoData } = this.state
        const { onOk } = this.props
        // TODO ajax 提交数据
        // id存在未修改，不存在未添加
        const ajax = infoData.id ? edit(infoData) : add(infoData);

        this.setState({ loading: true });
        ajax.then((data) => {
            //保存成功，执行回调函数修改本地数据
            if (onOk) onOk(infoData.id, data)
        })
            .finally(() => this.setState({ loading: false }));
    }

    /**
     * 处理取消操作
     */
    handleCancel = () => {
        const { onCancel } = this.props;
        if (onCancel) onCancel();
    };

    /**
     * 处理保存数据
     */
    handleOk = () => {
        //先校验商品详情


        //提交表单数据
    }

    /**
     * 处理重置表单
     */
    handleReset = () => {
        this.props.form.resetFields();
    };
    render() {
        const { visible, brandOptions, categoryTree, onLoadData } = this.props;
        const { loading, current, data } = this.state;
        const title = data.id ? '修改商品' : '添加商品';
        const steps = [
            {
                title: '商品信息',
                content: <ProductInfo data={data} onRef={ref => this.infoForm = ref}
                    brandOptions={brandOptions}
                    categoryTree={categoryTree}
                    onLoadData={onLoadData}
                    setData={data => this.setState({ data })} />,
            },
            {
                title: '商品属性',
                content: "content",
            },
            {
                title: '商品详情',
                content: <DetailEditor />,
            },
        ];

        const footer = (
            <div className="steps-action">
                {current > 0 && (
                    <Button style={{ marginLeft: 8 }} onClick={() => this.prev()}>上一步</Button>
                )}
                {current < steps.length - 1 && (
                    <Button type="primary" onClick={() => this.next()}>下一步</Button>
                )}
                {current === steps.length - 1 && (
                    <Button type="primary" onClick={this.handleOk}>完成</Button>
                )}
            </div>
        )
        return (
            <Modal width={800}
                destroyOnClose
                confirmLoading={loading}
                visible={visible}
                title={title}
                footer={footer}
                onCancel={this.handleCancel}
            >
                <Spin spinning={loading}>
                    <Steps current={current}>
                        {steps.map(item => (
                            <Step key={item.title} title={item.title} />
                        ))}
                    </Steps>
                    <div styleName="edit-container">{steps[current].content}</div>
                </Spin>
            </Modal>
        );
    }
}
