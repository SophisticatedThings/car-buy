package artem.strelcov.controllers;

import artem.strelcov.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final WebClient.Builder webClient;
    @GetMapping()
    public void getDemo(){
        ProductResponse[] productResponseArray = webClient.build().get()
                .uri("http://localhost:8080/product")
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();
        for(ProductResponse response : productResponseArray)
            System.out.println(response.getBrand());
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("images", "fieldValue");
        MultiValueMap<String, HttpEntity<?>> parts = builder.build();
    }
}
