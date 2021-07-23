package com.xky.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/20 7:42 下午
 */
@Component
public class Constants {
    public static final String SALT = "sdlkf#@]]12";
    /**
     * 登录缓存key
     */
    public static final String USER_LOGIN_CACHE_KEY = "user_login_cache_key";

    /**
     * 图片缓存的后端目录，放到配置文件
     */
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setUploadFileDir(String uploadFileDir) {
        FILE_UPLOAD_DIR = uploadFileDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc","price asc");
    }

    public interface SaleStatus{
        public Integer NOT_SALE = 0; //已下架
        public Integer SALE = 1; //上架
    }

    public interface Cart{
        public Integer CHECKED = 1; //已选择
        public Integer UNCHECKED = 0; //未选择
    }
}
