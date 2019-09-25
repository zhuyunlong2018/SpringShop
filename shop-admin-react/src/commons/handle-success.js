import {notification} from 'antd';

export default function handleSuccess({successTip}) {
    successTip && notification.success({
        message: '操作成功',
        description: successTip,
        duration: 1.5,
    })
}
