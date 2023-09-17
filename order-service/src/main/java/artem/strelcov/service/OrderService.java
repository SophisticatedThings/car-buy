package artem.strelcov.service;

import artem.strelcov.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface OrderService {

    void createOrder(String carId, Authentication authentication,
                     HttpServletRequest request);

    void confirmOrder(Integer orderId);

    void declineOrder(Integer orderId);

    void deleteOrdersWhereIsConfirmedNotNull();
}
