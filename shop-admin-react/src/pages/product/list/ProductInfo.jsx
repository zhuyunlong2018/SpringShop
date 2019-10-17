import React, { Component } from 'react';
import { Form, Avatar } from 'antd';
import { FormElement } from '@/library/antd';
import ImagesUpload from '@/components/images-upload'
import { fileSrc } from '@/commons';

/**
 * 商品基础信息编辑页
 */
@Form.create()
export default class ProductInfo extends Component {

    state = {
        uploadVisible: false,
    }

    componentDidMount() {
        this.props.onRef(this)
    }

    /**
     * 处理信息填写完成后的校验
     */
    handleComplete = (call) => {
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
            const { data, setData, form: { setFieldsValue } } = this.props
            const selectImage = images[0]
            setFieldsValue({ imageId: selectImage.id })
            setData({ ...data, mainImage: selectImage })
        }
        this.setState({ uploadVisible: false })
    }

    FormElement = (props) => <FormElement form={this.props.form} labelWidth={100} {...props} />;

    render() {
        const { data, brandOptions, categoryTree, onLoadData, handleChangeCategory } = this.props;
        const { uploadVisible } = this.state
        const FormElement = this.FormElement;
        const imgSrc = data.mainImage ? fileSrc(data.mainImage) : '';
        return (
            <Form>
                {data.id ? (<FormElement type="hidden" field="id" decorator={{ initialValue: data.id }} />) : null}

                <FormElement
                    label="所属类目"
                    type="select-tree"
                    field="categoryId"
                    options={categoryTree}
                    showSearch={true}
                    treeDataSimpleMode
                    treeNodeFilterProp="title"
                    searchPlaceholder="搜索仅支持一、二级类目"
                    loadData={(node) => onLoadData(node)}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    decorator={{
                        initialValue: data.categoryId,
                        rules: [
                            { required: true, message: '所属类目不能为空！' },
                        ],
                    }}
                    onChange={categoryId => handleChangeCategory(categoryId)}
                />

                <FormElement
                    label="所属品牌"
                    type="select"
                    field="brandId"
                    optionFilterProp="label"
                    showSearch
                    options={brandOptions}
                    decorator={{
                        initialValue: data.brandId,
                        rules: [
                            { required: true, message: '所属品牌不能为空！' },
                        ],
                    }}
                />

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
                    field="imageId"
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
                    label="商品状态"
                    type="switch"
                    field="status"
                    decorator={{
                        initialValue: data.status,
                        rules: [
                            { required: true, message: '商品状态不能为空！' },
                        ],
                    }}
                />

            </Form>
        );
    }
}
