import com.li.constant.CommandConstant;
import com.li.exception.MyException;
import com.li.service.ExercisesGenerateService;
import com.li.service.impl.ExercisesGenerateServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        loadParameter(args);
    }

    private static void loadParameter(String[] args){
        ExercisesGenerateService service = new ExercisesGenerateServiceImpl();
        int flag = 0;
        //默认生成10道题
        int exerciseNum = 10;
        //数字范围
        int numRange = 0;
        //题目路径
        String exerciseFilePath = "";
        //答案路径
        String answerFilePath = "";
        for (int i = 0; i < args.length ; i++) {
            if(args[i].equals(CommandConstant.ARGS_NUM_RANGE)) {
                flag = 1;
                numRange = Integer.parseInt(args[i + 1]);
            }
            if(args[i].equals(CommandConstant.ARGS_EXERCISES_NUM)){
                exerciseNum = Integer.parseInt(args[i+1]);
            }
            if(args[i].equals(CommandConstant.ARGS_EXERCISES_FILE)){
                exerciseFilePath = args[i+1];
                flag = 1;
            }
            if(args[i].equals(CommandConstant.ARGS_EXERCISES_ANSWER)){
                answerFilePath = args[i+1];
                flag = 1;
            }
        }
        if(flag == 0){
            throw new MyException("-r参数必须给出");
        }
        try {
            service.exercisesToFile(service.getExerciseList(exerciseNum,numRange));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
