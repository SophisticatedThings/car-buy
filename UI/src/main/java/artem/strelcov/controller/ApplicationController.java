package artem.strelcov.controller;

import artem.strelcov.dto.AuthenticationRequest;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.dto.UserDto;
import artem.strelcov.service.RestApiClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/app")
@RequiredArgsConstructor
public class ApplicationController {
    private final RestApiClientService restApiClientService;
    private final WebClient.Builder webClient;

    @GetMapping("/authentication")
    public String authenticationView(Model model) {
        AuthenticationRequest request = new AuthenticationRequest();
        model.addAttribute("authenticationRequest", request);
        return "auth/login";
    }
    @PostMapping("/authentication")
    public String submitAuthentication(
            @ModelAttribute("authenticationRequest") AuthenticationRequest request,
            HttpServletResponse response) {
        System.out.println(restApiClientService.authenticate(request, response));
        return "auth/application";
    }
    @GetMapping("/products/{product-id}")
    public String getProductDetails(
            @PathVariable("product-id") String productId,
            Model model,
            HttpServletRequest request) {
        ProductResponse productResponse = restApiClientService.getProduct(productId,request);
        String ownerEmail = productResponse.getOwner();
        System.out.println(ownerEmail);
        model.addAttribute("product", productResponse);
        model.addAttribute("owner",
                restApiClientService.getOwner(ownerEmail, request));
        return "app/product-details";
    }
    @GetMapping("/products")
    public String getProducts(Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        model.addAttribute("products",
                restApiClientService.getProducts(request));
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        response.addCookie(cookie);
        return "app/products";
    }
    @GetMapping("/display/image/{image-url}")
    public ResponseEntity<byte[]> displayItemImage(
            @PathVariable("image-url") String url,
            HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        byte[] image = webClient.build()
                .get()
                .uri("http://localhost:8081/products/image/{image-url}",
                        uriBuilder -> uriBuilder
                                .build(url))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(cookie.getValue()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        System.out.println(image.length);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    @GetMapping("/users/{user-email}")
    public String userInfo(
            @PathVariable("user-email") String email,
            Model model,
            HttpServletRequest request) {
        UserDto userDto = restApiClientService.getUserInfo(email,request);
        model.addAttribute("user", userDto);
        return "app/user-info";
    }
}
