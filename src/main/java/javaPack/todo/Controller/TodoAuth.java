package javaPack.todo.Controller;

import javaPack.todo.Dto.JwtAuthResponse;
import javaPack.todo.Dto.LoginDto;
import javaPack.todo.Dto.RegisterDto;
import javaPack.todo.Services.AuthServices;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("site/auth")

public class TodoAuth {


    private AuthServices authServices;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
    {
        String  response =authServices.register(registerDto);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        JwtAuthResponse jwtAuthResponse =authServices.login(loginDto);



        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

}
