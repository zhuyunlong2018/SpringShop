import React, {Component} from 'react';
import {Modal, Form, Spin} from 'antd';
import {FormElement} from '@/library/antd';
import { add, edit } from '@/api/product'

@Form.create()
export default class ProductEdit extends Component {
    state = {
        loading: false,
        data: {},
    };

    componentDidUpdate(prevProps) {
        const {formData, visible, form: {resetFields}} = this.props;

        // 打开弹框
        if (!prevProps.visible && visible) {
            // 重置表单，接下来填充新的数据
            resetFields();

            // 填充数据
            this.setState({ data: formData })
        }
    }

    /**
     * 处理提交保存
     */
    handleSubmit = () => {
        const {loading} = this.state;
        if (loading) return;
        const {onOk, form: {validateFieldsAndScroll}} = this.props;
        //表单验证
        validateFieldsAndScroll((err, values) => {
            if (err) return ;
            const params = {...values};
            const {id} = values;

            // TODO ajax 提交数据
            // id存在未修改，不存在未添加
            const ajax = id ? edit(params) : add(params);

            this.setState({loading: true});
            ajax.then((data) => {
                //保存成功，执行回调函数修改本地数据
                if (onOk)  onOk(id, data)
            })
                .finally(() => this.setState({loading: false}));
        });
    };

    /**
     * 处理取消操作
     */
    handleCancel = () => {
        const {onCancel} = this.props;
        if (onCancel) onCancel();
    };

    /**
     * 处理重置表单
     */
    handleReset = () => {
        this.props.form.resetFields();
    };

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props}/>;

    render() {
        const {visible} = this.props;
        const {loading, data} = this.state;
        const title = data.id ? '修改商品' : '添加商品';
        const FormElement = this.FormElement;

        return (
            <Modal
                destroyOnClose
                confirmLoading={loading}
                visible={visible}
                title={title}
                onOk={this.handleSubmit}
                onCancel={this.handleCancel}
            >
                <Spin spinning={loading}>
                    <Form>
                        {data.id ? (<FormElement type="hidden" field="id" decorator={{initialValue: data.id}}/>) : null}
                        
                        <FormElement
                            label="商品标题"
                            type="input"
                            field="title"
                            decorator={{
                                initialValue: data.title,
                                rules: [
                                    {required: true, message: '商品标题不能为空！'},
                                    {max: 100, message: '最多100个字符！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="商品卖点"
                            type="textarea"
                            field="sellPoint"
                            decorator={{
                                initialValue: data.sellPoint,
                                rules: [
                                    {required: true, message: '商品卖点不能为空！'},
                                    {max: 500, message: '最多500个字符！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="价格区间"
                            type="input"
                            field="priceRange"
                            decorator={{
                                initialValue: data.priceRange,
                                rules: [
                                    {required: true, message: '价格区间不能为空！'},
                                    {max: 128, message: '最多128个字符！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="商品图片"
                            type="input"
                            field="image"
                            decorator={{
                                initialValue: data.image,
                                rules: [
                                    {required: true, message: '商品图片不能为空！'},
                                    {max: 500, message: '最多500个字符！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="所属类目，叶子类目"
                            type="select-tree"
                            field="categoryId"
                            decorator={{
                                initialValue: data.categoryId,
                                rules: [
                                    {required: true, message: '所属类目，叶子类目不能为空！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="所属品牌"
                            type="select"
                            field="brandId"
                            decorator={{
                                initialValue: data.brandId,
                                rules: [
                                    {required: true, message: '所属品牌不能为空！'},
                                ],
                            }}
                        />
                        
                        <FormElement
                            label="商品状态，1-正常，2-下架，3-删除"
                            type="switch"
                            field="status"
                            decorator={{
                                initialValue: data.status,
                                rules: [
                                    {required: true, message: '商品状态，1-正常，2-下架，3-删除不能为空！'},
                                ],
                            }}
                        />
                        
                    </Form>
                </Spin>
            </Modal>
        );
    }
}
