package artem.strelcov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final WebClient.Builder webClient;
    @Scheduled(cron = "@midnight")
    public void scheduleFixedDelayTask() {

        webClient.build()
                .delete()
                .uri("http://localhost:8090/orders")
                .retrieve()
                .bodyToMono(Void.class);

        webClient.build()
                .delete()
                .uri("http://localhost:8081/products")
                .retrieve()
                .bodyToMono(Void.class);
    }

}
