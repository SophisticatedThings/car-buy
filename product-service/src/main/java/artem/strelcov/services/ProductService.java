package artem.strelcov.services;

import artem.strelcov.dto.ProductDto;
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
    public byte[] getImage(String imageUrl);
    public void updateProduct(Authentication authentication,
                              String id,
                              ProductRequest productRequest);
    public void deleteProductById(Authentication authentication, String id);
    public Product getProductByBrand(String brand);

    String getOwnerEmailByCarId(String carId);

    void deleteProductsWhereIsConfirmedNotNull();

    ProductResponse getProduct(String id);
}
