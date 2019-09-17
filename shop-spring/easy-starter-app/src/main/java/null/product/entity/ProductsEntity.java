package null.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 商品表 实体类
 * @author zhuyunlong2018
 * @since 2019-09-16
 */
 @Data
 @FieldNameConstants(prefix = "")
 @ApiModel(value="ProductsEntity对象")
 @TableName("sp_products")
public class ProductsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String image;

    @ApiModelProperty(value = "所属类目，叶子类目",example="1")
    private Long categoryId;

    @ApiModelProperty(value = "所属品牌",example="1")
    private Long brandId;

    @ApiModelProperty(value = "商品状态，1-正常，2-下架，3-删除",example="1")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}