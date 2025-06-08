package com.clothashe.clotashe_backend.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;

@Configuration
public class OpenApiConfig {

    // Construye una respuesta de error con referencia al esquema "ApiError"
    private ApiResponse buildErrorResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
    }

    // Aplica respuestas de error globales a todas las operaciones
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();
                    responses.addApiResponse("400", buildErrorResponse("Bad Request"));
                    responses.addApiResponse("401", buildErrorResponse("Unauthorized"));
                    responses.addApiResponse("403", buildErrorResponse("Forbidden"));
                    responses.addApiResponse("404", buildErrorResponse("Not Found"));
                }));
    }

    // Registra el schema "ApiError" en los componentes globales de OpenAPI
    @Bean
    public OpenAPI customOpenAPI() {
        Schema<?> apiErrorSchema = new Schema<>()
                .type("object")
                .description("Structure for API errors")
                .addProperty("message", new Schema<>().type("string").description("Error message to display to the user"))
                .addProperty("status", new Schema<>().type("integer").format("int32").description("HTTP status code of the error"))
                .addProperty("errorCode", new Schema<>().type("string").description("Internal code to distinguish errors on the frontend"))
                .addProperty("path", new Schema<>().type("string").description("Path where the error occurred"))
                .addProperty("timestamp", new Schema<>().type("string").description("Date and time of the error in ISO format"))
                .example("""
                {
                  "message": "Resource not founds",
                  "status": 404,
                  "errorCode": "NOT_FOUND",
                  "path": "/api/users/123",
                  "timestamp": "2025-06-08T16:00:00"
                }
                """);

        return new OpenAPI()
                .components(new Components().addSchemas("ApiError", apiErrorSchema));
    }
}