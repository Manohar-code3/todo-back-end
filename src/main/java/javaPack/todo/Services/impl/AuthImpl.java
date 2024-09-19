package javaPack.todo.Services.impl;

import javaPack.todo.Dto.JwtAuthResponse;
import javaPack.todo.Dto.LoginDto;
import javaPack.todo.Dto.RegisterDto;
import javaPack.todo.Entity.Roles;
import javaPack.todo.Entity.Users;
import javaPack.todo.Exception.TdoApi;
import javaPack.todo.Repository.RoleRepository;
import javaPack.todo.Repository.UserRepository;
import javaPack.todo.Security.JwtTokenProvider;
import javaPack.todo.Services.AuthServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthImpl implements AuthServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Constructor-based dependency injection
    @Autowired
    public AuthImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TdoApi(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TdoApi(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Users user = new Users();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Roles> roles = new HashSet<>();
        Roles userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User successfully created";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =jwtTokenProvider.generateToken(authentication);
        Optional<Users>usersOptional= userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail());


        String role=null;

        if(usersOptional.isPresent())
        {
            Users loggedInUser=usersOptional.get();

            Optional<Roles> optionalRole=loggedInUser.getRoles().stream().findFirst();
            if(optionalRole.isPresent())
            {
                Roles userRole =optionalRole.get();
                role =userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse =new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }
}
