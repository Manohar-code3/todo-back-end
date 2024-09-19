package javaPack.todo.Services;

import javaPack.todo.Dto.TodoDto;
import javaPack.todo.Entity.Todo;

import java.util.List;

public interface TodoServices {

    TodoDto addtodo(TodoDto todoDto);

    TodoDto gettodo(Long id);

    List<TodoDto> getalltodo();

    TodoDto updatetodo(TodoDto todoDto,Long id);

    void deletetodo(Long id);

    TodoDto todocomplete(Long id);

    TodoDto incomplete(Long id);

}
