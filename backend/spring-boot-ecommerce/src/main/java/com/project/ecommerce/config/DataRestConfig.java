package com.project.ecommerce.config;

import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] unsupportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};

        config.getExposureConfiguration().forDomainType(Product.class)
                .withItemExposure((metaData, httpMethods)-> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metaData, httpMethods)-> httpMethods.disable(unsupportedActions));

        config.getExposureConfiguration().forDomainType(ProductCategory.class)
                .withItemExposure((metaData, httpMethods)-> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metaData, httpMethods)-> httpMethods.disable(unsupportedActions));
    }
}
