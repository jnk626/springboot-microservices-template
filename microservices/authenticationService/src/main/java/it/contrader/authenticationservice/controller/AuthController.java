package it.contrader.authenticationservice.controller;

import it.contrader.authenticationservice.customException.EmailAlreadyInUseException;
import it.contrader.authenticationservice.customException.UsernameAlreadyInUseException;
import it.contrader.authenticationservice.dto.LoginDTO;
import it.contrader.authenticationservice.dto.MessageResponse;
import it.contrader.authenticationservice.dto.SignupDTO;
import it.contrader.authenticationservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private StreamBridge streamBridge;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok()
            .body(userService.login(loginDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signUpRequest) {
        try {
            userService.registerUser(signUpRequest);
            // bindingName must match the application.yml property of the binding
            streamBridge.send("output-out-0", signUpRequest.getAnagrafica(), MimeType.valueOf("application/json"));
            streamBridge.send("output-out-1", signUpRequest.getEmail());
            LOGGER.info("Successfully sent: {}", signUpRequest.getAnagrafica());
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (UsernameAlreadyInUseException | EmailAlreadyInUseException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

}
