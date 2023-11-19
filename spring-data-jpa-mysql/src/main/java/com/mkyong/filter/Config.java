//package com.mkyong.filter;
//
//import com.mkyong.filter.LogHandleFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.util.Optional;
//
//@Configuration
//public class Config {
//
//    @Bean
//    public FilterRegistrationBean<LogHandleFilter> loggingFilter(){
//        FilterRegistrationBean<LogHandleFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new LogHandleFilter());
//        registrationBean.addUrlPatterns("/books/*");
//        return registrationBean;
//    }
//}