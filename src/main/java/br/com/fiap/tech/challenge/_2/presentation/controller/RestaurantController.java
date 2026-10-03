package br.com.fiap.tech.challenge._2.presentation.controller;

import br.com.fiap.tech.challenge._2.application.usecase.restaurant.*;
import br.com.fiap.tech.challenge._2.application.usecase.user.*;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.response.RestaurantResponse;
import br.com.fiap.tech.challenge._2.presentation.presenter.RestaurantPresenter;
import br.com.fiap.tech.challenge._2.presentation.presenter.UserPresenter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindRestaurantUseCase findRestaurantUseCase;
    private final GetAllRestaurantUseCase getAllRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                FindRestaurantUseCase findRestaurantUseCase,
                                GetAllRestaurantUseCase getAllRestaurantUseCase,
                                DeleteRestaurantUseCase deleteRestaurantUseCase,
                                UpdateRestaurantUseCase updateRestaurantUseCase
    )
    {
         this.createRestaurantUseCase = createRestaurantUseCase;
         this.findRestaurantUseCase = findRestaurantUseCase;
         this.getAllRestaurantUseCase=getAllRestaurantUseCase;
         this.deleteRestaurantUseCase=deleteRestaurantUseCase;
         this.updateRestaurantUseCase=updateRestaurantUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(
           @Valid @RequestBody RestaurantRequest request
    ) {
        Restaurant restaurant = createRestaurantUseCase.execute(request);
        return RestaurantPresenter.toResponse(restaurant);
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
    public RestaurantResponse findById(
           @PathVariable UUID id
    ) {

        Restaurant restaurant = findRestaurantUseCase.execute(id);

        return RestaurantPresenter.toResponse(restaurant);
   }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantResponse> getAll()
    {
        List<Restaurant> list = getAllRestaurantUseCase.execute();
        return RestaurantPresenter.toResponseList(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

       deleteRestaurantUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponse update( @PathVariable UUID id,
          @Valid  @RequestBody RestaurantRequest request
    ) {

        Restaurant restaurant = updateRestaurantUseCase.execute(id, request);
        return RestaurantPresenter.toResponse(restaurant);
    }
}
