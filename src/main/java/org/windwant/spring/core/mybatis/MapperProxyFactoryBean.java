package org.windwant.spring.core.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * Mapper 代理 工厂类，用于生成mapper接口相应的代理类
 *
 * 此处作为 MapperFactoryBean 的外一层包装
 * 内部处理一些MaperProxy相关的操作
 */
public class MapperProxyFactoryBean implements FactoryBean {

    private Class<?> mapperInterface;

    /**
     * 可以注入mapper接口的工厂类
     */
    private MapperFactoryBean mapperFactoryBean;

    public MapperProxyFactoryBean(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
        mapperFactoryBean = new MapperFactoryBean<>(mapperInterface);
    }

    /**
     * 返回mapper代理
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        mapperFactoryBean.afterPropertiesSet();
        //getObject(): getSqlSession().getMapper(this.mapperInterface);
        Object object = mapperFactoryBean.getObject();
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class<?>[]{mapperFactoryBean.getMapperInterface()},
                new MapperProxy(object, mapperInterface));
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public boolean isAddToConfig() {
        return mapperFactoryBean.isAddToConfig();
    }

    public void setAddToConfig(boolean addToConfig) {
        mapperFactoryBean.setAddToConfig(addToConfig);
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        mapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
