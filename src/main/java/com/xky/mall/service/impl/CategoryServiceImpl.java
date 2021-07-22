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
import org.springframework.util.StringUtils;

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
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CATEGORY_ADD_F);
        }
    }

    @Override
    public void updataCategory(Category category) {
        //名称如果修改，不能重复
        if (StringUtils.hasLength(category.getName())) {
            Category oldCategory = categoryMapper.selectByName(category.getName());
            if (oldCategory != null && !category.getId().equals(oldCategory.getId())) {
                //查到记录，又不是一条，重复
                throw new MallException(MallExceptionEnum.CATEGORY_NAME_REPEAT);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (count == 0) {
            //更新失败
            throw new MallException(MallExceptionEnum.CATEGORY_UPDATA_F);
        }
    }
}
