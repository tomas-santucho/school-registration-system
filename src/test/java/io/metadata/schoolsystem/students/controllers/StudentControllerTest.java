package io.metadata.schoolsystem.students.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadata.schoolsystem.AbstractTest;
import io.metadata.schoolsystem.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.metadata.schoolsystem.students.controllers.StudentProviders.providesPostResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StudentControllerTest extends AbstractTest {

    final ObjectMapper mapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

   @Test
    public void getStudents() throws Exception {
        String uri = "/students/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        var students = super.mapFromJson(content, Student[].class);
        assertTrue(students.length > 0);
    }


    @Test
    public void addStudent() throws Exception {
        var uri = "/students/register";
        var student = new Student();
        student.setId(4L);
        student.setName("Thomas");
        student.setSurname("Desch");
        student.setBornDate("today");
        var inputJson = super.mapToJson(student);
        var mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        var status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        var result = mvcResult.getResponse().getContentAsString();
        var expected = providesPostResponse(student);
        assertEquals(mapper.readTree(expected), mapper.readTree(result));
    }
    @Test
    public void updateStudent() throws Exception {
        String uri = "/students/update/2";
        var student = new Student();
        student.setName("Lemon");
        String inputJson = super.mapToJson(student);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "actual");
    }
    @Test
    public void deleteStudent() throws Exception {
        String uri = "/students/delete/3";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        var status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

}