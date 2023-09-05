package artem.strelcov.services;

import artem.strelcov.dto.ProductRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public List<ProductResponse> getAllProducts();
    public void addProduct(Authentication authentication,
                           MultipartFile image,
                           ProductRequest productRequest);
    public void updateProduct(Authentication authentication,
                              String id,
                              ProductRequest productRequest);
    public Product getProductByBrand(String brand);
}
