package io.metadata.schoolsystem.services;

import io.metadata.schoolsystem.repositories.StudentRepository;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("WHEN student is saved THEN it properties are correct")
    public void save() {
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


}