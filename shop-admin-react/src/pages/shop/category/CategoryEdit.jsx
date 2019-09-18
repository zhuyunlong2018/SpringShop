import React, { Component } from 'react';
import { Modal, Form, Spin } from 'antd';
import { FormElement } from '@/library/antd';
import { add, edit } from '@/api/category'

@Form.create()
export default class CategoryEdit extends Component {
    state = {
        loading: false,
        data: {},
    };

    componentDidUpdate(prevProps) {
        const { formData, visible, form: { resetFields } } = this.props;

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
        const { loading } = this.state;
        if (loading) return;
        const { onOk, form: { validateFieldsAndScroll } } = this.props;
        //表单验证
        validateFieldsAndScroll((err, values) => {
            if (err) return;
            const params = { ...values };
            const { id } = values;

            // TODO ajax 提交数据
            // id存在未修改，不存在未添加
            const ajax = id ? edit(params) : add(params);

            this.setState({ loading: true });
            ajax.then((data) => {
                //保存成功，执行回调函数修改本地数据
                if (onOk) onOk(id, data)
            })
                .finally(() => this.setState({ loading: false }));
        });
    };

    /**
     * 处理取消操作
     */
    handleCancel = () => {
        const { onCancel } = this.props;
        if (onCancel) onCancel();
    };

    /**
     * 处理重置表单
     */
    handleReset = () => {
        this.props.form.resetFields();
    };

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props} />;

    render() {
        const { visible, radioOptions } = this.props;
        const { loading, data } = this.state;
        const title = data.id ? '修改分类管理' : '添加分类管理';
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
                        {data.id ? (<FormElement type="hidden" field="id" decorator={{ initialValue: data.id }} />) : null}
                        <FormElement
                            label="类目等级"
                            type="radio-group"
                            field="level"
                            options={radioOptions}
                            decorator={{
                                initialValue: data.level,
                                rules: [
                                    { required: true, message: '类目等级不能为空！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="父类目"
                            type="number"
                            field="pid"
                            decorator={{
                                initialValue: data.pid,
                                rules: [
                                    { required: true, message: '父类目ID=0时，代表的是一级的类目不能为空！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="类目名称"
                            type="input"
                            field="title"
                            decorator={{
                                initialValue: data.title,
                                rules: [
                                    { required: true, message: '类目名称不能为空！' },
                                    { max: 255, message: '最多255个字符！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="描述"
                            type="input"
                            field="description"
                            decorator={{
                                initialValue: data.description,
                                rules: [
                                    { required: true, message: '描述不能为空！' },
                                    { max: 65535, message: '最多65535个字符！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="图片"
                            type="input"
                            field="img"
                            decorator={{
                                initialValue: data.img,
                                rules: [
                                    { required: true, message: '图片不能为空！' },
                                    { max: 255, message: '最多255个字符！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="排序"
                            type="number"
                            field="sortOrder"
                            decorator={{
                                initialValue: data.sortOrder,
                                rules: [
                                    { required: true, message: '排列序号不能为空！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="状态"
                            type="switch"
                            field="status"
                            checked={data.status===1}
                            onClick={()=>{
                                let status
                                if (data.status === 1) {
                                    status = 2
                                } else {
                                    status = 1
                                }
                                this.setState({data: {...data, status}})
                            }}
                            decorator={{
                                initialValue: data.status,
                                rules: [
                                    { required: true, message: '状态' },
                                ],
                            }}
                        />

                    </Form>
                </Spin>
            </Modal>
        );
    }
}
