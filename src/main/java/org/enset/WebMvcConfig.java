package org.enset;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

@Override
public void addViewControllers(ViewControllerRegistry registry) {

//Pour lier entre les vues et urls
//liaison obligée pr chaque vue
registry.addViewController("/loginUrl").setViewName("login");  
registry.addViewController("/logout").setViewName("logout");

}



}