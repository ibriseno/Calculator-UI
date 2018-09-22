package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {

    public int priority(){
        return 3;
    }


    public Operand execute(Operand op1, Operand op2 ){
        Operand temp = new Operand(power(op1.getValue(), op2.getValue()));
        return temp;
    }
    public int power(int i, int j){
        int result = i;
        for(int c = 2 ; c <= j; c++){
            result = result*i;

        }return result;
    }
}
