package artem.strelcov.config;

import artem.strelcov.dto.OrderDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final JavaMailSender javaMailSender;
    @KafkaListener(
            topics = "notification",
            groupId = "notification",
            containerFactory = "orderListener"
    )
    void notificationListener(OrderDto orderDto) throws MessagingException, UnsupportedEncodingException {
        sendVerificationEmail(orderDto);
    }
    private void sendVerificationEmail(OrderDto orderDto)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = orderDto.getOwnerEmail();
        String fromAddress = "shadowsqweze1123@gmail.com";
        String senderName = "CarBuy";
        String subject = "Пожалуйста, подтвердите/отклоните продажу авто";
        String content = "Уважаемый [[ownerName]],<br>"
                + "пользователь с email [[buyerName]] хочет приобрести у Вас" +
                " автомобиль [[carId]]. Подтверждаете ли Вы сделку?<br>"
                + "<h3><a href=\"[[confirmURL]]\">Подтверждаю</a></h3>"
                + "<h3><a href=\"[[declineURL]]\">Отклоняю</a></h3>"
                + "С уважением, сервис CarBuy";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(fromAddress, senderName);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);

        content = content.replace("[[ownerName]]", orderDto.getOwnerEmail());
        content = content.replace("[[buyerName]]", orderDto.getBuyerEmail());
        content = content.replace("[[carId]]", orderDto.getCarId());

        String confirmURL = "http://localhost:8090" + "/orders/confirm" + "/" + orderDto.getOrderId();
        content = content.replace("[[confirmURL]]", confirmURL);

        String declineUrl = "http://localhost:8090" + "/orders/decline" + "/" + orderDto.getOrderId();
        content = content.replace("[[declineURL]]", declineUrl);
        messageHelper.setText(content, true);
        javaMailSender.send(message);
    }
}
