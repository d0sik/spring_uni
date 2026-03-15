package kbtu.kz.sis5.mapper;

import kbtu.kz.sis5.dto.StudentRequestDto;
import kbtu.kz.sis5.dto.StudentResponseDto;
import kbtu.kz.sis5.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequestDto dto);

    StudentResponseDto toResponseDto(Student student);
}