package com.li.service;

import com.li.bean.Exercise;

import java.io.IOException;
import java.util.List;

public interface ExercisesGenerateService {


    /**
     * 根据题目数量生成题目
     * @param exercisesNum
     * @return
     */
    public List<Exercise> getExerciseList(int exercisesNum,int range);

    /**
     * 根据范围生成一条练习题
     * @param range
     * @return
     */
    public Exercise generateOneExercise(int range);

    /**
     * 将题目容器转换到文件
     * @param exerciseList
     */
    public void exercisesToFile(List<Exercise> exerciseList) throws IOException;

}
