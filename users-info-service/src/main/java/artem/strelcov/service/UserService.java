package artem.strelcov.service;

import artem.strelcov.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void addUser(UserDto userDto);

    void setPhoto(Authentication authentication, MultipartFile avatar);

}
