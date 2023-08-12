package artem.strelcov.services;

import artem.strelcov.model.User;
import artem.strelcov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  JavaMailSender javaMailSender;
    @Transactional
    public User findByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    /*public void register(RegisterRequest request,
                         HttpServletRequest httpServletRequest)
            throws MessagingException, UnsupportedEncodingException {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        //sendVerificationEmail(user, getSiteURL(httpServletRequest));
    }

    /*public void authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            UserDetails userDetails = loadUserByUsername(request.getEmail());
            String token = jwtTokenUtils.generateToken(userDetails);
    } */
    /*private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        System.out.println(siteURL.replace(request.getServletPath(), ""));
        return siteURL.replace(request.getServletPath(), "");
    }
    private void sendVerificationEmail(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "shadowsqweze@mail.ru";
        String senderName = "CarRent";
        String subject = "Please, verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\">VERIFY ACCOUNT</a></h3>"
                + "Thank you,<br>"
                + "CarRent";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(fromAddress, senderName);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullname());
        String verificationCode = RandomStringUtils.random(64,true,true);
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        String verifyURL = siteURL + "/api/v1/auth" +  "/verify?code=" + verificationCode ;
        content = content.replace("[[URL]]", verifyURL);
        System.out.println(verifyURL);
        messageHelper.setText(content, true);
        javaMailSender.send(message);
    }
    public void verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    } */

}
