package javaPack.todo.Controller;

import javaPack.todo.Dto.TodoDto;
import javaPack.todo.Entity.Todo;
import javaPack.todo.Services.TodoServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("site/Todos")
@AllArgsConstructor

public class TodoController {
    private TodoServices todoServices;

    //    build add todo  rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addtodo(@RequestBody TodoDto todoDto){
        TodoDto saved =todoServices.addtodo(todoDto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);


    }

//    build get by id gettodo rest api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto>gettodo(@PathVariable("id") Long todoid)
    {
        TodoDto getid=todoServices.gettodo(todoid);
        return new ResponseEntity<>(getid,HttpStatus.OK);
    }

// build getall rest api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getalltodo()
    {
         List<TodoDto> getall=todoServices.getalltodo();
         return new ResponseEntity<>(getall,HttpStatus.OK);
    }

// build update rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public  ResponseEntity<TodoDto>updatetodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoid)
    {
        TodoDto updateid=todoServices.updatetodo(todoDto,todoid);
        return new ResponseEntity<>(updateid,HttpStatus.OK);
    }

//    build delete by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public  ResponseEntity<String>deletetodo(@PathVariable("id") Long todoid)
    {
        todoServices.deletetodo(todoid);
        return  ResponseEntity.ok("todo was successfully delete ");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/completed")
    public  ResponseEntity<TodoDto>todocomplete(@PathVariable("id") Long Todoid)
    {
        TodoDto compl_id=todoServices.todocomplete(Todoid);
        return ResponseEntity.ok(compl_id);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/incompleted")
    public  ResponseEntity<TodoDto>incomplete(@PathVariable("id") Long todoid)
    {
        TodoDto incompl_id=todoServices.incomplete(todoid);
        return ResponseEntity.ok(incompl_id);
    }

}


