import { sxAjax } from "@/commons/ajax"

//获取所有图片分类
export const getClass = params => sxAjax.get("admin/imagesClass/list", params)

//添加图片分类
export const addClass = params => sxAjax.post("admin/imagesClass/add", params)

//编辑图片分类
export const editClass = params => sxAjax.put("admin/imagesClass/edit", params)

//删除图片分类
export const delClass = params => sxAjax.del("admin/imagesClass/del", params)

//获取图片列表
export const list = params => sxAjax.get("admin/images/list", params)

//添加图片
export const add = params => sxAjax.post("admin/images/add", params)

//删除图片
export const del = params => sxAjax.del("admin/images/del", params)