
import {fileSrc} from '@/commons'

/**
 * 全局state
 */
export default {
    initialState: {
        previewVisible: false,    // 全局显示图片预览版框
        previewImage: '',          // 需要预览图片地址
        previewAlt: "图片无法显示",         //  预览图的标注说明
    },

    //根据图片SRC显示预览图
    showPreviewVisible: (src) => ({previewVisible: true, previewImage: src}),
    
    //关闭预览
    hidePreviewVisible: () => ({previewVisible: false, previewImage: ''}),

    //根据图片对象显示预览图
    showPreviewForFile: file => ({previewVisible: true, previewImage: fileSrc(file)}),
}
