package com.toto;

import com.toto.filter.EncodingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@SpringBootApplication
@ServletComponentScan
public class DemoApplication {
	@Bean
	public FilterRegistrationBean buildFilterObject(){
		FilterRegistrationBean filter=new FilterRegistrationBean();
		filter.setFilter(new EncodingFilter());
		filter.addUrlPatterns("/*");
		filter.addInitParameter("encoding","utf-8");
		filter.setName("Encoder");
		filter.setOrder(1);
		System.out.println("FilterRegistrationBean===============恩这个OK");
		return  filter;
	}
	@Bean(value = "Encoder")
	public Filter encodingFilter(){
		return  new EncodingFilter();
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
