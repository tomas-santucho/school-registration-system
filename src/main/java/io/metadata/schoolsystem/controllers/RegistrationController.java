package io.metadata.schoolsystem.controllers;

import io.metadata.schoolsystem.asemblers.StudentModelAssembler;
import io.metadata.schoolsystem.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.exceptions.MaxCoursesException;
import io.metadata.schoolsystem.exceptions.MaxStudentsException;
import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import io.metadata.schoolsystem.services.RegistrationService;
import io.metadata.schoolsystem.utils.RegisterWIthIdDto;
import lombok.SneakyThrows;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.metadata.schoolsystem.utils.Endpoints.REGISTER_WITH_ID;
import static io.metadata.schoolsystem.utils.Endpoints.REGISTRATION;

@RestController
@RequestMapping(REGISTRATION)
public class RegistrationController {
    private final RegistrationService service;
    private final StudentModelAssembler assembler;

    public RegistrationController(RegistrationService service, StudentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @SneakyThrows
    @PostMapping(REGISTER_WITH_ID)
    ResponseEntity<?> registerWithId(@RequestBody RegisterWIthIdDto dto) throws MaxCoursesException, StudentNotFoundException, CourseNotFoundException, MaxStudentsException {
        var entityModel = assembler.toModel(service.register(dto.getStudentId(), dto.getCourseId()));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}