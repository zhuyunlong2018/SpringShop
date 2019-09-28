import React, { Component } from 'react';
import { Modal, Form, Spin, Avatar } from 'antd';
import { FormElement } from '@/library/antd';
import { add, edit } from '@/api/category'
import ImagesUpload from '@/components/images-upload'

/**
 * 编辑框
 */
@Form.create()
export default class CategoryEdit extends Component {
    state = {
        loading: false,
        data: {},
        uploadVisible: false,
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
            const { id, status } = values;
            const params = { ...values, status: status ? 1 : 2 };
            // id存在未修改，不存在未添加
            const ajax = id ? edit(params) : add(params);
            console.log(params)
            this.setState({ loading: true });
            ajax.then((data) => {
                //保存成功，执行回调函数修改本地数据
                data.image = this.state.data.image
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

    /**
     * 处理选中图片后回调
     * @param {Object} images 
     */
    handelSelectImages(images) {
        if (images.length > 0) {
            const selectImage = images[0]
            this.props.form.setFieldsValue({imageId: selectImage.id})
            this.setState({data: {...this.state.data, image:selectImage}})
        }
        this.setState({ uploadVisible: false })
    }

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props} />;

    render() {
        const { visible, treeNode, form: { setFieldsValue }, fileSrc } = this.props;
        const { loading, data, uploadVisible } = this.state;
        const title = data.id ? '修改分类管理' : '添加分类管理';
        const imgSrc = data.image ? fileSrc(data.image) : '';
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
                            type="hidden"
                            field="level"
                            decorator={{
                                initialValue: data.level ? data.level : 1,
                            }}
                        />

                        <FormElement
                            label="父类目"
                            type="select-tree"
                            field="pid"
                            options={treeNode}
                            showSearch
                            dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                            wrapperStyle={{ flex: 0 }}
                            onChange={(value, label, extra) => {
                                setFieldsValue({ level: extra.triggerNode.props.level + 1 })
                            }}
                            treeNodeFilterProp="title"
                            decorator={{
                                initialValue: data.pid ? data.pid : '0',
                                rules: [
                                    { required: true, message: '父类目不能为空！' },
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
                            type="input-hidden"
                            field="imageId"
                            decorator={{
                                initialValue: data.imageId,
                                rules: [
                                    { required: true, message: '图片不能为空！' },
                                ],
                            }}
                        >
                            <Avatar shape="square" size={64} icon="picture" src={imgSrc}
                                onClick={() => this.setState({ uploadVisible: true })} />
                            <ImagesUpload visible={uploadVisible}
                                handleCancel={() => this.setState({ uploadVisible: false })}
                                handelSelectImages={images => this.handelSelectImages(images)} />
                        </FormElement>

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
                            checked={data.status === 1}
                            onClick={() => {
                                let status
                                if (data.status === 1) {
                                    status = 2
                                } else {
                                    status = 1
                                }
                                this.setState({ data: { ...data, status } })
                            }}
                            decorator={{
                                initialValue: data.status === 1,
                            }}
                        />

                    </Form>
                </Spin>
            </Modal>
        );
    }
}
