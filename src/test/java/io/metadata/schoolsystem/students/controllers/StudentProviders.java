package io.metadata.schoolsystem.students.controllers;

import io.metadata.schoolsystem.models.Student;

public class StudentProviders {
    public static String providesPostResponse(Student s) {
        return "{\"id\":" + s.getId() + ",\"name\":\"" + s.getName() + "\",\"surname\":\"" + s.getSurname() + "\",\"bornDate\":\"" + s.getBornDate() + "\",\"_links\":{\"self\":{\"href\":\"http://localhost/students/" + s.getId() + "\"},\"students\":{\"href\":\"http://localhost/students/all\"}}}\n";
    }

    public static String providesDeleteResponse(Student s) {
        return "{\"id\":" + s.getId() + ",\"name\":\"" + s.getName() + "\",\"surname\":\"" + s.getSurname() + "\",\"bornDate\":\"" + s.getBornDate() + "\",\"_links\":{\"self\":{\"href\":\"http://localhost/students/" + s.getId() + "\"},\"students\":{\"href\":\"http://localhost/students/all\"}}}\n";
    }
}
