package io.metadata.schoolsystem.courses.controllers;

import io.metadata.schoolsystem.courses.modelAsammbler.CourseModelAssembler;
import io.metadata.schoolsystem.courses.models.Course;
import io.metadata.schoolsystem.courses.repositories.CourseRepository;
import io.metadata.schoolsystem.students.exceptions.StudentNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final CourseRepository service;
    private final CourseModelAssembler assembler;

    public CoursesController(CourseRepository aService, CourseModelAssembler anAssembler) {
        this.service = aService;
        this.assembler = anAssembler;
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<Course>> all() {
        List<EntityModel<Course>> courses = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(courses, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }
    @GetMapping("/{id}")
    public EntityModel<Course> one(@PathVariable Long id) {
        //FIX THIS
        final Course course;
        try {
            course = service.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException(id.toString()));
        } catch (StudentNotFoundException e) {
            throw new RuntimeException(e);
        }
        return assembler.toModel(course);
    }

    @PostMapping("/register")
    ResponseEntity<?> addCourse(@RequestBody Course newCourse) {
        var entityModel = assembler.toModel(service.save(newCourse));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @PutMapping("/update/{id}")
    ResponseEntity<?> updateCourse(@RequestBody Course newCourse, @PathVariable Long id) {
        var updatedCourse = service.findById(id)
                .map(course -> {
                    course.setName(newCourse.getName());
                    course.setStudents(newCourse.getStudents());
                    return service.save(course);
                })
                .orElseGet(() -> {
                    newCourse.setId(id);
                    return service.save(newCourse);
                });
        var entityModel = assembler.toModel(updatedCourse);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}