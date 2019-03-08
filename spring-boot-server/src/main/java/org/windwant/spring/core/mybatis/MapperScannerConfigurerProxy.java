package org.windwant.spring.core.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 扫描相应包路径下的接口（至少包含一个方法），注册为 MapperFactoryBean
 */
public class MapperScannerConfigurerProxy extends MapperScannerConfigurer {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        super.postProcessBeanDefinitionRegistry(registry);
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            if (!(beanDefinition instanceof GenericBeanDefinition)) {
                continue;
            }

            //用于用户自定义bean定义，
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinition;
            if (!genericBeanDefinition.hasBeanClass()) {
                continue;
            }

            if (genericBeanDefinition.getBeanClass() != MapperFactoryBean.class) {
                continue;
            }

            //用于MapperProxyFactoryBean定义，替换 MapperFactoryBean
            genericBeanDefinition.setBeanClass(MapperProxyFactoryBean.class);
            genericBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }
}
