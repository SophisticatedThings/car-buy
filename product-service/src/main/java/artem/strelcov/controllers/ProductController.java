package artem.strelcov.controllers;

import artem.strelcov.dto.ProductRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.services.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productServiceImpl.getAllProducts();
    }
    @PostMapping
    public ResponseEntity<Product> addProduct(
            Authentication authentication,
            @RequestParam(value = "image") MultipartFile image,
            @RequestPart(value = "product") ProductRequest productRequest){
        productServiceImpl.addProduct(authentication,image, productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            Authentication authentication,
            @PathVariable("id") String id,
            @RequestBody ProductRequest productRequest) {
        productServiceImpl.updateProduct(authentication,id,productRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{brand}")
    public ResponseEntity<Product> getProductByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(productServiceImpl.getProductByBrand(brand));
    }



}
