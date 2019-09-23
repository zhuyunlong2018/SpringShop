package com.bianquan.springShop.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 图片存储管理 实体类
 * @author zhuyunlong2018
 * @since 2019-09-21
 */
 @Data
 @FieldNameConstants(prefix = "")
 @ApiModel(value="ImagesEntity对象")
 @TableName("sp_images")
public class ImageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = ",新增不传",example="1")
    private Long id;

    @ApiModelProperty(value = "图片标题")
    private String title;

    @ApiModelProperty(value = "图片关键词")
    private String keywords;

    @ApiModelProperty(value = "地址")
    private String src;

    @ApiModelProperty(value = "来源1本地服务器",example="1")
    private Integer origin;

    @ApiModelProperty(value = "大小")
    private long size;

    @ApiModelProperty(value = "链接数（被引用次数）",example="1")
    private Integer links;

    @ApiModelProperty(value = "存储路劲")
    private String dir;

    @ApiModelProperty(value = "归类",example="1")
    private Integer classification;

    @ApiModelProperty(value = "")
    private long createTime;

    @ApiModelProperty(value = "")
    private long updateTime;

}