import { notification } from 'antd';
import { toLogin } from './index';

/**
 * 尝试获取错误信息 errorTio > resData.message > error.message > '未知系统错误'
 *
 * @param err
 * @param errorTip
 * @returns {*}
 */
function getErrorTip({ error, errorTip }) {

    if (errorTip && errorTip !== true) return errorTip;

    const { status, data } = error

    if (data && data.msg) {
        const { code, msg } = data;
        if (code === 4001) { // 需要登录
            setTimeout(() => toLogin(), 3000)
        }
        // 后端自定义信息
        if (msg) return msg;
    }

    if (status) {
        switch (status) {
            case 401:
                return '未授权访问';
            case 403:
                return '您无权访问';
            case 404:
                return '您访问的资源不存在';
            case 500:
                return '服务器繁忙';
            case 504:
                return '服务器繁忙';
            default:
                return '服务器繁忙';
        }
    }


}

export default function handleError({ error, errorTip }) {

    if (errorTip === false) return;
    const description = getErrorTip({ error, errorTip });
    console.log(description)
    notification.error({
        message: '操作失败',
        description,
        duration: 1.5,
    });
}
