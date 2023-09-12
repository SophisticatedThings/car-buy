package artem.strelcov.controllers;

import artem.strelcov.dto.ProductRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.services.ProductService;
import artem.strelcov.services.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
    @PostMapping
    public ResponseEntity<Product> addProduct(
            Authentication authentication,
            @RequestParam(value = "image") MultipartFile image,
            @RequestPart(value = "product") ProductRequest productRequest){
        productService.addProduct(authentication,image, productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            Authentication authentication,
            @PathVariable("id") String id,
            @RequestBody ProductRequest productRequest) {
        productService.updateProduct(authentication,id,productRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    /*@GetMapping("/{brand}")
    public ResponseEntity<Product> getProductByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(productService.getProductByBrand(brand));
    } */
    @GetMapping("/{car-id}")
    public String getOwnerEmailByCarId(@PathVariable("car-id") String carId) {
        return productService.getOwnerEmailByCarId(carId);
    }


}
