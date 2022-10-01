package io.metadata.schoolsystem.utils;

public class Endpoints {
    private final static String API = "/api";
    public final static String ID = "/{id}";
    public final static String STUDENTS = API+"/students";
    public final static String COURSES = API+"/courses";
    public final static String ALL = "/all";

    public final static String FIND_BY_STUDENT_ID = "/byStudent"+ID;

    public final static String FIND_BY_COURSE_ID = "/byCourse"+ID;
    public final static String EMPTY_COURSES = "/emptyCourses";

    public final static String STUDENTS_WITHOUT_COURSES = "/studentsWithoutCourses";

    public final static String REGISTER = "/register";
    public final static String UPDATE = "/update"+ID;
    public final static String DELETE = "/delete"+ID;






}
