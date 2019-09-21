import React, { Component } from 'react';
import { Modal, Form, Spin } from 'antd';
import { FormElement } from '@/library/antd';
import { addClass, editClass } from '@/api/image'


@Form.create()
export default class ClassEdit extends Component {
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
            this.setState({ data: formData })
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
            const ajax = id ? editClass(params) : addClass(params);
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
        const title = data.id ? '修改分类名' : '添加分类';
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
                            label="标题"
                            type="input"
                            field="title"
                            decorator={{
                                initialValue: data.title,
                                rules: [
                                    { required: true, message: '分类标题不能为空！' },
                                    { max: 60, message: '最多60个字符！' },
                                ],
                            }}
                        />
                    </Form>
                </Spin>
            </Modal>
        );
    }
}
