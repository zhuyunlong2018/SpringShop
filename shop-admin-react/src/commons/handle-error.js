import {notification} from 'antd';
import {toLogin} from './index';

/**
 * 尝试获取错误信息 errorTio > resData.message > error.message > '未知系统错误'
 *
 * @param error
 * @param errorTip
 * @returns {*}
 */
function getErrorTip({error, errorTip}) {

    if (errorTip && errorTip !== true) return errorTip;
    console.log(error)
    if (error && error.msg) {
        const {code, msg} = error;

        if (code === 4002) { // 需要登录
            return toLogin();
        }
        // 后端自定义信息
        if (msg) return msg;

        if (code === 403) {
            return '您无权访问';
        }

        if (code === 404) {
            return '您访问的资源不存在';
        }

        if (code === 504) {
            return '服务器繁忙';
        }

        if (code === 500) {
            return '服务器繁忙';
        }
    }

    if (error && error.message && error.message.startsWith('timeout of')) return "请求超时";

    if (error) return error.message;

    return '服务器繁忙';
}

export default function handleError({error, errorTip}) {

    if (errorTip === false) return;
    const description = getErrorTip({error, errorTip});
    console.log(description)
    notification.error({
        message: '操作失败',
        description,
        duration: 1.5,
    });
}
