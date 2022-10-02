package io.metadata.schoolsystem.controllers;

import io.metadata.schoolsystem.asemblers.CourseModelAssembler;
import io.metadata.schoolsystem.exceptions.CourseNotFoundException;
import io.metadata.schoolsystem.models.Course;
import io.metadata.schoolsystem.services.CourseService;
import io.metadata.schoolsystem.exceptions.StudentNotFoundException;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.metadata.schoolsystem.utils.Endpoints.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@Import(CourseService.class)
@RequestMapping(COURSES)
public class CoursesController {
    private final CourseService service;
    private final CourseModelAssembler assembler;

    public CoursesController(CourseService aService, CourseModelAssembler anAssembler) {
        this.service = aService;
        this.assembler = anAssembler;
    }

    @GetMapping(ALL)
    public CollectionModel<EntityModel<Course>> all() {
        List<EntityModel<Course>> courses = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(courses, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }

    @GetMapping(ID)
    public EntityModel<Course> one(@PathVariable Long id) {
        //FIX THIS
        final Course course;
        try {
            course = service.findByIdOrThrow(id);
        } catch (CourseNotFoundException e) {
            throw new RuntimeException(e);
        }
        return assembler.toModel(course);
    }

    @GetMapping(FIND_BY_STUDENT_ID)
    public CollectionModel<EntityModel<Course>> byStudentId(@PathVariable Long id) {
        List<EntityModel<Course>> courses = service.findCourseByStudentId(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(courses, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }

    @GetMapping(EMPTY_COURSES)
    public CollectionModel<EntityModel<Course>> emptyCourses() {
        List<EntityModel<Course>> courses = service.findEmptyCourses().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(courses, linkTo(methodOn(CoursesController.class).all()).withSelfRel());
    }

    @PostMapping(REGISTER)
    ResponseEntity<?> addCourse(@RequestBody Course newCourse) {
        var entityModel = assembler.toModel(service.save(newCourse));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @PutMapping(UPDATE)
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

    @DeleteMapping(DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}