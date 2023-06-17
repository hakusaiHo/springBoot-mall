package com.staceyho.springbootmall.controller;

import com.staceyho.springbootmall.constant.ProductCategory;
import com.staceyho.springbootmall.dto.PorductQueryParams;
import com.staceyho.springbootmall.dto.ProductRequest;
import com.staceyho.springbootmall.model.Product;
import com.staceyho.springbootmall.service.ProductService;
import com.staceyho.springbootmall.utill.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated //@RequestParam 使驗證請求參數的註解生效
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表
    //@RequestParam 取得 url 裡面，路徑下所設定好的參數
    //@RequestParam(defaultValue = value) 提供預設定義好的值
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, //取得幾筆商品數據
            @RequestParam(defaultValue = "0") @Min(0) Integer offset //要跳過多少筆數據
    ){

        PorductQueryParams productQueryParams = new PorductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得productList(商品列表數據)
        List<Product> productList = productService.getProducts(productQueryParams);

        //取得product總筆數
        Integer total = productService.countProduct(productQueryParams);

        //分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

            return ResponseEntity.status(HttpStatus.OK).body(page);
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
