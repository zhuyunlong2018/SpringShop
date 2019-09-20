/**
 * 全局state
 */
export default {
    initialState: {
        previewVisible: false,    // 全局显示图片预览版框
        previewImage: '',          // 需要预览图片地址
        previewAlt: "图片无法显示",         //  预览图的标注说明
    },

    showPreviewVisible: (src) => ({previewVisible: true, previewImage: src}),
    hidePreviewVisible: () => ({previewVisible: false, previewImage: ''}),
}
