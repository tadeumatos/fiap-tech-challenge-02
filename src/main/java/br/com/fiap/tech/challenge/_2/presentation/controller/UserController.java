package br.com.fiap.tech.challenge._2.presentation.controller;

import br.com.fiap.tech.challenge._2.application.usecase.user.*;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.*;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.response.UserResponse;
import br.com.fiap.tech.challenge._2.presentation.presenter.UserPresenter;
import br.com.fiap.tech.challenge._2.presentation.presenter.UserTypePresenter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final GetAllUserUseCase getAllUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                            FindUserUseCase findUserUseCase,
                            GetAllUserUseCase getAllUserUseCase,
                            DeleteUserUseCase deleteUserUseCase,
                            UpdateUserUseCase updateUserUseCase
    )
    {
         this.createUserUseCase = createUserUseCase;
         this.findUserUseCase = findUserUseCase;
         this.getAllUserUseCase=getAllUserUseCase;
         this.deleteUserUseCase=deleteUserUseCase;
         this.updateUserUseCase=updateUserUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
           @Valid @RequestBody UserRequest request
    ) {
        User user = createUserUseCase.execute(request);
        return UserPresenter.toResponse(user);
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
    public UserResponse findById(
           @PathVariable UUID id
    ) {

        User user = findUserUseCase.execute(id);

        return UserPresenter.toResponse(user);
   }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll()
    {
        List<User> list = getAllUserUseCase.execute();
        return UserPresenter.toResponseList(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

       deleteUserUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update( @PathVariable UUID id,
          @Valid  @RequestBody UserRequest request
    ) {

        User user = updateUserUseCase.execute(id, request);
        return UserPresenter.toResponse(user);
    }
}
