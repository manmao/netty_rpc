package com.patterncat.rpc.spring;

import com.patterncat.rpc.proxy.RpcProxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;

/**
 * https://github.com/wcong/learn-java/blob/9765f86851b5ae84c322f6c924b92c4870a31174/src/main/java/org/wcong/test/spring/CustomizeScanTest.java
 * Created by patterncat on 2016-04-10.
 */
public class SpringProxyFactoryBean<T> implements InitializingBean, FactoryBean<T> {

    private String innerClassName;

    private int timeoutInMillis;

    public void setInnerClassName(String innerClassName) {
        this.innerClassName = innerClassName;
    }

    @Override
    public T getObject() throws Exception {
        Class innerClass = Class.forName(innerClassName);
        return (T)Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{innerClass}, new RpcProxy());
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(innerClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
