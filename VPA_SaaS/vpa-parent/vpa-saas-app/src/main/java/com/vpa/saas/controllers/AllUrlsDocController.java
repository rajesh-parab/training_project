package com.vpa.saas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class AllUrlsDocController {
    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public AllUrlsDocController(RequestMappingHandlerMapping handlerMapping){
        this.handlerMapping = handlerMapping;

    }

    @RequestMapping(value = "/allEndPoints")
    public Map allUrls() {
        Map<Set<String>, String> result = new HashMap<Set<String>, String>();

        Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            result.put(entry.getKey().getPatternsCondition().getPatterns(), entry.getValue().getBean().toString());
        }
        return result;
    }
}
