package com.staceyho.springbootmall.dao;

import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

}
