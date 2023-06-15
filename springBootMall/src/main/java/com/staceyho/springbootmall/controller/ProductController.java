package com.staceyho.springbootmall.controller;

import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;
import com.staceyho.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){

        List<Product> productList = productService.getProducts();

            return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

//    查詢商品功能
//   @PathVariable 可以接住url路徑上的值，只能加在方法上的參數內
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product = productService.getProductByID(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    新增商品功能
//    @Valid 可以讓@NotNull註解生效使用
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductByID(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

//   修改商品功能
//   @PathVariable 可以接住url路徑上的值，只能加在方法上的參數內
//   @Valid 可以讓@NotNull註解生效使用
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        //檢查product是否存在
        Product product = productService.getProductByID(productId);

        //存在，修改商品數據
        if (product != null) {
            productService.updateProduct(productId, productRequest);

            Product updatedProduct = productService.getProductByID(productId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

            //不存在，返回404Not Found給前端
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
