package br.com.fiap.tech.challenge._2.presentation.controller.response;

import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponse(UUID id,String name,String description,AddressResponse addressResponse,String addressNumber,String addressComplement,
                                 UserResponse userResponse,FoodTypeResponse foodTypeResponse,LocalTime startTime,LocalTime endTime) {
}
