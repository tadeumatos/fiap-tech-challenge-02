package br.com.fiap.tech.challenge._2.presentation.controller;

import br.com.fiap.tech.challenge._2.presentation.controller.request.CreateUserTypeRequest;
import br.com.fiap.tech.challenge._2.presentation.presenter.UserTypePresenter;
import br.com.fiap.tech.challenge._2.application.usecase.usertype.*;
import br.com.fiap.tech.challenge._2.domain.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usertypes")
public class UserTypeController {
    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final FindUserTypeUseCase findUserTypeUseCase;
    private final GetAllUserTypeUseCase getAllUserTypeUseCase;
    private final DeleteUserTypeUseCase deleteUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;

    public UserTypeController(CreateUserTypeUseCase createUserTypeUseCase,
                              FindUserTypeUseCase findUserTypeUseCase,
                              GetAllUserTypeUseCase getAllUserTypeUseCase,
                              DeleteUserTypeUseCase deleteUserTypeUseCase,
                              UpdateUserTypeUseCase updateUserTypeUseCase)
    {
       this.createUserTypeUseCase = createUserTypeUseCase;
       this.findUserTypeUseCase = findUserTypeUseCase;
       this.getAllUserTypeUseCase=getAllUserTypeUseCase;
       this.deleteUserTypeUseCase=deleteUserTypeUseCase;
       this.updateUserTypeUseCase=updateUserTypeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTypePresenter.UserTypeResponse create(
            @RequestBody CreateUserTypeRequest request
    ) {

        UserType userType = createUserTypeUseCase.execute(
                request.name(),
                request.owner()
        );

        return UserTypePresenter.toResponse(userType);
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
    public UserTypePresenter.UserTypeResponse findById(
           @PathVariable UUID id
    ) {

        UserType userType = findUserTypeUseCase.execute(id);

        return UserTypePresenter.toResponse(userType);
   }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserTypePresenter.UserTypeResponse> getAll()
    {
        List<UserType> list = getAllUserTypeUseCase.execute();
        return UserTypePresenter.toResponseList(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

       deleteUserTypeUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserTypePresenter.UserTypeResponse update( @PathVariable UUID id,
            @RequestBody CreateUserTypeRequest request
    ) {

        UserType userType = updateUserTypeUseCase.execute(id, request);
        return UserTypePresenter.toResponse(userType);
    }
}
