package br.com.fiap.tech.challenge._2.adapters.presenter;

import br.com.fiap.tech.challenge._2.domain.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        for (UserType userType:list) {

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

    public record UserTypeResponse(
            UUID id,
            String name,
            boolean owner
    ) {
    }
}
