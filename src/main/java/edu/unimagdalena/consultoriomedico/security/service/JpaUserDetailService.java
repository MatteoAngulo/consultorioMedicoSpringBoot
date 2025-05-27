package edu.unimagdalena.consultoriomedico.security.service;

import edu.unimagdalena.consultoriomedico.DTO.UserDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.UserDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.EnumRole;
import edu.unimagdalena.consultoriomedico.entities.Role;
import edu.unimagdalena.consultoriomedico.entities.UserEntity;
import edu.unimagdalena.consultoriomedico.exceptions.alreadyExists.EmailAlreadyExistsException;
import edu.unimagdalena.consultoriomedico.exceptions.alreadyExists.UsernameAlreadyExistsException;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.RolNotFoundException;
import edu.unimagdalena.consultoriomedico.repositories.RoleRepository;
import edu.unimagdalena.consultoriomedico.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new UserInfoDetail(user);
    }

    public UserDtoResponse addUser(UserDtoRequest userInfo) {

        if (userRepository.existsByUsername(userInfo.username())) {
            throw new UsernameAlreadyExistsException(userInfo.username());
        }

        if (userRepository.existsByEmail(userInfo.email())) {
            throw new EmailAlreadyExistsException(userInfo.email());
        }


        Set<Role> roles = userInfo.roles().stream()
                .map(roleName -> mapToRole(roleName))
                .collect(Collectors.toSet());

        UserEntity user = new UserEntity();
        user.setUsername(userInfo.username());
        user.setPassword(passwordEncoder.encode(userInfo.password()));
        user.setEmail(userInfo.email());
        user.setRoles(roles);

        user = userRepository.save(user);

        return new UserDtoResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );

    }

    private Role mapToRole(String roleName) {

            EnumRole enumRole = EnumRole.valueOf(roleName);
            return roleRepository.findByName(enumRole)
                    .orElseThrow(() -> new RolNotFoundException("Role doesn't exist in DB: " + roleName));

    }



}
