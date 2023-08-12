package artem.strelcov.services;

import artem.strelcov.dto.ProductResponse;
import artem.strelcov.model.Product;
import artem.strelcov.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product ->
                        ProductResponse.builder()
                                .amountOfSeats(product.getAmountOfSeats())
                                .brand(product.getBrand())
                                .model(product.getModel())
                                .classOfTheAuto(product.getClassOfTheAuto())
                                .rentPrize(product.getRentPrize())
                                .year(product.getYear())
                                .driveType(product.getDriveType())
                                .steeringWheelPosition(product.getSteeringWheelPosition())
                                .transmission(product.getTransmission())
                                .build()
                ).toList();
    }

    public void addProduct(Product product) {
        productRepository.insert(product);
    }


    public void updateProduct(Product product) {
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException(String.format("Невозможно найти машину с айди %s", product.getId())));
        savedProduct.setModel(product.getModel());
        savedProduct.setBrand(product.getBrand());
        savedProduct.setYear(product.getYear());
        savedProduct.setDriveType(product.getDriveType());
        savedProduct.setTransmission(product.getTransmission());
        savedProduct.setAmountOfSeats(product.getAmountOfSeats());
        savedProduct.setRentPrize(product.getRentPrize());
        savedProduct.setClassOfTheAuto(product.getClassOfTheAuto());
        savedProduct.setSteeringWheelPosition(product.getSteeringWheelPosition());

        productRepository.save(savedProduct);
    }

    public Product getProductByModel(String model) {
        return productRepository.findByModel(model)
                .orElseThrow(() -> new RuntimeException(String.format("Не удаётся найти данную модель - %s", model)));
    }
}
