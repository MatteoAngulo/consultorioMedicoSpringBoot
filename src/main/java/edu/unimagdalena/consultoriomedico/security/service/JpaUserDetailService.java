package edu.unimagdalena.consultoriomedico.security.service;

import edu.unimagdalena.consultoriomedico.DTO.UserDtoRequest;
import edu.unimagdalena.consultoriomedico.entities.UserEntity;
import edu.unimagdalena.consultoriomedico.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new UserInfoDetail(user);
    }

    public UserDtoRequest addUser(UserDtoRequest userInfo) {
        UserEntity user = new UserEntity(null, userInfo.username(), passwordEncoder.encode(userInfo.password()), userInfo.email(),userInfo.roles());
        user = userRepository.save(user);
        return new UserDtoRequest(user.getUsername(),user.getEmail(), userInfo.password(), user.getRoles());
    }



}
