package com.li.bean;

import java.util.ArrayList;

public class Exercise {

    //数值范围
    private Integer numberRange;

    //运算符数量
    private Integer operatorNum;

    //运算符集合
    private ArrayList<String> operators;


    //运算数集合
    private ArrayList<String> numbers = new ArrayList<String>();

    //题号
    private Integer titleNumber;

    //答案
    private String Answer;

    //题目字符串
    private String ExerciseString;

    public Exercise() {
    }

    public Integer getNumberRange() {
        return numberRange;
    }

    public void setNumberRange(Integer numberRange) {
        this.numberRange = numberRange;
    }

    public Integer getOperatorNum() {
        return operatorNum;
    }

    public void setOperatorNum(Integer operatorNum) {
        this.operatorNum = operatorNum;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<String> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<String> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<String> operators) {
        this.operators = operators;
    }

    public Integer getTitleNumber() {
        return titleNumber;
    }

    public void setTitleNumber(Integer titleNumber) {
        this.titleNumber = titleNumber;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getExerciseString() {
        return ExerciseString;
    }

    public void setExerciseString(String exerciseString) {
        ExerciseString = exerciseString;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "numberRange=" + numberRange +
                ", operatorNum=" + operatorNum +
                ", operators=" + operators +
                ", numbers=" + numbers +
                ", titleNumber=" + titleNumber +
                ", Answer='" + Answer + '\'' +
                ", ExerciseString='" + ExerciseString + '\'' +
                '}';
    }
}
