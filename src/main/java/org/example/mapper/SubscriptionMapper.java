package org.example.mapper;

import org.example.dto.requestdto.SubscriptionRequestDTO;
import org.example.dto.responsedto.SubscriptionResponseDTO;
import org.example.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserMapper.class, PlanMapper.class})
public interface SubscriptionMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "plan", target = "plan")
    SubscriptionResponseDTO toSubscriptionResponseDTO(Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "planId", target = "plan.id")
    Subscription toSubscriptionForCreate(SubscriptionRequestDTO subscriptionRequestDTO);
}
