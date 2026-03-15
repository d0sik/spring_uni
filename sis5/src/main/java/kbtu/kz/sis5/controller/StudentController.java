package kbtu.kz.sis5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kbtu.kz.sis5.dto.StudentRequestDto;
import kbtu.kz.sis5.dto.StudentResponseDto;
import kbtu.kz.sis5.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student API", description = "Operations for managing students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Create a new student")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(
            @Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(requestDto));
    }

    @Operation(summary = "Get all students with pagination")
    @ApiResponse(responseCode = "200", description = "List of students")
    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @Operation(summary = "Get student by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @Operation(summary = "Update student by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, requestDto));
    }

    @Operation(summary = "Delete student by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
