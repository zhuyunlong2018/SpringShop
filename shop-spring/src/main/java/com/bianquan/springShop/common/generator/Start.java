package com.bianquan.springShop.common.generator;

/**
 * 代码生成器入口
 */
public class Start {
    public static void main(String[] args) {
        CodeGeneConfig codeGeneConfig = new CodeGeneConfig();
        new CodeGenerator(codeGeneConfig).execute();
    }
}
