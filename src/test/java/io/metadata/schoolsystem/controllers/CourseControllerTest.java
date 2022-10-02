package io.metadata.schoolsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadata.schoolsystem.AbstractTest;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.models.Student;
import io.metadata.schoolsystem.services.CourseService;
import io.metadata.schoolsystem.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static io.metadata.schoolsystem.providers.CourseProviders.providesCourse;
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

class CourseControllerTest extends AbstractTest {

    final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private CourseService service;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void register() throws Exception {
        //GIVEN
        var student = providesCourse();
        given(service.save(any(Course.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        //when
        ResultActions response = mvc.perform(post(COURSES + REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }

    @Test
    void all() throws Exception {
        //given
        var courses = singletonList(providesCourse());
        given(service.findAll()).willReturn(courses);

        //when
        ResultActions response = mvc.perform(get(COURSES + ALL));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(courses.size())));
    }

    @Test
    void findById() throws Exception {
        //given
        long id = 1L;
        var course = providesCourse();
        given(service.findByIdOrThrow(id)).willReturn(course);
        //when
        ResultActions response = mvc.perform(get(COURSES + ID, id));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(course.getName())));
    }

    @Test
    void deleteById() throws Exception {
        //given
        long id = 1L;
        willDoNothing().given(service).deleteById(id);
        //when
        ResultActions response = mvc.perform(delete(COURSES + DELETE, id));
        //then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

}