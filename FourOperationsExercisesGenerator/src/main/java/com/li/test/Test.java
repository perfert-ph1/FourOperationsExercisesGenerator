package com.li.test;

import com.li.service.ExercisesGenerateService;
import com.li.service.impl.ExercisesGenerateServiceImpl;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        ExercisesGenerateService service = new ExercisesGenerateServiceImpl();
        try {
            service.exercisesToFile(service.getExerciseList(10000,10));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
