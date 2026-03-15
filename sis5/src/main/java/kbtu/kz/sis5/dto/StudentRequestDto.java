package kbtu.kz.sis5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Request body for creating or updating a student")
@Getter
@Setter
public class StudentRequestDto {

    @Schema(example = "Kazi Doszhan")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Schema(example = "d_kazi@kbtu.kz")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "IS")
    private String major;

    @Schema(example = "20")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
}