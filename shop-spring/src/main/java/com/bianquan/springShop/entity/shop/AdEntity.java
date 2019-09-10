package com.bianquan.springShop.entity.shop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@FieldNameConstants(prefix = "")
@TableName("sp_ad")
public class AdEntity implements Serializable {
    private static final long serialVersionUID = -1283297748653269350L;

    @TableId
    private Integer id;

    //广告位置ID
    private Integer positionId;

    //标题
    private String title;

    //链接
    private String link;

    //图片地址
    private String image;

    //内容
    private String content;
}
