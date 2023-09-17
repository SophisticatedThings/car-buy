package artem.strelcov.service;

import artem.strelcov.dto.AuthenticationRequest;
import artem.strelcov.dto.AuthenticationResponse;
import artem.strelcov.dto.ProductResponse;
import artem.strelcov.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.WebUtils;

@Service
@RequiredArgsConstructor
public class RestApiClientService {
    private final WebClient.Builder webClient;
    public  String authenticate(AuthenticationRequest request, HttpServletResponse httpResponse) {
        AuthenticationResponse response =
                webClient.build()
                .post()
                .uri("http://localhost:8080/auth/authentication")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .block();

        Cookie cookie = new Cookie("jwt", response.getToken());
        httpResponse.addCookie(cookie);
        return response.getToken().substring(7);
    }

    public ProductResponse[] getProducts(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return webClient.build()
                .get()
                .uri("http://localhost:8081/products")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(cookie.getValue()))
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();


    }
    public ProductResponse getProduct(String productId, HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return webClient.build()
                .get()
                .uri("http://localhost:8081/products/{product-id}",
                        uriBuilder -> uriBuilder
                        .build(productId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(cookie.getValue()))
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }
    public UserDto getOwner(String ownerEmail, HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return webClient.build()
                .get()
                .uri("http://localhost:8085/users/{email}",
                        uriBuilder -> uriBuilder
                                .build(ownerEmail))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(cookie.getValue()))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public UserDto getUserInfo(String email, HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "jwt");
        return webClient.build()
                .get()
                .uri("http://localhost:8085/users/{email}",
                        uriBuilder -> uriBuilder.build(email))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(cookie.getValue()))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
