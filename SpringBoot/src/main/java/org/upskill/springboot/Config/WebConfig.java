package org.upskill.springboot.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up resource handlers in the web application.
 * This class configures how static resources, like images or files, are served.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the resource handler to serve files located in the "uploads" directory.
     * This method maps requests to "/uploads/**" to the file system location "uploads/"
     * so that files can be accessed from the browser or external resources.
     *
     * @param registry The ResourceHandlerRegistry used to register the resource handler.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}