package io.metadata.schoolsystem.courses.modelAsammbler;

import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.students.controllers.StudentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class CourseModelAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {
    @Override
    @NonNull
    public EntityModel<Course> toModel(@NonNull Course student) {
        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).one(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).all()).withRel("courses"));
    }
}
