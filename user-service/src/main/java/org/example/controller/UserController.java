package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.UserService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создает нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь создан")
    @PostMapping
    public ResponseEntity<UserDto> createdUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity.ok(new UserDto(user.getName(), user.getEmail(), user.getAge()));
    }

    @Operation(summary = "Возвращает всех пользователей")
    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.getAllUsers().stream()
                .map(user -> {
                    UserDto dto = new UserDto(user.getName(), user.getEmail(), user.getAge());
                    return EntityModel.of(dto,
                            linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
                })
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @Operation(summary = "Возвращает пользователя по id")
    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto dto = new UserDto(user.getName(), user.getEmail(), user.getAge());

        EntityModel<UserDto> model = EntityModel.of(dto);

        model.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        model.add(linkTo(methodOn(UserController.class).updateUser(id, null)).withRel("update"));
        model.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"));

        return model;
    }

    @Operation(summary = "Обновляет пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(new UserDto(updatedUser.getName(), updatedUser.getEmail(), updatedUser.getAge()));
    }

    @Operation(summary = "Удаляет пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
