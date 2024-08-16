package org.example.mapper;

import org.example.dto.request_dto.SubscriptionRequestDTO;
import org.example.dto.response_dto.SubscriptionResponseDTO;
import org.example.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserMapper.class, PlanMapper.class})
public interface SubscriptionMapper {

    @Mapping(source = "user.id", target = "user.id")
    @Mapping(source = "plan.id", target = "plan.id")
    SubscriptionResponseDTO toSubscriptionResponseDTO(Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user.id", target = "user.id")
    @Mapping(source = "plan.id", target = "plan.id")
    Subscription toSubscriptionForCreate(SubscriptionRequestDTO subscriptionRequestDTO);
}
