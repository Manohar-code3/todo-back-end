package javaPack.todo.Services.impl;

import javaPack.todo.Dto.TodoDto;
import javaPack.todo.Entity.Todo;
import javaPack.todo.Exception.IDnotfound;
import javaPack.todo.Repository.TodoRepository;
import javaPack.todo.Services.TodoServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceImpl implements TodoServices {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addtodo(TodoDto todoDto) {

    //convert todoDto into JPa entity
//        Todo todo=new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());
//inseted of writing all the lines we use mapper
        Todo todo=modelMapper.map(todoDto,Todo.class);
    // TODO JPA entity
        Todo savedTodo =todoRepository.save(todo);
    //    convert saved  Todo Jpa  entity object into  TodoDto object
//        TodoDto savedTodoDto =new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle((savedTodo.getTitle()));
//        savedTodoDto.setDescription((savedTodo.getDescription()));
//        savedTodoDto.setCompleted(savedTodo.isCompleted());
//inseted of writing all the lines we use mapper dependence
        TodoDto savedTodoDto=modelMapper.map(savedTodo,TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto gettodo(Long id) {
         Todo byid=todoRepository.findById(id)
                 .orElseThrow(() ->new IDnotfound("ID was not created"+id));


         TodoDto getid= modelMapper.map(byid, TodoDto.class);
         return getid;

    }

    @Override
    public List<TodoDto> getalltodo() {
        List<Todo> all=todoRepository.findAll();
        return all.stream().map((todo) -> modelMapper.map(todo,TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updatetodo(TodoDto todoDto, Long id) {
        Todo update=todoRepository.findById(id)
                .orElseThrow(()-> new IDnotfound("TODO not found on this ID"+id));

        update.setTitle(todoDto.getTitle());
        update.setDescription(todoDto.getDescription());
        update.setCompleted(todoDto.isCompleted());

        Todo save_update=todoRepository.save(update);

        return  modelMapper.map(save_update,TodoDto.class);
    }

    @Override
    public void deletetodo(Long id) {
        Todo delete=todoRepository.findById(id)
                .orElseThrow(()-> new IDnotfound("TODO not found on this ID"+id));
        todoRepository.deleteById(id);

    }

    @Override
    public TodoDto todocomplete(Long id) {
        Todo com=todoRepository.findById(id)
                .orElseThrow(()-> new IDnotfound("TODO not found on this ID"+id));
        com.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(com);

        return  modelMapper.map(updatedTodo,TodoDto.class);

    }

    @Override
    public TodoDto incomplete(Long id) {
        Todo com=todoRepository.findById(id)
                .orElseThrow(()-> new IDnotfound("TODO not found on this ID"+id));
        com.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(com);

        return  modelMapper.map(updatedTodo,TodoDto.class);

    }
}
