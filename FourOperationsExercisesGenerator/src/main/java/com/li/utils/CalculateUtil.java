package com.li.utils;

import com.li.constant.OperatorConstant;
import com.li.exception.MyException;

import javax.sql.DataSource;


/**
 * 用于计算
 */
public class CalculateUtil {

    public static void main(String[] args) {
        String s = BinocularOperation("1/3", "2/3", OperatorConstant.MULTIPLY);
        System.out.println(s);
    }

    /**
     * 根据范围生成自然数
     * @param range
     * @return
     */
    public static String generateNaturalNumber(int range){
        return CommonUtil.getRandomNum(0, range).toString();
    }


    /**
     * 根据范围生成分数
     * @param range
     * @return
     */
    public static String generateFraction(int range){

        if(range == 1){
            Integer denominator = CommonUtil.getRandomNum(2,10);
            Integer molecule = CommonUtil.getRandomNum(1,denominator-1);
            return molecule+"/"+denominator;
        }else {
            int count = CommonUtil.getRandomNum(0,9);
            if(count>=0 && count <= 4){
                Integer denominator = CommonUtil.getRandomNum(2,Math.max(5,range));
                Integer molecule = CommonUtil.getRandomNum(1,denominator);
                String num = molecule+"/"+denominator;
                if(checkFraction(num) == 4){
                    Integer result = molecule/denominator;
                    return result.toString();
                }else if(checkFraction(num) == 2){
                    return FalseFractionToDaiFraction(About(num));
                }
                return About(molecule+"/"+denominator);
            }else {
                Integer denominator = CommonUtil.getRandomNum(2,Math.max(5,range));
                Integer molecule = CommonUtil.getRandomNum(denominator,range*denominator-1);
                String num = molecule+"/"+denominator;
                if(checkFraction(num) == 4){
                    Integer result = molecule/denominator;
                    return result.toString();
                }else if(checkFraction(num) == 2){
                    return FalseFractionToDaiFraction(About(num));
                }
                return About(molecule+"/"+denominator);
            }
        }

    }


    /**
     * 将带分数转换为假分数
     * @param operand
     * @return
     */
    public static String DaiFractionToFalseFraction(String operand){
        if(checkFraction(operand) != 3){
            return operand;
        }else {
            String[] split = operand.split("'");
            Integer leftNum = Integer.parseInt(split[0]);
            String[] fraction = split[1].split("/");
            Integer molecule = Integer.parseInt(fraction[0]);
            Integer denominator = Integer.parseInt(fraction[1]);
            molecule = leftNum*denominator+molecule;
            return molecule.toString()+"/"+denominator.toString();
        }
    }

    /**
     * 将假分数转换为带分数
     * @param operand
     * @return
     */
    public static String FalseFractionToDaiFraction(String operand){
        if(checkFraction(operand)!=2 && checkFraction(operand)!= 4){
            throw new MyException("该分数不是假分数");
        };
        if(checkFraction(operand)==2){
            String[] nums = operand.split("/");
            Integer leftNum = Integer.parseInt(nums[0])/Integer.parseInt(nums[1]);
            Integer remainder = Integer.parseInt(nums[0])%Integer.parseInt(nums[1]);
            String Fraction = leftNum.toString()+"'"+remainder.toString()+"/"+nums[1];
            return Fraction;
        }else {
            String[] nums = operand.split("/");
            Integer molecule = Integer.parseInt(nums[0]);
            Integer denominator = Integer.parseInt(nums[1]);
            Integer result = molecule/denominator;
            return result.toString();
        }

    }

    /**
     * 检验运算数类型（自然数、真分数、假分数、带分数）
     * 返回值 0：自然数  1：真分数  2：假分数 3：带分数 4.约分后为整数
     * @param operand
     * @return
     */
    public static int checkFraction(String operand){

        //如果是分数
        if(operand.contains("/")){
            //如果分数为带分数，保持不变
            if(operand.contains("'")){
                return 3;
            }else {
                String[] split = operand.split("/");
                if(Integer.parseInt(split[0])%Integer.parseInt(split[1]) == 0){
                    return 4;
                }
                if(Integer.parseInt(split[0])>Integer.parseInt(split[1])){
                    return 2;
                }else if(Integer.parseInt(split[0])<Integer.parseInt(split[1])){
                    return 1;
                }else {
                    return 4;
                }
            }
        }else {
            return 0;
        }
    }

