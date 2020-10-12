package com.li.service.impl;

import com.li.bean.Exercise;
import com.li.constant.OperatorConstant;
import com.li.exception.MyException;
import com.li.service.ExercisesGenerateService;
import com.li.utils.CalculateUtil;
import com.li.utils.CommonUtil;

import java.io.*;
import java.util.*;

public class ExercisesGenerateServiceImpl implements ExercisesGenerateService {

    public static void main(String[] args) {
        ExercisesGenerateService generateService = new ExercisesGenerateServiceImpl();
        List<Exercise> exerciseList = generateService.getExerciseList(10000, 10);
        for (Exercise exercise : exerciseList){
            System.out.println(exercise);
        }
    }

    /**
     * 根据题目数量和范围获取练习题
     * @param exercisesNum
     * @param range
     * @return
     */
    @Override
    public List<Exercise> getExerciseList(int exercisesNum, int range) {
        List<Exercise> exercisesList = new ArrayList<Exercise>();
        Set<String> exercisesSet = new HashSet<String>();
        int questionNum = 0;
        Exercise exercise = new Exercise();
        while (questionNum < exercisesNum){
            exercise = generateOneExercise(range);
            if(checkQuestion(exercise,exercisesSet)){
                questionNum++;
                exercise.setTitleNumber(questionNum);
                exercisesList.add(exercise);
                exercisesSet.add(getFormatQuestion(exercise));
                exercisesSet.add(getEqualFormatQuestion(getFormatQuestion(exercise)));
            }
        }
        return exercisesList;
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public List<Exercise> exerciseFileToExerciseList(File file) throws IOException {
        file = new File(System.getProperty("user.dir")+"/Exercises.txt");
        BufferedReader exercisesReader = new BufferedReader(new FileReader(file));
        String question = "";
        List<Exercise> exerciseList = new ArrayList<Exercise>();

        while((question = exercisesReader.readLine())!=null){
            String expression = "";
            Exercise exercise = new Exercise();
            String[] split = question.split(".");
            exercise.setTitleNumber(Integer.parseInt(split[0]));
            expression = split[1].replace("=","");
        }
        return null;
    }

    /**
     * 获取相同最简式的问题
     * @param exercise
     * @return
     */
    private String getEqualFormatQuestion(String exercise) {
        if(exercise == ""){
            return "";
        }
        String formatExercise = exercise;
        if(formatExercise.contains(OperatorConstant.ADD)){
            String[] split = formatExercise.split("[+]");
            return split[1]+OperatorConstant.ADD+split[0];
        }else if(formatExercise.contains(OperatorConstant.SUB)){
            String[] split = formatExercise.split(OperatorConstant.SUB);
            return split[1]+OperatorConstant.SUB+split[0];
        }else if(formatExercise.contains(OperatorConstant.DIVIDE)){
            String[] split = formatExercise.split(OperatorConstant.DIVIDE);
            if(split[0].equals('0')){
                throw new MyException("getEqualFormatQuestion除法分母为0");
            }
            return split[1]+OperatorConstant.DIVIDE+split[0];
        }else if(formatExercise.contains(OperatorConstant.MULTIPLY)){
            String[] split = formatExercise.split("[*]");
            return split[1]+OperatorConstant.MULTIPLY+split[0];
        }else {
            throw new MyException("错误:"+exercise);
        }
    }

    /**
     * 生成范围内的一条题目
     * @param range
     * @return
     */
    public Exercise generateOneExercise(int range) {
        Exercise exercise = new Exercise();
        exercise.setNumberRange(range);
        exercise.setOperatorNum(CommonUtil.getRandomNum(1,3));
        List<String> operatorList = new ArrayList<String>();
        String operator = null;
        for (int i = 0; i < exercise.getOperatorNum() ; i++) {
            operator = OperatorConstant.operatorMap.get((Integer) CommonUtil.getRandomNum(1, 4));
            operatorList.add(operator);
        }
        List<String> numList = new ArrayList<String>();
        int flag = 0;
        for (int i = 0; i < operatorList.size()+1 ; i++) {
            flag = CommonUtil.getRandomNum(0,1);
            if(flag == 0){
                numList.add(CalculateUtil.generateFraction(range));
            }else {
                numList.add(CalculateUtil.generateNaturalNumber(range));
            }
        }
        exercise.setNumbers((ArrayList<String>) numList);
        exercise.setOperators((ArrayList<String>) operatorList);
        exercise.setExerciseString(generateExerciseStr(exercise));
        exercise.setAnswer(getAnswer(exercise));
        return exercise;
    }

    /**
     * 将题目和答案输出到文件
     * @param exerciseList
     * @throws IOException
     */
    @Override
    public void exercisesToFile(List<Exercise> exerciseList)  throws IOException {
        BufferedWriter exercisesWriter = null;
        BufferedWriter answerWriter = null;
        try {
            exercisesWriter = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"/Exercises.txt")));
            answerWriter = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir")+"/Answer.txt")));
            Exercise exercise = new Exercise();
            for (int i = 0; i < exerciseList.size() ; i++) {
                exercise = exerciseList.get(i);
                Integer titleNumber = exercise.getTitleNumber();
                String exerciseString = exercise.getExerciseString();
                exercisesWriter.write(titleNumber.toString()+"."+exerciseString);
                answerWriter.write(titleNumber.toString()+"."+exercise.getAnswer());
                exercisesWriter.newLine();
                answerWriter.newLine();
                exercisesWriter.flush();
                answerWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exercisesWriter.close();
            answerWriter.close();
        }
    }

    /**
     * 检查问题是否重复或答案是否小于0
     * @param exercise
     * @param questionSet
     * @return
     */
    public boolean checkQuestion(Exercise exercise,Set<String> questionSet){
        if(exercise == null || questionSet == null){
            throw new MyException("checkQuestion方法参数出现问题");
        }

        if(exercise.getAnswer().contains("-")){
            return false;
        }
        if(questionSet.contains(getFormatQuestion(exercise))){
            return false;
        }
        return true;
    }

    /**
     * 获取题目答案
     * @param exercise
     * @return
     */
    private static String getAnswer(Exercise exercise) {
        ArrayList<String> numbers = exercise.getNumbers();
        ArrayList<String> operators = exercise.getOperators();
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < numbers.size() ; i++) {
            String s = numbers.get(i);
            if(CalculateUtil.checkFraction(s) == 3){
               s = CalculateUtil.DaiFractionToFalseFraction(s);
            }
            queue.add(s);
        }
        for (int i = 0; i < operators.size() ; i++) {
            String operator = operators.get(i);
            String num1 = queue.remove();
            String num2 = queue.remove();
            String answer = CalculateUtil.BinocularOperation(num1,num2,operator);
            if(answer == null){
                exercise.setAnswer(null);
                return "";
            }
            if(CalculateUtil.checkFraction(answer) == 2){
                answer = CalculateUtil.FalseFractionToDaiFraction(answer);
            }
            queue.add(answer);
        }
        return queue.remove();
    }

    /**
     * 获取题目符合格式的输出
     * @param exercise
     * @return
     */
    private static String generateExerciseStr(Exercise exercise) {
        Queue<String> queue = new LinkedList<>();
        ArrayList<String> operators = exercise.getOperators();
        ArrayList<String> numbers = exercise.getNumbers();
        for (int i = 0; i < numbers.size() ; i++) {
            queue.add(numbers.get(i));
        }
        String exerciseStr = "";
        for (int i = 0; i < operators.size() ; i++) {
            String operator = operators.get(i);
            String num1 = queue.remove();
            String num2 = queue.remove();
            if(i != operators.size()-1){
                exerciseStr = OperatorConstant.LEFT_BRACKETS+num1+operator+num2+OperatorConstant.RIGHT_BRACKETS;
            }else {
                exerciseStr = num1+operator+num2;
            }
            queue.add(exerciseStr);
        }
        StringBuilder sb = new StringBuilder("").append(queue.remove()).append("=");
        return sb.toString();
    }

    /**
     *
     * @return
     */
    public static String getFormatQuestion(Exercise exercise){
        String formatQuestion = "";
        ArrayList<String> numbers = exercise.getNumbers();
        ArrayList<String> operators = exercise.getOperators();
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < numbers.size() ; i++) {
            String s = numbers.get(i);
            if(CalculateUtil.checkFraction(s) == 3){
                s = CalculateUtil.DaiFractionToFalseFraction(s);
            }
            queue.add(s);
        }
        for (int i = 0; i < operators.size() ; i++) {
            String operator = operators.get(i);
            String num1 = queue.remove();
            String num2 = queue.remove();
            String answer = CalculateUtil.BinocularOperation(num1,num2,operator);
            if(answer == null){
                exercise.setAnswer(null);
                return "";
            }
            if(CalculateUtil.checkFraction(answer) == 2){
                answer = CalculateUtil.FalseFractionToDaiFraction(answer);
            }
            if(i == operators.size()-1){
                formatQuestion = CalculateUtil.About(num1)+operator+CalculateUtil.About(num2);
            }
            queue.add(answer);
        }
        return formatQuestion;
    }


}
