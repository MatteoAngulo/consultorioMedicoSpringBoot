package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.AuthRequest;
import edu.unimagdalena.consultoriomedico.DTO.UserDtoRequest;
import edu.unimagdalena.consultoriomedico.security.jwt.JwtUtil;
import edu.unimagdalena.consultoriomedico.security.service.JpaUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final JpaUserDetailService service;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    UserController(JpaUserDetailService service, JwtUtil jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDtoRequest> addNewUser(@RequestBody UserDtoRequest userDto){
        UserDtoRequest response = service.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.username());
            return ResponseEntity.ok(token);
        }else{
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }



}
