package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.presentation.controller.response.UserResponse;
import br.com.fiap.tech.challenge._2.presentation.controller.response.UserTypeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserPresenter {
    private UserPresenter() {
    }

    public static UserResponse toResponse(User user) {

        return new UserResponse(
           user.getId(),
           user.getName(),
           user.getEmail(),
                new UserTypeResponse(
                        user.getUserType().getId(),
                        user.getUserType().getName(),
                        user.getUserType().isOwner()
                )
        );
    }

    public static List<UserResponse> toResponseList(List<User> list) {
        List<UserResponse> responseList = new ArrayList<>();

        for (User user :list) {

            responseList.add(
              new UserResponse(
                  user.getId(),
                  user.getName(),
                  user.getEmail(),
                  new UserTypeResponse(
                          user.getUserType().getId(),
                          user.getUserType().getName(),
                          user.getUserType().isOwner()
                  )
              )
            );
        }
        return responseList;
    }

}
