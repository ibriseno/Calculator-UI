package edu.csc413.calculator.evaluator;
/**
 * Operand class used to represent an operand
 * in a valid mathmetical expression.
 */
public class Operand {
  private int opnd;
  /**
  * construct operand from string token.
  */
  public Operand( String token ) {
    try{
      opnd = Integer.parseInt(token);
    }catch(NumberFormatException e){
      System.out.println("Error 1");
      System.exit(1);
    }

  }
  /**
   * construct operand from integer
   */
  public Operand( int value ) {
    opnd = value;
    
  }
  /**
   * return value of opernad
   */
  public int getValue() {
      return opnd;
  }
  /**
   * Check to see if given token is a valid
   * operand.
   */
  public static boolean check( String token ) {
    try{
      Integer.parseInt(token);
    }catch(NumberFormatException e){
      return false;
    }
    return true;
  }
}
