package com.bianquan.springShop.common.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器
 */
@Slf4j
public class CodeGenerator {

    private CodeGeneConfig config;

    public CodeGenerator(CodeGeneConfig config) {
        this.config = mergeProps(config);
    }

    private CodeGeneConfig mergeProps(CodeGeneConfig config) {
        if (null == config) {
            config = new CodeGeneConfig();
        }
        Properties props = new Properties();
        try {
            props.load(CodeGenerator.class.getClassLoader().getResourceAsStream("gene.properties"));
        } catch (IOException e) {
            log.error("CodeGenerator读取配置失败", e);
        }

        if (StringUtils.isBlank(config.getDsUrl())) {
            config.setDsUrl(props.getProperty(CodeGeneProps.DS_URL));
        }
        if (StringUtils.isBlank(config.getDsUsername())) {
            config.setDsUsername(props.getProperty(CodeGeneProps.DS_USERNAME));
        }
        if (StringUtils.isBlank(config.getDsPassword())) {
            config.setDsPassword(props.getProperty(CodeGeneProps.DS_PASSWORD));
        }

        if (StringUtils.isBlank(config.getTablePrefix())) {
            config.setTablePrefix(props.getProperty(CodeGeneProps.TABLE_PREFIX));
        }
        if (StringUtils.isBlank(config.getPackageParent())) {
            config.setPackageParent(props.getProperty(CodeGeneProps.PKG_PARENT));
        }

        if (StringUtils.isBlank(config.getEntityName())) {
            config.setEntityName(props.getProperty(CodeGeneProps.ENTITY_NAME));
        }
        if (StringUtils.isBlank(config.getMapperName())) {
            config.setMapperName(props.getProperty(CodeGeneProps.MAPPER_NAME));
        }
        if (StringUtils.isBlank(config.getXmlName())) {
            config.setXmlName(props.getProperty(CodeGeneProps.XML_NAME));
        }
        if (StringUtils.isBlank(config.getServiceName())) {
            config.setServiceName(props.getProperty(CodeGeneProps.SERVICE_NAME));
        }
        if (StringUtils.isBlank(config.getServiceImplName())) {
            config.setServiceImplName(props.getProperty(CodeGeneProps.SERVICE_IMPL_NAME));
        }
        if (StringUtils.isBlank(config.getControllerName())) {
            config.setControllerName(props.getProperty(CodeGeneProps.CONTROLLER_NAME));
        }
        if (StringUtils.isBlank(config.getAuthor())) {
            config.setAuthor(props.getProperty(CodeGeneProps.AUTHOR));
        }
        return config;
    }


    public void execute() {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(config.getAuthor());
        globalConfig.setOpen(false);
        globalConfig.setSwagger2(true); //实体属性 Swagger2 注解
        globalConfig.setEntityName(config.getEntityName());
        globalConfig.setMapperName(config.getMapperName());
        globalConfig.setXmlName(config.getXmlName());
        globalConfig.setServiceName(config.getServiceName());
        globalConfig.setServiceImplName(config.getServiceImplName());
        globalConfig.setControllerName(config.getControllerName());
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(config.getDsUrl());
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername(config.getDsUsername());
        dataSourceConfig.setPassword(config.getDsPassword());
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("请输入模块名"));
        packageConfig.setParent(config.getPackageParent());
        generator.setPackageInfo(packageConfig);

        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setXml("templates/mapper.xml");
//         templateConfig.setMapper("templates/mapper.xml");
        // templateConfig.setService();
        templateConfig.setController("templates/controller.java");
        generator.setTemplate(templateConfig);


        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_XML;
            }
        });

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                // 用来作为类注释的时间，模板中通过${cfg.datetime}获取
                map.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.setMap(map);
            }
        };

        injectionConfig.setFileOutConfigList(focList);
        generator.setCfg(injectionConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        if (StringUtils.isNotBlank(config.getEntityName())) {
            strategy.setSuperEntityClass(config.getEntityName());
        }
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("请输入表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(config.getTablePrefix());
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


}
