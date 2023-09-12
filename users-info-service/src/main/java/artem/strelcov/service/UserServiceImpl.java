package artem.strelcov.service;

import artem.strelcov.dto.UserDto;
import artem.strelcov.model.User;
import artem.strelcov.repository.UserRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final MinioClient minioClient;
    @Override
    public void addUser(UserDto userDto) {

        User user = User.builder()
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .city(userDto.getCity())
                .rating(userDto.getRating())
                .registeredAt(userDto.getRegisteredAt())
                .build();

        userRepository.save(user);
    }

    @Override
    public void setPhoto(Authentication authentication, MultipartFile avatar) {
        User user = userRepository.findUserByEmail(authentication.getName());
        addImageToMinioAndUser(avatar, user);
    }

    private void addImageToMinioAndUser(MultipartFile avatar, User user) {
        String imageName = generateImageName(avatar);
        try (InputStream inputStream =
                     new BufferedInputStream(avatar.getInputStream())) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("avatars")
                            .object(imageName)
                            .stream(inputStream, -1, 10485760)
                            .contentType("application/octet-stream")
                            .build());
            user.setPhotoUrl(imageName);
            userRepository.save(user);
        } catch (IOException | ServerException | InsufficientDataException
                 | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException |
                 InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateImageName(MultipartFile image) {
        String extension = getExtension(image);
        return UUID.randomUUID() + "." + extension;
    }
    private String getExtension(MultipartFile image) {
        return image.getOriginalFilename()
                .substring(image.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }
}
