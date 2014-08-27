package com.daren.gis.web.bootup.wicket.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @类描述：OSGI初始化类
 * @创建人：sunlf
 * @创建时间：2014-04-16 下午3:05
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class InitActivator implements BundleActivator {
    private static BundleContext context;

    /**
     * 获取Bundle上下文
     *
     * @return Bundle上下文
     */
    public static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * @see org.osgi.tp.BundleActivator#start(org.osgi.tp.BundleContext)
    */
    public void start(BundleContext bundleContext) throws Exception {
        InitActivator.context = bundleContext;
//        注册一个测试filter
//        DelegatingFilterFactory delegatingFilterFactory = new DelegatingFilterFactory(bundleContext, "daren.project.gis", 2);
//        delegatingFilterFactory.register();
    }

    /*
     * (non-Javadoc)
     * @see org.osgi.tp.BundleActivator#stop(org.osgi.tp.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        InitActivator.context = null;
    }
}
