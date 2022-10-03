package io.metadata.schoolsystem.services;

import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.models.Student;
import io.metadata.schoolsystem.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.metadata.schoolsystem.providers.StudentProviders.providesNullStudent;
import static io.metadata.schoolsystem.providers.StudentProviders.providesStudent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void findByIdOrThrows() {
        //GIVEN
        var opt = Optional.ofNullable(providesNullStudent());
        var id = 4L;
        given(repository.findById(id)).willReturn(opt);
        //WHEN
        var e = assertThrows(StudentNotFoundException.class, () -> {
            studentService.findByIdOrThrow(id);
        });
        //THEN
        assertThat(e.getMessage()).contains(String.valueOf(id));
    }

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void deleteById() {
        //GIVEN
        var id = 4L;
        doNothing().when(repository).deleteById(id);
        //WHEN
        studentService.deleteById(id);
        //THEN
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findAll() {
        //GIVEN
        given(repository.findAll()).willReturn(null);
        //WHEN
        studentService.findAll();
        //THEN
        verify(repository).findAll();
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findEmptyCourses() {
        //GIVEN
        given(repository.findStudentsWithoutCourses()).willReturn(null);
        //WHEN
        studentService.findStudentsWithoutCourses();
        //THEN
        verify(repository).findStudentsWithoutCourses();
    }


    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findAllByStudentId() {
        //GIVEN
        var id = 4L;
        given(repository.findAllByCourseId(id)).willReturn(null);
        //WHEN
        studentService.findAllByCourseId(id);
        //THEN
        verify(repository).findAllByCourseId(id);
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void save() {
        //GIVEN
        var id = 4L;
        given(repository.save(any())).willReturn(null);
        //WHEN
        studentService.save(any());
        //THEN
        verify(repository).save(any());
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findById() {
        //GIVEN
        var id = 4L;
        given(repository.findById(id)).willReturn(null);
        //WHEN
        studentService.findById(id);
        //THEN
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void update() {
        //GIVEN
        var id = 4L;
        given(repository.save(any(Student.class))).willReturn(null);
        //WHEN
        studentService.update(providesStudent(), id);
        //THEN
        verify(repository).save(any(Student.class));
    }


}