package it.contrader.authenticationservice.service;

import feign.FeignException;
import it.contrader.authenticationservice.customException.CustomFeignException;
import it.contrader.authenticationservice.customException.EmailAlreadyInUseException;
import it.contrader.authenticationservice.customException.UsernameAlreadyInUseException;
import it.contrader.authenticationservice.dto.LoggedUserDTO;
import it.contrader.authenticationservice.dto.LoginDTO;
import it.contrader.authenticationservice.dto.SignupDTO;
import it.contrader.authenticationservice.feignClient.AnagraficaFeignClient;
import it.contrader.authenticationservice.model.User;
import it.contrader.authenticationservice.repository.UserRepository;
import it.contrader.authenticationservice.security.JwtUtils;
import it.contrader.authenticationservice.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnagraficaFeignClient anagraficaFeignClient;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public LoggedUserDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwt(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new LoggedUserDTO(userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            jwt,
            roles);
    }

    public void registerUser(SignupDTO signUpRequest) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyInUseException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyInUseException("Error: Email is already in use!");
        }

        User savedUser = userRepository.save(User.builder()
            .email(signUpRequest.getEmail())
            .username(signUpRequest.getUsername())
            .password(encoder.encode(signUpRequest.getPassword()))
            .roles(authService.createRoles(signUpRequest.getRoles()))
            .build());

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(savedUser.getUsername());

        jwtUtils.setRequestJwt(jwtUtils.generateJwt(userDetails));

        try {
            signUpRequest.getAnagrafica().setUserId(savedUser.getId());
            anagraficaFeignClient.register(signUpRequest.getAnagrafica());
        } catch (FeignException e) {
            throw new CustomFeignException("Errore durante la registrazione dell'anagrafica", e);
        }

    }
}
