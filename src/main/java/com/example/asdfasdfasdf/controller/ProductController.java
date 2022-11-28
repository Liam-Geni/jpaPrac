package com.example.asdfasdfasdf.controller;

import com.example.asdfasdfasdf.domain.MemberRoleEnum;
import com.example.asdfasdfasdf.domain.Product;
import com.example.asdfasdfasdf.dto.ProductMyPriceRequestDto;
import com.example.asdfasdfasdf.dto.ProductRequestDto;
import com.example.asdfasdfasdf.security.UserDetailsImpl;
import com.example.asdfasdfasdf.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //JSON으로 데이터를 주고받음을 선언
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    //신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto productRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        //로그인 되어 있는 회원 테이블 ID
        Long userId = userDetailsImpl.getMember().getId();
        return productService.createProduct(productRequestDto, userId);
    }

    //설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,
                              @RequestBody ProductMyPriceRequestDto productMypriceRequestDto){
        Product product = productService.updateProduct(id, productMypriceRequestDto);

        //응답 보내기
        return product.getId();
    }

    //로그인한 회원이 등록한 관심 상품 조회
    @GetMapping("/api/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){

        //로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetailsImpl.getMember().getId();
        return productService.getProducts(userId);
    }

    //(관리자용) 등록된 모든 상품 목록 조회
    @Secured(value = MemberRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/products")
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

}
