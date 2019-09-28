import { sxAjax } from "@/commons/ajax"

//分页获取列表分页
export const getList = params => sxAjax.get("admin/brands/list", params)

//添加
export const add = params => sxAjax.post("admin/brands/add", params)

//编辑
export const edit = params => sxAjax.put("admin/brands/edit", params)

//删除
export const del = params => sxAjax.del("admin/brands/del", params)

//获取所有品牌列表
export const all = params => sxAjax.get("admin/brands/all", params)