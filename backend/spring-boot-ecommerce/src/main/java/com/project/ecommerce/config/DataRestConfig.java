package com.project.ecommerce.config;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.*;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {


    private EntityManager entityManager;

    @Autowired
    public DataRestConfig(EntityManager theEntityManager){
        entityManager=theEntityManager;
    }

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


         exposeIds(config);       
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        //expose entity ids
        //get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an arraylist of entity types
        List<Class> entityClasses = new ArrayList<>();

        for(EntityType entityType:entities){
            entityClasses.add(entityType.getJavaType());
        }

        //expose entity ids for the array of entity/domain types
        Class[] domainTypes=entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}
