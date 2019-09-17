import React, { Component } from 'react';
import { Modal, Form, Spin } from 'antd';
import { FormElement } from '@/library/antd';
import { add, edit } from '@/api/brand'


@Form.create()
export default class BrandEdit extends Component {
    state = {
        loading: false,
        data: {},
    };

    componentDidUpdate(prevProps) {
        const { brand, visible, form: { resetFields } } = this.props;

        // 打开弹框
        if (!prevProps.visible && visible) {
            // 重置表单，接下来填充新的数据
            resetFields();
            this.setState({ data: brand })
        }
    }

    handleOk = () => {
        const { loading } = this.state;
        if (loading) return;
        const { onOk, form: { validateFieldsAndScroll } } = this.props;

        validateFieldsAndScroll((err, values) => {
            if (err) return;

            const params = { ...values };
            const { id } = values;

            // id存在未修改，不存在未添加
            const ajax = id ? edit(params) : add(params);
            this.setState({ loading: true });
            ajax.then((data) => {
                if (onOk)  onOk(id, data)
            })
                .finally(() => this.setState({ loading: false }));
        });
    };

    handleCancel = () => {
        const { onCancel } = this.props;
        if (onCancel) onCancel();
    };


    handleReset = () => {
        this.props.form.resetFields();
    };

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props} />;

    render() {
        const { visible } = this.props;
        const { loading, data } = this.state;
        const title = data.id ? '修改品牌管理' : '添加品牌管理';
        const FormElement = this.FormElement;

        return (
            <Modal
                destroyOnClose
                confirmLoading={loading}
                visible={visible}
                title={title}
                onOk={this.handleOk}
                onCancel={this.handleCancel}
            >
                <Spin spinning={loading}>
                    <Form>
                        {data.id ? (<FormElement type="hidden" field="id" decorator={{ initialValue: data.id }} />) : null}

                        <FormElement
                            label="品牌名称"
                            type="input"
                            field="name"
                            decorator={{
                                initialValue: data.name,
                                rules: [
                                    { required: false, message: '品牌名称不能为空！' },
                                    { max: 80, message: '最多80个字符！' },
                                ],
                            }}
                        />

                        <FormElement
                            label="品牌描述"
                            type="input"
                            field="description"
                            decorator={{
                                initialValue: data.description,
                                rules: [
                                    { required: false, message: '品牌描述不能为空！' },
                                    { max: 512, message: '最多512个字符！' },
                                ],
                            }}
                        />
                    </Form>
                </Spin>
            </Modal>
        );
    }
}
