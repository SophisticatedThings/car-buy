package artem.strelcov.controllers;

import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public List<ProductResponse> getAllProducts(){

        return productService.getAllProducts();
    }
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{model}")
    public ResponseEntity<Product> getProductByModel(@PathVariable String model) {
        return ResponseEntity.ok(productService.getProductByModel(model));
    }



}
