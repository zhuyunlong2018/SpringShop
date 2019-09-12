import { sxAjax } from "@/commons/ajax"

//刷新后台缓存
export const refreshCache = params => sxAjax.get("admin/system/refreshCache", params)