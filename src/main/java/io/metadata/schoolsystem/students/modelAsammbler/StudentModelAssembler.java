package io.metadata.schoolsystem.students.modelAsammbler;

import io.metadata.schoolsystem.students.controllers.StudentController;
import io.metadata.schoolsystem.students.models.Student;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {
    @Override
    @NonNull
    public EntityModel<Student> toModel(@NonNull Student student) {
        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).one(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).all()).withRel("students"));
    }
}
