import { sxAjax } from "@/commons/ajax"

//获取所有用户列表
export const getUsers = params => sxAjax.get("admin/member/getList", params)

//根据id获取一名用户信息
export const getUser = params => sxAjax.get("admin/member/getInfo", params)

//添加一名用户
export const add = params => sxAjax.post("admin/member/add", params)

//编辑一名用户
export const edit = params => sxAjax.put("admin/member/edit", params)

//删除一名用户
export const del = params => sxAjax.del("admin/member/del", params)