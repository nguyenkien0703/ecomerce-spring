//package com.ecommerce.shopapp.components.converts;
//
//import com.ecommerce.shopapp.entity.Category;
//import org.springframework.kafka.support.converter.JsonMessageConverter;
//import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
//import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//
//@Component
//
//public class CategoryMessageConverter extends JsonMessageConverter {
//    public CategoryMessageConverter() {
//        super();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//        typeMapper.addTrustedPackages("com.ecommerce.shopapp");
//        typeMapper.setIdClassMapping(Collections.singletonMap("category", Category.class));
//        this.setTypeMapper(typeMapper);
//    }
//}
