package br.com.fiap.tech.challenge._2.presentation.controller;

import br.com.fiap.tech.challenge._2.application.usecase.menu.*;
import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.presentation.controller.request.MenuRequest;
import br.com.fiap.tech.challenge._2.presentation.controller.response.MenuResponse;
import br.com.fiap.tech.challenge._2.presentation.presenter.MenuPresenter;
import br.com.fiap.tech.challenge._2.presentation.presenter.UserPresenter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
public class MenuController {
      private final CreateMenuUseCase createMenuUseCase;
      private final FindMenuUseCase findMenuUseCase;
      private final GetAllMenuUseCase getAllMenuUseCase;
      private final DeleteMenuUseCase deleteMenuUseCase;
      private final UpdateMenuUseCase updateMenuUseCase;

    public MenuController(CreateMenuUseCase createMenuUseCase,
                          FindMenuUseCase findMenuUseCase,
                          GetAllMenuUseCase getAllMenuUseCase,
                          DeleteMenuUseCase deleteMenuUseCase,
                          UpdateMenuUseCase updateMenuUseCase
    )
    {
         this.createMenuUseCase = createMenuUseCase;
         this.findMenuUseCase = findMenuUseCase;
         this.getAllMenuUseCase=getAllMenuUseCase;
         this.deleteMenuUseCase=deleteMenuUseCase;
         this.updateMenuUseCase=updateMenuUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse create(
          @Valid @RequestBody MenuRequest request
    ) {
        Menu menu = createMenuUseCase.execute(request);
        return MenuPresenter.toResponse(menu);
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
    public MenuResponse findById(
           @PathVariable UUID id
    ) {

        Menu menu = findMenuUseCase.execute(id);

        return MenuPresenter.toResponse(menu);
   }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MenuResponse> getAll()
    {
        List<Menu> list = getAllMenuUseCase.execute();
        return MenuPresenter.toResponseList(list);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {

       deleteMenuUseCase.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponse update( @PathVariable UUID id,
          @Valid  @RequestBody MenuRequest request
    ) {

        Menu menu = updateMenuUseCase.execute(id, request);
        return MenuPresenter.toResponse(menu);
    }
}
