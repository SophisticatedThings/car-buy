package artem.strelcov.service;

import artem.strelcov.dto.OrderDto;
import artem.strelcov.model.Order;
import artem.strelcov.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String,OrderDto> kafkaTemplate;
    @Override
    public void createOrder(String carId, Authentication authentication,
                            HttpServletRequest request) {

        String jwtToken = request.getHeader("Authorization").substring(7);
        String ownerEmail = webClient.build()
                .get()
                .uri("http://localhost:8081/products/{car-id}",
                        uriBuilder -> uriBuilder
                                .build(carId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(jwtToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Order order = Order.builder()
                .buyerEmail(authentication.getName())
                .ownerEmail(ownerEmail)
                .date(LocalDateTime.now())
                .carId(carId)
                .isConfirmed(null)
                .build();
        orderRepository.save(order);

        OrderDto orderDto = OrderDto.builder()
                .orderId(order.getId())
                .buyerEmail(order.getBuyerEmail())
                .ownerEmail(order.getOwnerEmail())
                .carId(order.getCarId())
                .date(order.getDate())
                .token(jwtToken)
                .build();

        kafkaTemplate.send("notification", orderDto);
    }

    @Override
    public void confirmOrder(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.get();
        order.setIsConfirmed(true);
        orderRepository.save(order);
    }

    @Override
    public void declineOrder(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.get();
        order.setIsConfirmed(false);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrdersWhereIsConfirmedNotNull() {
        orderRepository.deleteOrdersByIsConfirmedIsNotNull();
    }
}
