import java.util.Scanner;
import java.lang.*;
import java.applet.Applet;
import net.objecthunter.exp4j.*;

@SuppressWarnings("unused")
public class TrapezoidalErrorCalculator
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7984592406346035387L;
	public static double ERROR = -999.0;
	private static Scanner sc;
	
	public static void main(String[] args)
	{
		double K = 0.0;
		
		
		sc = new Scanner(System.in);
		System.out.println("Please enter upper bounds (b):");
		 String upperbound = sc.nextLine();
		 double b = Convert.Parse(upperbound,ERROR);
		System.out.println("Please enter lower bounds (a):");
		 String lowerbound = sc.nextLine();
		 double a = Convert.Parse(lowerbound,ERROR);
		System.out.println("Please enter value for n:");
		 String nvalue = sc.nextLine();	 
		 double n = Convert.Parse(nvalue,ERROR);
		System.out.println("Have you found K value already? (Y/N?)");
		 String yesorno = sc.nextLine();
		 if(yesorno.equals("Y") || yesorno.equals("y") || yesorno.equals("yes") || yesorno.equals("Yes"))
		 {
			 System.out.println("Please enter K value:");
			  String kvalue = sc.nextLine();
			  K = CSCIConvert.Parse(kvalue,ERROR);
		 }
		 else
		 {
			 System.out.println("Enter second derivative:");
			 
				Expression secondderivativeExp = new ExpressionBuilder(sc.nextLine())
				        .variables("x")
				        .build();
				K = calculateKVal(secondderivativeExp, a, b);
			 //Function secondderivative = secondderivativeExp;
			 //double Max = findMax(secondderivative, a, b, 0.1);
		 }
		double ErrorTrapezoidal;
		double badifference = Math.pow((b-a),3);
		double nsquared = Math.pow(n,2);
		ErrorTrapezoidal = (K*(badifference))/(12*(nsquared));
				
		System.out.println("\nN Value: "+nvalue);
		System.out.println("Upper Bound: "+upperbound);
		System.out.println("Lower Bound: "+lowerbound);
		System.out.println("\nError Trapezoidal <= "+ErrorTrapezoidal);
		
		/*String function = "3x^2 + 4";
		System.out.println("\nTestingParsing... String function initialized, attempting Parse...");
		String alternative = inputXvar(function);
		System.out.println("\nSample Output: "+alternative);*/
		
	}
	
	public static double calculateKVal(Expression e, double a, double b)
	{
		double maxValue = e.setVariable("x", a).evaluate();
		for (double x = a, i = 0; x <= b; x += .1, i++)
		{
				double val = e.setVariable("x", x).evaluate();

				if (val > maxValue)
					maxValue = val;
		}
		/*Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
		        .variables("x", "y")
		        .build()
		        .setVariable("x", 2.3)
		        .setVariable("y", 3.14);
				double result = e.evaluate();
				System.out.println(""+result);*/
		double kSolution = maxValue;
		return kSolution;
	}
	
	public static String inputXvar(String function)
	{
		int testXint = (5);
		String alternative = function.replaceAll("x", "("+testXint+")");
		
		return alternative;
	}
	
	public static interface Function 
	{
        public double f(double x);
    }
	
	public static double findMax(Function f, double lowerBound, double upperBound, double step) 
	{
		double maxValue = f.f(lowerBound);

		for (double i=lowerBound; i <= upperBound; i+=step) {
			double currEval = f.f(i);
			if (currEval > maxValue) {
				maxValue = currEval;
			}
		}

		return maxValue;
	}
	
	public static double eval(final String str) 
	{
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			//        | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if      (eat('+')) x += parseTerm(); // addition
					else if (eat('-')) x -= parseTerm(); // subtraction
					else return x;
				}
			}

			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if      (eat('*')) x *= parseFactor(); // multiplication
					else if (eat('/')) x /= parseFactor(); // division
					else return x;
				}
			}

			double parseFactor() {
				if (eat('+')) return parseFactor(); // unary plus
				if (eat('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z') nextChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt")) x = Math.sqrt(x);
					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
					else throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new RuntimeException("Unexpected: " + (char)ch);
				}

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}

	public class Convert 
	{
		/*
		Class  Convert
			Purpose:
			 Utility class to provide methods to convert Data from String to int and Double.
			*/

		public static boolean IsNotInt(String TestString) 
		{

		//  Tests a String to see if it is an integer requtrns False if it is not an integer.

			int start = 0;
			
			try{
				start = Integer.parseInt(TestString);
				return false;
			}
				catch(NumberFormatException e){
				return true;
			}
		 }  // end IsNotInt
		 public static boolean IsNotDouble(String TestString) 
		 {
		//  Tests a String to see if it is an Double requtrns False if it is not an Double.
			double start = 0;
			
			try{
				start = Double.parseDouble(TestString);
				return false;
			}
				catch(NumberFormatException e){
				return true;
			}
		 }  // end IsNotInt
		 

		 public static int Parse(String InString, int ErrorValue) 
		 {
		//  Tries to Parse Value returns ErrorValue if there is an Error.
		
			int myValue = 0;
			// Check for negative integers.  If negative, we will just skip the first character when we test the rest of the character string for digits.
			
			try{
				myValue = Integer.parseInt(InString);
				return myValue;
			}
			catch(NumberFormatException e){
			return ErrorValue;
			}
		 }  // end Parse
		 public static double Parse(String InString, double ErrorValue) {

		//  Tries to Parse Value returns ErrorValue if there is an Error.

			double myValue = 0;
			// Check for negative integers.  If negative, we will just skip the first character when we test the rest of the character string for digits.
			
			try{
				myValue = Double.parseDouble(InString);
				return myValue;
		}
			catch(NumberFormatException e){
			return ErrorValue;
			}
		 }  // end Parse
		 public boolean Parse(char value, boolean ERROR){
			value = Character.toUpperCase(value);
			boolean result;
			switch(value){
				case ('T'):
				case ('Y'):
				case ('1'):
					result = true;
					break;
				case ('F'):
				case ('N'):
				case ('0'):
					result = false;
					break;
				default:
					result = ERROR;
					break;
			}
			return result;
		}// end Parse;

	} // end CSCIConvert

}