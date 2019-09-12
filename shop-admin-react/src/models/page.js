import {getCurrentLocal} from '@/i18n';

export default {
    initialState: {
        breadcrumbs: [],    // 面包屑数据 [{key, local, text, path}] 支持国际化
        title: '',          // 页面title {local, text, icon} 支持国际化
        showHead: true,     // 是否显示/隐藏页面头部
        loading: false,
    },

    showHead: () => ({showHead: true}),
    hideHead: () => ({showHead: false}),

    setTitle: (title) => {
        const local = getCurrentLocal();
        if (title && title.local) {
            const text = local.menu[title.local];
            if (text) title.text = text;
        }
        return {title};
    },

    /**
     * 切换语言时重置面包屑国际化
     */
    setBreadcrumbs: (breadcrumbs) => {
        //修复面包屑国际化
        const i18n = getCurrentLocal();
        if (breadcrumbs && breadcrumbs.length) {
            breadcrumbs.forEach(item => {
                const {local, key} = item
                if (local) {
                    const text = i18n.menu[key];
                    if (text) item.text = text;
                }
            })
        }
        return {breadcrumbs};
    },
    appendBreadcrumbs: (appendBreadcrumbs, state) => {
        console.log(appendBreadcrumbs)
        let {breadcrumbs = []} = state;
        breadcrumbs = breadcrumbs.concat(appendBreadcrumbs);
        return {breadcrumbs};
    },

    showLoading: () => ({loading: true}),
    hideLoading: () => ({loading: false}),
}
