package com.staceyho.springbootmall.service;

import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;

public interface ProductService {

    Product getProductByID(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
