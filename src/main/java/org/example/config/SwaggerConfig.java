package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.email}")
    private String email;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Provider System API")
                        .version("1.0")
                        .description(
                                "API for service provider management")
                        .contact(new Contact()
                                .name("Support Team")
                                .email(email))
                );
    }

    @Bean
    public GroupedOpenApi publicAuthApi() {
        return GroupedOpenApi.builder()
                .group("Auth API")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminPlanApi() {
        return GroupedOpenApi.builder()
                .group("Admin Plans API")
                .pathsToMatch("/api/admin/plans/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientPlanApi() {
        return GroupedOpenApi.builder()
                .group("Client Plans API")
                .pathsToMatch("/api/client/plans/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminPromotionApi() {
        return GroupedOpenApi.builder()
                .group("Admin Promotion API")
                .pathsToMatch("/api/admin/promotions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientPromotionApi() {
        return GroupedOpenApi.builder()
                .group("Client Promotion API")
                .pathsToMatch("/api/client/promotions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminPromotionalTariffApi() {
        return GroupedOpenApi.builder()
                .group("Admin Promotional Tariff API")
                .pathsToMatch("/api/admin/promotions-tariffs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientPromotionalTariffApi() {
        return GroupedOpenApi.builder()
                .group("Client Promotional Tariff API")
                .pathsToMatch("/api/client/promotions-tariffs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminRoleApi() {
        return GroupedOpenApi.builder()
                .group("Admin Role API")
                .pathsToMatch("/api/admin/roles/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminStatusApi() {
        return GroupedOpenApi.builder()
                .group("Admin Status API")
                .pathsToMatch("/api/admin/statuses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminSubscriptionApi() {
        return GroupedOpenApi.builder()
                .group("Admin Subscription API")
                .pathsToMatch("/api/admin/subscription/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientSubscriptionApi() {
        return GroupedOpenApi.builder()
                .group("Client Subscription API")
                .pathsToMatch("/api/client/subscriptions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminTariffApi() {
        return GroupedOpenApi.builder()
                .group("Admin Tariff API")
                .pathsToMatch("/api/admin/tariffs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientTariffApi() {
        return GroupedOpenApi.builder()
                .group("Client Tariff API")
                .pathsToMatch("/api/client/tariffs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAdminUserApi() {
        return GroupedOpenApi.builder()
                .group("Admin User API")
                .pathsToMatch("/api/admin/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicClientUserApi() {
        return GroupedOpenApi.builder()
                .group("Client User API")
                .pathsToMatch("/api/client/users/**")
                .build();
    }
}
