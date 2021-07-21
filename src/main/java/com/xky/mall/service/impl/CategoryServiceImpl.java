package com.xky.mall.service.impl;

import com.xky.mall.exception.MallException;
import com.xky.mall.exception.MallExceptionEnum;
import com.xky.mall.model.dao.CategoryMapper;
import com.xky.mall.model.pojo.Category;
import com.xky.mall.model.request.AddCategoryReq;
import com.xky.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiekongying
 * @version 1.0
 * @date 2021/7/21 5:19 下午
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     *
     * @param addCategoryReq
     */
    @Override
    public void addCategory(AddCategoryReq addCategoryReq) {
        //先判断下是否重名
        Category category = categoryMapper.selectByName(addCategoryReq.getName());
        if (category != null) {
            throw new MallException(MallExceptionEnum.CATEGORY_NAME_REPEAT);
        }
        Category newCategory = new Category();
        BeanUtils.copyProperties(addCategoryReq, newCategory);
        int count = categoryMapper.insertSelective(newCategory);
        if (count == 0){
            throw new MallException(MallExceptionEnum.CATEGORY_ADD_F);
        }
    }
}
