package kbtu.kz.sis5.service;

import kbtu.kz.sis5.dto.StudentRequestDto;
import kbtu.kz.sis5.dto.StudentResponseDto;
import kbtu.kz.sis5.entity.Student;
import kbtu.kz.sis5.exception.ResourceNotFoundException;
import kbtu.kz.sis5.mapper.StudentMapper;
import kbtu.kz.sis5.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        log.info("Creating student with email: {}", requestDto.getEmail());
        Student student = studentMapper.toEntity(requestDto);
        Student saved = studentRepository.save(student);
        log.info("Student created with id: {}", saved.getId());
        return studentMapper.toResponseDto(saved);
    }

    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        log.debug("Fetching all students, page: {}", pageable.getPageNumber());
        return studentRepository.findAll(pageable)
                .map(studentMapper::toResponseDto);
    }

    public StudentResponseDto getStudentById(Long id) {
        log.debug("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with id: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });
        return studentMapper.toResponseDto(student);
    }

    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        log.info("Updating student with id: {}", id);
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        existing.setName(requestDto.getName());
        existing.setEmail(requestDto.getEmail());
        existing.setMajor(requestDto.getMajor());
        existing.setAge(requestDto.getAge());
        return studentMapper.toResponseDto(studentRepository.save(existing));
    }

    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}