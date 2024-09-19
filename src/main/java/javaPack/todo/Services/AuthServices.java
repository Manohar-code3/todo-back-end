package javaPack.todo.Services;

import javaPack.todo.Dto.JwtAuthResponse;
import javaPack.todo.Dto.LoginDto;
import javaPack.todo.Dto.RegisterDto;

public interface AuthServices {
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);


}
