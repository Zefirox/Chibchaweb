package co.edu.unbosque.chibchawebbackend.controllers;

import co.edu.unbosque.chibchawebbackend.services.MailService;
import co.edu.unbosque.chibchawebbackend.config.UserAuthenticationProvider;
import co.edu.unbosque.chibchawebbackend.dtos.CredentialsDto;
import co.edu.unbosque.chibchawebbackend.dtos.SignUpDto;
import co.edu.unbosque.chibchawebbackend.dtos.UserDto;
import co.edu.unbosque.chibchawebbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final MailService mailService;
    private final UserService userService;
    private final UserAuthenticationProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user.getCorreo()));
        String[] message = {user.getCorreo(),user.getNombre()};
        mailService.sendMessage("Actividad Reciente",message, "notificationAccess.html");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto signUpDto){

        /*
         *Codigo quemado para corroborar creacion de usuario
         */
        signUpDto.setDireccion("CLL 130 #30");
        signUpDto.setTelefono("3102584265");
        signUpDto.setCodPostal("100101");
        UserDto user = userService.registerUser(signUpDto);
        user.setToken(userAuthProvider.createToken(user.getCorreo()));

        String[] message = {user.getCorreo(),user.getNombre()};
        mailService.sendMessage("Creación de cuenta en NovaWave",message,"notificationReg.html");

        return ResponseEntity.created(URI.create("/users/"+user.getId())).body(user);
    }

}
