package artem.strelcov.controller;

import artem.strelcov.dto.UserDto;
import artem.strelcov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{email}")
    public UserDto getUserByEmail(@PathVariable("email")String email) {
        return userService.getUserByEmail(email);
    }
    @PostMapping()
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }
    @PutMapping("/photo")
    public void setPhoto(
            Authentication authentication,
            @RequestParam(value = "avatar") MultipartFile avatar
    ) {
        userService.setPhoto(authentication, avatar);
    }
}
