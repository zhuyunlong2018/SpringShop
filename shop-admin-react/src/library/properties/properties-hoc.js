
import React, {Component} from 'react';
import properties from './index'

/**
 * 环境配置装饰器
 */
export const propertiesHoc = ({propName = 'properties'} = {}) => WrappedComponent => {
    class WithSubscription extends Component {
        constructor(props) {
            super(props);
            this.properties = properties || {};
        }

        static displayName = `WithSubscription(${WrappedComponent.displayName || WrappedComponent.name || 'Component'})`;

        render() {
            const injectProps = {
                [propName]: this.properties,
            };
            return <WrappedComponent {...injectProps} {...this.props}/>;
        }
    }

    return WithSubscription;
};