    /**
     * 对假分数和真分数约分
     * @param operand
     * @return
     */
    public static String About(String operand){
        //如果是真分数或假分数
        if(checkFraction(operand) == 1 || checkFraction(operand) == 2){
            String[] split = operand.split("/");
            Integer molecule = Integer.parseInt(split[0]);
            Integer denominator = Integer.parseInt(split[1]);
            int maxCommonFactor = getMaxCommonFactor(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
            //如果不可约分，则返回原数
            if( maxCommonFactor == 1){
                return operand;
            }else {
                molecule = molecule/maxCommonFactor;
                denominator = denominator/maxCommonFactor;
                return molecule.toString()+"/"+denominator.toString();
            }
        }else {
            if(checkFraction(operand) == 4){
                String[] split = operand.split("/");
                Integer molecule = Integer.parseInt(split[0]);
                Integer denominator = Integer.parseInt(split[1]);
                Integer result = molecule/denominator;
                return result.toString();
            }
            return operand;
        }

    }

    /**
     * 获取两自然数的最大公因数
     * @param num1
     * @param num2
     * @return
     */
    public static int getMaxCommonFactor(Integer num1,Integer num2){
        if(num2 == 0){
            return num1;
        }
        return getMaxCommonFactor(num2,num1%num2);
    }

    /**
     * 获取分数分母
     * @param fraction
     * @return
     */
    public static int getDenominato(String fraction){
        if(checkFraction(fraction)!=1 && checkFraction(fraction)!=2){
            return 1;
        }
        fraction = About(fraction);
        String[] split = fraction.split("/");
        int denominato = Integer.parseInt(split[1]);
        return denominato;
    }

    /**
     * 获取分数分子
     * @param fraction
     * @return
     */
    public static int getMolecule(String fraction){

        if(checkFraction(fraction)!=1 && checkFraction(fraction)!=2){
            if(checkFraction(fraction) == 4){
                String[] split = fraction.split("/");
                int molecule = Integer.parseInt(split[0]);
                return molecule;
            }
            return Integer.parseInt(fraction);
        }
        fraction = About(fraction);
        String[] split = fraction.split("/");
        int molecule = Integer.parseInt(split[0]);
        return molecule;
    }


    /**
     * 通分
     * 0：自然数  1：真分数  2：假分数 3：带分数 4.约分后为整数
     * @param operand1
     * @param operand2
     * @return
     */
    public static String[] reductionFraction(String operand1,String operand2){
        Integer denominato1;
        Integer denominato2;
        Integer molecule1;
        Integer molecule2;
        //如果operand1为分数
        if(checkFraction(operand1) == 1 || checkFraction(operand1)==2 || checkFraction(operand1) == 3){
            if(checkFraction(operand1) == 3){
                operand1 = DaiFractionToFalseFraction(operand1);
            }
            //如果operand2为自然数
            if(checkFraction(operand2)==0){
                denominato1 = getDenominato(operand1);
                molecule2 = Integer.parseInt(operand2)*denominato1;
                String fraction2 = molecule2.toString()+"/"+denominato1;
                return new String[]{operand1,fraction2};
            //如果operand2为分数
            }else {
                if(checkFraction(operand2) == 3){
                    operand2 = DaiFractionToFalseFraction(operand2);
                }
                //获取分母
                denominato1 = getDenominato(operand1);
                denominato2 = getDenominato(operand2);
                if (denominato1 == denominato2) {
                    return new String[]{operand1, operand2};
                }
                molecule1 = getMolecule(operand1);
                molecule2 = getMolecule(operand2);
                return new String[]{molecule1 * denominato2 + "/" + denominato1 * denominato2, molecule2 * denominato1 + "/" + denominato1 * denominato2};
            }
        //如果operand1为自然数
        }else {
            if(checkFraction(operand1) == 4){
                operand1 = About(operand1);
            }
            //如果operand2为分数
            if(checkFraction(operand2) == 1 || checkFraction(operand2) == 2 || checkFraction(operand2) == 3){
                if(checkFraction(operand2) == 3){
                    operand2 = DaiFractionToFalseFraction(operand2);
                }
                denominato2 = getDenominato(operand2);
                molecule1 = denominato2*Integer.parseInt(operand1);
                return new String[]{molecule1+"/"+denominato2,operand2};
            }else {
                return new String[]{operand1,operand2};
            }
        }
    }

    /**
     * 双目运算
     * @param operand1 运算数1
     * @param operand2 运算数2
     * @param operator 运算符
     * @return 运算结果
     */
    public static String BinocularOperation(String operand1,String operand2,String operator){

        if(operator.equals(OperatorConstant.ADD)){
            //两数都不是自然数
            if(checkFraction(operand1)!=0 || checkFraction(operand2)!=0){
                String[] strings = reductionFraction(operand1, operand2);
                String[] split = strings[0].split("/");
                if(split.length!=2){
                    return null;
                }
                Integer denominator1 = Integer.parseInt(split[1]);
                Integer molecule1 = Integer.parseInt(split[0]);
                split = strings[1].split("/");
                Integer denominator2 = Integer.parseInt(split[1]);
                Integer molecule2 = Integer.parseInt(split[0]);
                return (molecule1+molecule2)+"/"+denominator1;
            }else {
                Integer result = Integer.parseInt(operand1)+Integer.parseInt(operand2);
                return result.toString();
            }
        }else if(operator.equals(OperatorConstant.SUB)){
            //两数不都是自然数
            if(checkFraction(operand1)!=0 || checkFraction(operand2)!=0){
                String[] strings = reductionFraction(operand1, operand2);
                String[] split = strings[0].split("/");
                if(split.length!=2){
                    return null;
                }
                Integer denominator1 = Integer.parseInt(split[1]);
                Integer molecule1 = Integer.parseInt(split[0]);
                split = strings[1].split("/");
                Integer denominator2 = Integer.parseInt(split[1]);
                Integer molecule2 = Integer.parseInt(split[0]);
                return (molecule1-molecule2)+"/"+denominator1;
            }else {
                Integer result = Integer.parseInt(operand1)-Integer.parseInt(operand2);
                return result.toString();
            }
        }else if(operator.equals(OperatorConstant.DIVIDE)){
            if(checkFraction(operand1)!=0 || checkFraction(operand2)!=0){
            String[] strings = reductionFraction(operand1, operand2);
            if(strings.length!=2){
                    return null;
            }
            String[] split = strings[0].split("/");
            if(split.length!=2){
                    return null;
            }
            Integer denominator1 = Integer.parseInt(split[1]);
            Integer molecule1 = Integer.parseInt(split[0]);
            split = strings[1].split("/");
            Integer denominator2 = Integer.parseInt(split[1]);
            Integer molecule2 = Integer.parseInt(split[0]);
            if(molecule2 == 0){
                return null;
            }
            Integer temp = denominator2;
            denominator2 = molecule2;
            molecule2 = temp;
            Integer molecule = molecule1*molecule2;
            Integer denominator = denominator1*denominator2;
            String result = molecule+"/"+denominator;
            result = About(result);
            return result;
            }else {
                if(Integer.parseInt(operand2) == 0){
                    return null;
                }
                String result = Integer.parseInt(operand1)+"/"+Integer.parseInt(operand2);
                if(checkFraction(result) == 4){
                    Integer temp = Integer.parseInt(operand1)/Integer.parseInt(operand2);
                    return temp.toString();
                }
                return result.toString();
            }
        }else {
            //两者不都是自然数
            if(checkFraction(operand1)!=0 || checkFraction(operand2)!=0){
                String[] strings = reductionFraction(operand1, operand2);
                String[] split = strings[0].split("/");
                if(split.length!=2){
                    return null;
                }
                Integer denominator1 = Integer.parseInt(split[1]);
                Integer molecule1 = Integer.parseInt(split[0]);
                split = strings[1].split("/");
                Integer denominator2 = Integer.parseInt(split[1]);
                Integer molecule2 = Integer.parseInt(split[0]);
                if(molecule2 == 0){
                    return null;
                }
                Integer molecule = molecule1*molecule2;
                Integer denominator = denominator1*denominator2;
                String result = molecule+"/"+denominator;
                return About(result);
            }else {
                Integer temp = Integer.parseInt(operand1)*Integer.parseInt(operand2);
                return temp.toString();
            }
        }
    }
}
