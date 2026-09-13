package br.com.fiap.tech.challenge._2.adapters.controller;

import br.com.fiap.tech.challenge._2.adapters.controller.request.CreateFoodTypeRequest;

import br.com.fiap.tech.challenge._2.adapters.presenter.FoodTypePresenter;

import br.com.fiap.tech.challenge._2.application.usecase.foodtype.*;

import br.com.fiap.tech.challenge._2.domain.FoodType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/foodtypes")
public class FoodTypeController {
    private final CreateFoodTypeUseCase createFoodTypeUseCase;
    private final FindFoodTypeUseCase findFoodTypeUseCase;
    private final GetAllFoodTypeUseCase getAllFoodTypeUseCase;
    private final DeleteFoodTypeUseCase deleteFoodTypeUseCase;
    private final UpdateFoodTypeUseCase updateFoodTypeUseCase;

    public FoodTypeController(CreateFoodTypeUseCase createFoodTypeUseCase,
                              FindFoodTypeUseCase findFoodTypeUseCase,
                              GetAllFoodTypeUseCase getAllFoodTypeUseCase,
                              DeleteFoodTypeUseCase deleteFoodTypeUseCase,
                              UpdateFoodTypeUseCase updateFoodTypeUseCase)
    {
        this.createFoodTypeUseCase = createFoodTypeUseCase;
        this.findFoodTypeUseCase = findFoodTypeUseCase;
        this.getAllFoodTypeUseCase=getAllFoodTypeUseCase;
        this.deleteFoodTypeUseCase=deleteFoodTypeUseCase;
        this.updateFoodTypeUseCase=updateFoodTypeUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodTypePresenter.FoodTypeResponse create(
            @RequestBody CreateFoodTypeRequest request
    ) {

        FoodType foodType = createFoodTypeUseCase.execute(
                request.name()
        );

        return FoodTypePresenter.toResponse(foodType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodTypePresenter.FoodTypeResponse findById(
            @PathVariable UUID id
    ) {

        FoodType foodType = findFoodTypeUseCase.execute(id);

        return FoodTypePresenter.toResponse(foodType);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FoodTypePresenter.FoodTypeResponse> getAll()
    {
        List<FoodType> list = getAllFoodTypeUseCase.execute();
        return FoodTypePresenter.toResponseList(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

        deleteFoodTypeUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodTypePresenter.FoodTypeResponse update( @PathVariable UUID id,
                                              @RequestBody CreateFoodTypeRequest request
    ) {

        FoodType foodType = updateFoodTypeUseCase.execute(id, request);
        return FoodTypePresenter.toResponse(foodType);
    }
}
