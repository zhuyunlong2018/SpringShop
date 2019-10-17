package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bianquan.springShop.entity.admin.ImageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 商品表 实体类
 * @author zhuyunlong2018
 * @since 2019-09-19
 */
@Data
@FieldNameConstants(prefix = "")
@TableName("sp_products")
@Document(indexName = "sp_products", type = "doc")
@ApiModel(value="ProductsEntity对象")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 5650088466120913205L;

    @Id
    @TableId
    @ApiModelProperty(value = "商品id，同时也是商品编号,新增不传",example="1")
    private Long id;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品卖点")
    private String sellPoint;

    @ApiModelProperty(value = "价格区间")
    private String priceRange;

    @ApiModelProperty(value = "商品图片")
    private Long imageId;

    //主图对象
    @TableField(exist = false)
    private ImageEntity mainImage;

    @ApiModelProperty(value = "所属类目，叶子类目",example="1")
    private Long categoryId;

    @ApiModelProperty(value = "所属品牌",example="1")
    private Long brandId;

    @ApiModelProperty(value = "商品状态，1-正常，2-下架，3-删除",example="1")
    private Integer status;

    //商品品牌
    @TableField(exist = false)
    private BrandEntity brand;

    //商品详情
    @TableField(exist = false)
    private ProductDescEntity desc;

    //商品参数
    @TableField(exist = false)
    private ProductAttributesEntity attributes;

    //sku参数
    @TableField(exist = false)
    private List<ProductsSkuEntity> sku;

    //分类对象
    @TableField(exist = false)
    private CategoryEntity category;

    //商品分类属性参数
    @TableField(exist = false)
    private CategoryAttributeEntity categoryAttributes;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
