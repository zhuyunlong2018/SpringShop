import React, { Component } from 'react';
import { Modal, Spin, Steps, Button } from 'antd';
import { add, edit, fetchById } from '@/api/product'
import ProductInfo from './ProductInfo'
import DetailEditor from './DetailEditor'
import Attributes from './Attributes'
import './style.less'

const { Step } = Steps;

/**
 * 商品编辑页
 */

export default class ProductEdit extends Component {
    state = {
        loading: false,
        data: {},       //商品主体
        infoData: {},   //商品基础信息
        skuData: [],    //商品sku列表数据
        attributes: {}, //商品基础属性
        categoryParams: [], //商品基础信息中选中了分类，此处存储该分类的属性值
        current: 0,     //当前编辑项
    };

    /**
     * 下一步操作
     */
    next() {
        const { loading, current, data } = this.state;
        if (loading) return;
        let next = false
        if (current === 0) {
            //完成商品信息
            const { handleComplete } = this.infoForm
            //存储基础信息
            next = !handleComplete(infoData => {
                const { categoryIdWithInfo } = this.props
                //获取基本信息中选中的分类所携带的属性信息
                const { params: { params } } = categoryIdWithInfo[infoData.categoryId]
                this.setState({ infoData, categoryParams: params ? JSON.parse(params) : [] })
            })
            if (next) {
                //todo 获取商品属性、详情和sku列表
                fetchById({id: data.id}).then(data => {
                    console.log(data)
                })
            }
        } else if (current === 1) {
            //完成商品属性
            const { skuData, attributes } = this.attributes.handleComplete()
            this.setState({ skuData, attributes })
            next = true
        }
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
            console.log(formData)
        }
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
        const detailData = this.detail.handleComplete()
        const { infoData, skuData, attributes } = this.state
        //提交表单数据
        const { onOk } = this.props
        // id存在未修改，不存在未添加
        const data = {
            ...infoData,    //商品基础信息
            attributes, //商品基础属性
            desc: detailData,   //商品详情
            sku: skuData    //商品sku列表
        }
        console.log(data);
        return;
        const ajax = data.id ? edit(data) : add(data);

        this.setState({ loading: true });
        ajax.then((res) => {
            //保存成功，执行回调函数修改本地数据
            if (onOk) onOk(infoData.id, res)
        })
            .finally(() => this.setState({ loading: false }));
    }

    /**
     * 处理重置表单
     */
    handleReset = () => {
        this.props.form.resetFields();
    };

    render() {
        const { visible, brandOptions, categoryTree, onLoadData } = this.props;
        const { loading, current, data, categoryParams } = this.state;
        const title = data.id ? '修改商品' : '添加商品';
        const steps = [
            {
                title: '商品信息',
                content: <ProductInfo data={data} onRef={ref => this.infoForm = ref}
                    brandOptions={brandOptions}
                    categoryTree={categoryTree}
                    onLoadData={onLoadData}
                    handleChangeCategory={categoryId => this.handleChangeCategory(categoryId)}
                    setData={data => this.setState({ data })} />,
            },
            {
                title: '商品属性',
                content: <Attributes data={data} onRef={ref => this.attributes = ref}
                    categoryParams={categoryParams} />,
            },
            {
                title: '商品详情',
                content: <DetailEditor data={data} onRef={ref => this.detail = ref} />,
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
