package com.xky.mall.service;

import com.xky.mall.model.pojo.Category;
import com.xky.mall.model.request.AddCategoryReq;

public interface CategoryService {
    void addCategory(AddCategoryReq addCategoryReq);

    void updataCategory(Category category);
}