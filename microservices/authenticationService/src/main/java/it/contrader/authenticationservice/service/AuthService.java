package it.contrader.authenticationservice.service;

import it.contrader.authenticationservice.model.Role;
import it.contrader.authenticationservice.repository.RoleRepository;
import it.contrader.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> createRoles(Set<String> rolesString) {
        if (rolesString != null && !rolesString.isEmpty()) {
            return rolesString.stream().map(role -> {
                if (role.equalsIgnoreCase("admin")) {
                    return roleRepository.findByRole(Role.ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                } else {
                    return roleRepository.findByRole(Role.ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                }
            }).collect(Collectors.toSet());
        } else {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRole(Role.ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            return roles;
        }
    }
}
