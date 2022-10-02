package io.metadata.schoolsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadata.schoolsystem.AbstractTest;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.services.CourseService;
import io.metadata.schoolsystem.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static io.metadata.schoolsystem.providers.CourseProviders.providesCourse;
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

class RegisterControllerTest extends AbstractTest {

    final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private RegistrationService service;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void registerWithId() throws Exception {
        //GIVEN
        var id = 5L;
        var student = providesCourse();
        var ids = new Ids(id, id);
        given(service.register(id, id))
                .willAnswer((invocation) -> invocation.getArgument(0));
        //when
        ResultActions response = mvc.perform(post(REGISTRATION + REGISTER_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString("{\n" +
                        "  \"studentId\": \"1\",\n" +
                        "  \"courseId\" : \"2\"\n" +
                        "}")));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }

    private static class Ids{
        long studentId;
        long courseId;

        public Ids(long studentId, long courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public long getStudentId() {
            return studentId;
        }

        public void setStudentId(long studentId) {
            this.studentId = studentId;
        }

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }
    }

}