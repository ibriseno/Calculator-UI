package edu.csc413.calculator.evaluator;
import edu.csc413.calculator.operators.Operator;


import java.util.Stack;
import java.util.StringTokenizer;


public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer tokenizer;
  private static final String DELIMITERS = "()+-*^/";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();

  }


  public int eval( String expression ) {
    String token;

    //Clear the stacks every time we start a new expression
    //to be evaluated.
    operatorStack.clear();
    operandStack.clear();

      //This will be used as the end of our stack.
      //The left parentheses operator has the lowest priority value of -1.
      operatorStack.push(Operator.getOperator("(")); //init for stack

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.tokenizer = new StringTokenizer( expression, DELIMITERS, true );



    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators


    while ( this.tokenizer.hasMoreTokens() ) {
      // filter out spaces
      if ( !( token = this.tokenizer.nextToken() ).equals( " " )) {
        // check if token is an operand
        if ( Operand.check( token )) {
          operandStack.push( new Operand( token ));
        } else {
          if ( ! Operator.check( token )) {
            System.out.println( "*****invalid token******" );
            throw new RuntimeException("*****invalid token******");
          }

            //This section takes care of the operations inside a (...)
            //If the ( token is detected it reads up to the ) token then the
            //program performs the arithmetic operations up until it reaches the ( token again.
            //It then pushes the new operand into the operand stack.
            //After operations are done it continues to check for more tokens in the string.
            if(token.equals(")")){
                while(operatorStack.peek().priority() > -1){
                    Operator oldOpr = operatorStack.pop();
                    Operand op2 = operandStack.pop();
                    Operand op1 = operandStack.pop();
                    operandStack.push(oldOpr.execute(op1,op2));
                }operatorStack.pop(); continue;

            }
            if(token.equals("(")){
                operatorStack.push(Operator.getOperator("("));
                continue;
            }
            Operator newOperator = Operator.getOperator(token);


            //adds the token that was assigned to newOperator into the stack
            //if the stack is empty.
            if(operatorStack.isEmpty()){
                operatorStack.add(newOperator);
                continue;
            }




            // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.


          //This part handles the priority operations for the expression.
          //Ex: 1*1-2, our initial priority is set to -1, when the first token
          //is evaluated it will push a new priority value of 2 to the stack.
          //Since our second token has a lower priority value than the current token
          //the program performs the operations inside the statement.
          while (operatorStack.peek().priority() >= newOperator.priority() ) {
            // note that when we eval the expression 1 - 2 we will
            // push the 1 then the 2 and then do the subtraction operation
            // This means that the first number to be popped is the
            // second operand, not the first operand - see the following code

            Operator oldOpr = operatorStack.pop();
            Operand op2 = operandStack.pop();
            Operand op1 = operandStack.pop();
            operandStack.push( oldOpr.execute( op1, op2 ));
          }

          operatorStack.push( newOperator );
        }
      }
    }

    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks (except
    // the init operator on the operator stack); that is, we should keep
    // evaluating the operator stack until it only contains the init operator;
    // Suggestion: create a method that takes an operator as argument and
    // then executes the while loop.

   //Added this
    while(operatorStack.peek().priority()> 0){
      Operator currentOperator = operatorStack.pop();
      Operand op2 = operandStack.pop();
      Operand op1 = operandStack.pop();
      operandStack.push(currentOperator.execute(op1,op2));
    }
    return operandStack.pop().getValue();
  }
}
