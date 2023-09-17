package artem.strelcov.services;

import artem.strelcov.dto.ProductDto;
import artem.strelcov.dto.ProductRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.repositories.ProductRepository;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MinioClient minioClient;
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product ->
                        ProductResponse.builder()
                                .id(product.getId())
                                .brand(product.getBrand())
                                .model(product.getModel())
                                .amountOfSeats(product.getAmountOfSeats())
                                .driveType(product.getDriveType())
                                .prize(product.getPrize())
                                .gearBox(product.getGearBox())
                                .typeOfEngine(product.getTypeOfEngine())
                                .imageUrl(product.getImageUrl())
                                .owner(product.getOwner())
                                .build()
                ).toList();
    }
    public void addProduct(Authentication authentication,
                           MultipartFile image,
                           ProductRequest productRequest) {
        Product product = Product.builder()
                .owner(authentication.getName())
                .brand(productRequest.getBrand())
                .model(productRequest.getModel())
                .prize(productRequest.getPrize())
                .typeOfEngine(productRequest.getTypeOfEngine())
                .gearBox(productRequest.getGearBox())
                .driveType(productRequest.getDriveType())
                .amountOfSeats(productRequest.getAmountOfSeats())
                .build();
        addImageToMinioAndProduct(image, product);
        productRepository.insert(product);
    }

    @Override
    public byte[] getImage(String imageUrl) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("cars")
                        .object(imageUrl)
                        .build())) {
            byte [] image = stream.readAllBytes();
            return image;
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }

    private void addImageToMinioAndProduct(MultipartFile image, Product product) {
        String imageName = generateImageName(image);
        try (InputStream inputStream =
                     new BufferedInputStream(image.getInputStream())) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("cars")
                            .object(imageName)
                            .stream(inputStream, -1, 10485760)
                            .contentType("application/octet-stream")
                            .build());
            product.setImageUrl(imageName);
        } catch (IOException | ServerException | InsufficientDataException
                 | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException |
                 InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateImageName(MultipartFile image) {
        String extension = getExtension(image);
        return UUID.randomUUID() + "." + extension;
    }
    private String getExtension(MultipartFile image) {
        return image.getOriginalFilename()
                .substring(image.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }
    public void updateProduct(Authentication authentication,
                              String id,
                              ProductRequest productRequest) {
        Product savedProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Невозможно найти машину с айди %s", id)));
        if(!Objects.equals(savedProduct.getOwner(), authentication.getName()))
            throw new RuntimeException("Вы не являетесь владельцом автомобиля");
        savedProduct.setBrand(productRequest.getBrand());
        savedProduct.setModel(productRequest.getModel());
        savedProduct.setAmountOfSeats(productRequest.getAmountOfSeats());
        savedProduct.setDriveType(productRequest.getDriveType());
        savedProduct.setPrize(productRequest.getPrize());
        savedProduct.setGearBox(productRequest.getGearBox());
        savedProduct.setTypeOfEngine(productRequest.getTypeOfEngine());

        productRepository.save(savedProduct);
    }

    @Override
    public void deleteProductById(Authentication authentication, String id) {
        Optional<Product> product = productRepository.getProductById(id);
        if(!product.get().getOwner().equals(authentication.getName())) {
            throw new RuntimeException("Вы не являетесь владельцем автомобиля");
        }
        productRepository.deleteProductById(id);
    }

    public Product getProductByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Не удаётся найти данный бренд - %s", brand)));
    }

    @Override
    public String getOwnerEmailByCarId(String carId) {
        Optional<Product> optionalCar = productRepository.getProductById(carId);
        if(optionalCar.isEmpty()) {
            throw new RuntimeException("Такого автомобиля не существует");
        }
        Product car = optionalCar.get();
        return car.getOwner();
    }

    @Override
    public void deleteProductsWhereIsConfirmedNotNull() {
        productRepository.deleteProductsByIsConfirmedNotNull();
    }

    @Override
    public ProductResponse getProduct(String id) {
        Product product = productRepository.getProductById(id).get();
        return ProductResponse.builder()
                .id(product.getId())
                .brand(product.getBrand())
                .model(product.getModel())
                .amountOfSeats(product.getAmountOfSeats())
                .driveType(product.getDriveType())
                .prize(product.getPrize())
                .gearBox(product.getGearBox())
                .typeOfEngine(product.getTypeOfEngine())
                .imageUrl(product.getImageUrl())
                .owner(product.getOwner())
                .build();
    }
}
