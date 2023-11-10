package it.contrader.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUserDTO {

    private Long id;

    private String username;

    private String email;

    private String jwt;

    private List<String> roles;
}
