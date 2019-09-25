package com.bianquan.springShop.common.utils;

import com.bianquan.springShop.entity.admin.ImageEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传工具包
 */
@Component
@Data
public class FileUtil {
    @Value("${bianquan.upload.path}")
    private String path;
    /**
     * @param file 文件
     * @return
     */
    public boolean upload(MultipartFile file, ImageEntity img) {

        //获取图片类型
        String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //使用工具类UUID给图片重命名
        String name = UUID.randomUUID().toString().replaceAll("-", "") + exName;

        //为了便于我们查找图片，将保存路径以 年 / 月 / 日 / 的格式命名
        StringBuilder realPath = new StringBuilder(DateUtil.getYear() + File.separator +
                DateUtil.getMonth() + File.separator +
                DateUtil.getDay() + File.separator);
        realPath.append(name);
        String fileFullName = realPath.toString();
        String dirPath = path + fileFullName;
        img.setDir(dirPath);
        img.setSrc(fileFullName);
        File dest = new File(dirPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
