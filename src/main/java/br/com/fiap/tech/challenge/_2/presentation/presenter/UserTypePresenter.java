package br.com.fiap.tech.challenge._2.presentation.presenter;

import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.presentation.controller.response.UserTypeResponse;

import java.util.ArrayList;
import java.util.List;

public class UserTypePresenter {
    private UserTypePresenter() {
    }

    public static UserTypeResponse toResponse(UserType userType) {

        return new UserTypeResponse(
           userType.getId(),
           userType.getName(),
           userType.isOwner()
        );
    }

    public static List<UserTypeResponse> toResponseList(List<UserType> list) {
        List<UserTypeResponse> responseList = new ArrayList<>();

        for (UserType userType :list) {

            responseList.add(
              new UserTypeResponse(
                  userType.getId(),
                  userType.getName(),
                  userType.isOwner()
              )
            );
        }
        return responseList;
    }

}
