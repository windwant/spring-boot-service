package org.windwant.spring.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.hibernate.validator.HibernateValidator;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.*;
import org.windwant.spring.core.interceptor.BootInterceptor;
import org.windwant.spring.core.mybatis.MapperScannerConfigurerProxy;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */
@Configuration
public class ApplicationConfig {

    @Configuration
    public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

        /**
         * 自定义拦截器
         * @param registry
         */
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new BootInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        }

        /**
         * 跨域处理 映射所有路径 允许所有来源 以下方法请求
         * @param registry
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH");
        }

        /**
         * 初始页面
         * @param registry
         */
        @Override
        public void addViewControllers(ViewControllerRegistry registry ) {
            registry.addViewController("/").setViewName( "forward:/index.html" );
            registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
            super.addViewControllers(registry);
        }

//        @Override
//        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//            converters.add(new FastJsonHttpMessageConverter());
//            converters.add(new ByteArrayHttpMessageConverter());
//            super.configureMessageConverters(converters);
//        }
    }

    /**
     * mybatis mapper 扫描
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurerProxy mapperScannerConfigurerProxy = new MapperScannerConfigurerProxy();
        mapperScannerConfigurerProxy.setBasePackage("org.windwant.spring.mapper");
        return mapperScannerConfigurerProxy;
    }

    /**
     * 验证信息 message
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
        rrbms.setBasename("classpath:/lang/messages");
        rrbms.setUseCodeAsDefaultMessage(false);
        rrbms.setDefaultEncoding("UTF-8");
        localValidatorFactoryBean.setValidationMessageSource(rrbms);
        return localValidatorFactoryBean;
    }
}
