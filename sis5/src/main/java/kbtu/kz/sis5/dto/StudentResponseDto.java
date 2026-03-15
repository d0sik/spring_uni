package kbtu.kz.sis5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Student data returned to client")
@Getter
@Setter
public class StudentResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Kazi Doszhan")
    private String name;

    @Schema(example = "d_kazi@kbtu.kz")
    private String email;

    @Schema(example = "IS")
    private String major;

    @Schema(example = "20")
    private Integer age;
}
