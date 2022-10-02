package io.metadata.schoolsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadata.schoolsystem.AbstractTest;
import io.metadata.schoolsystem.models.Student;
import io.metadata.schoolsystem.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static io.metadata.schoolsystem.providers.StudentProviders.providesStudent;
import static io.metadata.schoolsystem.utils.Endpoints.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest extends AbstractTest {

    final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private StudentService service;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void register() throws Exception {
        //GIVEN
        var student = providesStudent();
        given(service.save(any(Student.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        //when
        ResultActions response = mvc.perform(post(STUDENTS + REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())));
    }

    @Test
    void all() throws Exception {
        //given
        var students = singletonList(providesStudent());
        given(service.findAll()).willReturn(students);

        //when
        ResultActions response = mvc.perform(get(STUDENTS + ALL));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(students.size())));
    }

    @Test
    void findById() throws Exception {
        //given
        long id = 1L;
        var student = providesStudent();
        given(service.findByIdOrThrow(id)).willReturn(student);

        //when
        ResultActions response = mvc.perform(get(STUDENTS + ID, id));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())));
    }

    @Test
    void deleteById() throws Exception {
        //given
        long id = 1L;
        willDoNothing().given(service).deleteById(id);
        //when
        ResultActions response = mvc.perform(delete(STUDENTS + DELETE, id));
        //then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

}