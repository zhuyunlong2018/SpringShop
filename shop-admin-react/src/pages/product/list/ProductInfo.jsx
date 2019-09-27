import React, { Component } from 'react';
import { Form, Avatar } from 'antd';
import { FormElement } from '@/library/antd';
import ImagesUpload from '@/components/images-upload'
import { fileSrc } from '@/commons';
@Form.create()
export default class ProductInfo extends Component {

    state = {
        uploadVisible: false,
    }

    componentDidMount() {
        this.props.onRef(this)
    }

    /**
     * 处理提交保存
     */
    handleSubmit = (call) => {
        const { form: { validateFieldsAndScroll } } = this.props;
        let hasError = false
        //表单验证
        validateFieldsAndScroll((err, values) => {
            if (err) hasError = true
            if (call) call({ ...values })
        });
        return hasError
    };

        /**
     * 处理选中图片后回调
     * @param {Object} images 
     */
    handelSelectImages(images) {
        if (images.length > 0) {
            const {data, setData, form: {setFieldsValue}} = this.props
            const selectImage = images[0]
            setFieldsValue({imageId: selectImage.id})
            setData({...data, image:selectImage})
        }
        this.setState({ uploadVisible: false })
    }

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props} />;

    render() {
        const { data } = this.props;
        const {uploadVisible} = this.state
        const FormElement = this.FormElement;
        const imgSrc = data.mainImage ? fileSrc(data.mainImage) : '';
        return (
            <Form>
                {data.id ? (<FormElement type="hidden" field="id" decorator={{ initialValue: data.id }} />) : null}
                <FormElement
                    label="商品标题"
                    type="input"
                    field="title"
                    decorator={{
                        initialValue: data.title,
                        rules: [
                            { required: true, message: '商品标题不能为空！' },
                            { max: 100, message: '最多100个字符！' },
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
                            { required: true, message: '商品卖点不能为空！' },
                            { max: 500, message: '最多500个字符！' },
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
                            { required: true, message: '价格区间不能为空！' },
                            { max: 128, message: '最多128个字符！' },
                        ],
                    }}
                />

                <FormElement
                    label="商品主图"
                    type="input-hidden"
                    field="image"
                    decorator={{
                        initialValue: data.imageId,
                        rules: [
                            { required: true, message: '商品图片不能为空！' },
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
                    label="所属类目，叶子类目"
                    type="select-tree"
                    field="categoryId"
                    decorator={{
                        initialValue: data.categoryId,
                        rules: [
                            { required: true, message: '所属类目，叶子类目不能为空！' },
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
                            { required: true, message: '所属品牌不能为空！' },
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
                            { required: true, message: '商品状态，1-正常，2-下架，3-删除不能为空！' },
                        ],
                    }}
                />

            </Form>
        );
    }
}
