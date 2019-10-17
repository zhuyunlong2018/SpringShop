import { sxAjax } from "@/commons/ajax"

//获取列表分页
export const list = params => sxAjax.get("admin/categories/list", params)

//添加
export const add = params => sxAjax.post("admin/categories/add", params)

//编辑
export const edit = params => sxAjax.put("admin/categories/edit", params)

//删除
export const del = params => sxAjax.del("admin/categories/del", params)

//通过等级获取列表
export const listByLevels = params => sxAjax.get("admin/categories/listByLevels", params)

//保存类目的属性组
export const saveAttributes = params => sxAjax.post("admin/categories/saveAttributes", params)

//通过上级ID获取携带属性对象的列表
export const fetchWithParamsByPid = params => sxAjax.get("admin/categories/fetchWithParamsByPid", params)