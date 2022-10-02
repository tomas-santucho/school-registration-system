package io.metadata.schoolsystem.services;

import io.metadata.schoolsystem.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.repositories.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.metadata.schoolsystem.providers.CourseProviders.providesNullCourse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository repository;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void findByIdOrThrow() {
        //GIVEN
       var opt = Optional.ofNullable(providesNullCourse());
       var id = 4L;
       given(repository.findById(id)).willReturn(opt);
       //WHEN
        var e = assertThrows(CourseNotFoundException.class, () -> {
            courseService.findByIdOrThrow(id);
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
        courseService.deleteById(id);
        //THEN
       verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findAll() {
        //GIVEN
        given(repository.findAll()).willReturn(null);
        //WHEN
        courseService.findAll();
        //THEN
        verify(repository).findAll();
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findEmptyCourses() {
        //GIVEN
        given(repository.findEmptyCourses()).willReturn(null);
        //WHEN
        courseService.findEmptyCourses();
        //THEN
        verify(repository).findEmptyCourses();
    }


    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void findAllByStudentId() {
        //GIVEN
        var id = 4L;
        given(repository.findAllByStudentId(id)).willReturn(null);
        //WHEN
        courseService.findCourseByStudentId(id);
        //THEN
        verify(repository).findAllByStudentId(id);
    }

    @Test
    @DisplayName("WHEN service findAll is called repository method is called")
    public void save() {
        //GIVEN
        var id = 4L;
        given(repository.save(any())).willReturn(null);
        //WHEN
        courseService.save(any());
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
        courseService.findById(id);
        //THEN
        verify(repository).findById(id);
    }
}