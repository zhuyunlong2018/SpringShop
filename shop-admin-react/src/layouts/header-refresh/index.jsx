import React, { Component } from 'react';
import { Icon, Tooltip } from 'antd';
import config from '@/commons/config-hoc';
import { hasPermission } from '@/commons';
import { refreshCache } from '@/api/system'

/**
 * 刷新后台缓存
 */
@config({
    event: true,
})
export default class HeaderRefresh extends Component {
    state = {
        spin: false,
        toolTipVisible: false,
    };

    componentDidMount() {
        this.props.addEventListener(document, 'click', () => this.handleToolTipHide(0));
    }

    /**
     * 发送清理缓存请求
     */
    handleRefreshClick = () => {
        if (!this.state.spin) {
            this.setState({ spin: true })
            refreshCache().finally(() => {
                setTimeout(() => {
                    this.setState({ spin: false })
                }, 3000)
            })
        }
    };

    /**
     * 显示提示文字
     */
    handleToolTipShow = () => {
        if (this.ST) clearTimeout(this.ST);
        this.setState({ toolTipVisible: true });
    };

    /**
     * 隐藏提示文字
     */
    handleToolTipHide = (time = 300) => {
        this.ST = setTimeout(() => {
            this.setState({ toolTipVisible: false })
        }, time);
    };

    render() {
        const { className } = this.props;
        const { spin, toolTipVisible } = this.state;
        const permission = hasPermission('admin:system:refreshCache')
        return (
            <div
                style={{ padding: '0 16px', display: permission ? 'inherit' : 'none' }}
                className={className}
                onClick={this.handleRefreshClick}
                onMouseEnter={this.handleToolTipShow}
                onMouseLeave={() => this.handleToolTipHide()}
            >
                <Tooltip visible={toolTipVisible} placement="bottom" title="刷新缓存">
                    <div style={{ height: '30px', lineHeight: '30px', fontSize: 18, paddingTop: 2 }}>
                        <Icon type="sync" spin={spin} />
                    </div>
                </Tooltip>
            </div>
        );
    }
}
