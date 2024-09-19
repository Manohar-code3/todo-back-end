package javaPack.todo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class TdoApi extends RuntimeException {
    private HttpStatus status;
    private String msg;

}
