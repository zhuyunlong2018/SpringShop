package com.bianquan.springShop.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片分类表 实体类
 * @author zhuyunlong2018
 * @since 2019-09-21
 */
 @Data
 @FieldNameConstants(prefix = "")
 @ApiModel(value="ImagesClassEntity对象")
 @TableName("sp_images_class")
public class ImagesClassEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = ",新增不传",example="1")
    private Integer id;

    @ApiModelProperty(value = "分类标题")
    private String title;

    @ApiModelProperty(value = "总数量",example="1")
    private Integer number;

    @ApiModelProperty(value = "总暂用存储量")
    private String size;

    @ApiModelProperty(value = "")
    private Date createTime;

    @ApiModelProperty(value = "")
    private Date updateTime;

}