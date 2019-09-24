
/**
 * 环境配置
 */
const env = process.env

const properties = {
    env: env.NODE_ENV,  //当前环境
    api: env.REACT_APP_API, //后端请求地址
    upload: env.REACT_APP_API + env.REACT_APP_UPLOAD,   //后端本地服务器图片保存路劲
}
export default properties