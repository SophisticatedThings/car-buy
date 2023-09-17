package artem.strelcov.controller;

import artem.strelcov.dto.OrderDto;
import artem.strelcov.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/{car-id}")
    public void createOrder(
            @PathVariable(value = "car-id") String carId,
            Authentication authentication,
            HttpServletRequest request) {
        orderService.createOrder(carId, authentication, request);
    }
    @GetMapping("/confirm/{order-id}")
    public void confirmOrder(@PathVariable(value = "order-id") Integer orderId) {
        orderService.confirmOrder(orderId);
    }
    @GetMapping("/decline/{order-id}")
    public void declineOrder(@PathVariable(value = "order-id") Integer orderId) {
        orderService.declineOrder(orderId);
    }
    @DeleteMapping
    public void deleteOrdersWhereIsConfirmedNotNull() {
        orderService.deleteOrdersWhereIsConfirmedNotNull();
    }

}
