package io.metadata.schoolsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

//I dont like using dtos, it just for making the test pass.
@Data
@AllArgsConstructor
public class RegisterWIthIdDto {
    private long studentId;
    private long courseId;
}
