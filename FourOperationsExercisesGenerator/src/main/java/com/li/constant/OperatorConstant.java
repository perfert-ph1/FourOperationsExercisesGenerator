package com.li.constant;

import java.util.HashMap;

public class OperatorConstant {


    public static final String ADD = "+";

    public static final String SUB = "-";

    public static final String MULTIPLY = "*";

    public static final String DIVIDE = "÷";

    public static final String LEFT_BRACKETS = "(";

    public static final String RIGHT_BRACKETS = ")";

    public static final String EQUAL_SIGN = "=";

    public static final HashMap<Integer,String> operatorMap = new HashMap<>();

    static {
        operatorMap.put(1, OperatorConstant.ADD);
        operatorMap.put(2, OperatorConstant.SUB);
        operatorMap.put(3, OperatorConstant.MULTIPLY);
        operatorMap.put(4, OperatorConstant.DIVIDE);
    }


}
