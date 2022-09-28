package io.metadata.schoolsystem.courses.models;

import io.metadata.schoolsystem.students.models.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Long id;
    String name;
   // @ManyToMany
   // @JoinTable(
     //       name = "STUDENTS_COURSES",
       //     joinColumns = @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID"),
         //   inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
  //  )
    @ManyToMany(mappedBy = "courses")
    Set<Student> employees;
   // private Set<Student> students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var course = (Course) o;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}
