package com.staceyho.springbootmall.service.impl;

import com.staceyho.springbootmall.dao.ProductDao;
import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;
import com.staceyho.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductByID(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
