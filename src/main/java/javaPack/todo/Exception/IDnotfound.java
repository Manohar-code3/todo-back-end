package javaPack.todo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IDnotfound extends RuntimeException{
    public IDnotfound(String message) {
        super(message);
    }
}
