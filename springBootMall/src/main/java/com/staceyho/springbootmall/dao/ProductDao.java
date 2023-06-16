package com.staceyho.springbootmall.dao;

import com.staceyho.springbootmall.constant.ProductCategory;
import com.staceyho.springbootmall.dto.PorductQueryParams;
import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(PorductQueryParams productQueryParams);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
