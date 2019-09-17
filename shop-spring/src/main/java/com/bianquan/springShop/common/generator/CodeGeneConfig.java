package com.bianquan.springShop.common.generator;

import lombok.Data;

@Data
public class CodeGeneConfig {

    private String dsUrl;
    private String dsUsername;
    private String dsPassword;

    private String tablePrefix;

    private String packageParent;

    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;

    private String author;
}