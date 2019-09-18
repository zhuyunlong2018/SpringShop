import { sxAjax } from "@/commons/ajax"

//获取列表分页
export const list = params => sxAjax.get("admin/categories/list", params)

//添加
export const add = params => sxAjax.post("admin/categories/add", params)

//编辑
export const edit = params => sxAjax.put("admin/categories/edit", params)

//删除
export const del = params => sxAjax.del("admin/categories/del", params)