package org.example.springuserservice.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Управление пользователями")
public class UserController {
    private final UserService service;

    @PostMapping
    @Operation(
            summary = "Создать пользователя",
            description = "Создаёт нового пользователя ",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно создан",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    )
            }
    )
    public ResponseEntity<EntityModel> create(@RequestBody UserDTO dto) {
        UserDTO createdUser = service.create(dto);

        EntityModel<UserDTO> res = EntityModel.of(createdUser,
                linkTo(methodOn(UserController.class).get(createdUser.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).update(createdUser.getId(), dto)).withRel("update"),
                linkTo(methodOn(UserController.class).delete(createdUser.getId())).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
        );
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает данные пользователя по его ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    )
            }
    )
    public ResponseEntity<EntityModel> get(@PathVariable Long id) {
        UserDTO userDto = service.get(id);

        EntityModel<UserDTO> res = EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).get(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).update(id, userDto)).withRel("update"),
                linkTo(methodOn(UserController.class).delete(id)).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
        );
        return ResponseEntity.ok(res);
    }

    @GetMapping
    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователи найдены",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))
                    )
            }
            )
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> getAll() {
        List<EntityModel<UserDTO>> users = service.getAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).update(user.getId(), user)).withRel("update"),
                        linkTo(methodOn(UserController.class).delete(user.getId())).withRel("delete")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UserDTO>> resources = CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel(),
                linkTo(methodOn(UserController.class).create(null)).withRel("create-user")
        );

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение пользователя",
            description = "Изменяет пользвателя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь изменен",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не был изменен"
                    )
            }
    )
    public ResponseEntity<EntityModel<UserDTO>> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserDTO updatedUser = service.update(id, dto);

        EntityModel<UserDTO> res = EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).get(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).update(id, dto)).withRel("update"),
                linkTo(methodOn(UserController.class).delete(id)).withRel("delete"),
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
        );

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
