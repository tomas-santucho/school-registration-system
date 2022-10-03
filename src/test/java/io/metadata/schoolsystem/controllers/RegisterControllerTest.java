package io.metadata.schoolsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadata.schoolsystem.AbstractTest;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.services.CourseService;
import io.metadata.schoolsystem.services.RegistrationService;
import io.metadata.schoolsystem.utils.RegisterWIthIdDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static io.metadata.schoolsystem.providers.CourseProviders.providesCourse;
import static io.metadata.schoolsystem.providers.StudentProviders.providesStudent;
import static io.metadata.schoolsystem.utils.Endpoints.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        var student = providesStudent();
        var ids = new RegisterWIthIdDto(id, id);
        given(service.register(anyLong(), anyLong())).willReturn(student);
        //when
        ResultActions response = mvc.perform(post(REGISTRATION + REGISTER_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ids)));
        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }
}