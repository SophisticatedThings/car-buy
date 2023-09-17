package artem.strelcov.controllers;

import artem.strelcov.dto.ProductRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.services.ProductService;
import artem.strelcov.services.ProductServiceImpl;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MinioClient minioClient;
    @GetMapping
    public List<ProductResponse> getAllProducts(){

        return productService.getAllProducts();
    }
    @GetMapping("/image/{image-url}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("image-url") String url) {
        byte[] image = productService.getImage(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
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
    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @PathVariable("id") String id
    ) {
       return productService.getProduct(id);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById(
            Authentication authentication,
            @PathVariable("id") String id) {
        productService.deleteProductById(authentication, id);
    }
    /*@GetMapping("/{brand}")
    public ResponseEntity<Product> getProductByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(productService.getProductByBrand(brand));
    } */
    /*@GetMapping("/{car-id}")
    public String getOwnerEmailByCarId(@PathVariable("car-id") String carId) {
        return productService.getOwnerEmailByCarId(carId);
    } */
    @DeleteMapping()
    public void deleteProductsWhereIsConfirmedNotNull() {
        productService.deleteProductsWhereIsConfirmedNotNull();
    }

}
