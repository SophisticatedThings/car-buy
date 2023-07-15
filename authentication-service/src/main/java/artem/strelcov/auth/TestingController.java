package artem.strelcov.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/testing")
@RequiredArgsConstructor
public class TestingController {
    @GetMapping
    public String test(){
        return "Allright";
    }
}
