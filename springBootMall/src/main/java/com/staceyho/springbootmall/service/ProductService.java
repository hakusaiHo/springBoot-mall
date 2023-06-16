package com.staceyho.springbootmall.service;

import com.staceyho.springbootmall.dto.PorductQueryParams;
import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(PorductQueryParams productQueryParams);

    Product getProductByID(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
