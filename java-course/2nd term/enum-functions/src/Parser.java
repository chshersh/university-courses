
public class Parser {
	public Function parseExpression(String expression) {
        return lowPrior(expression);
    }
   
    private int skipBackBrackets(String expression, int i) {
        if (expression.charAt(i--) == ')') {
            int balance = -1;
            while (balance < 0) {
                if (expression.charAt(i) == '(') {
                    balance++;
                } else if (expression.charAt(i) == ')') {
                    balance--;
                }
                i--;
            }
        }
        return i;
    }

    private int skipTermBack(String expression) {
        int i = expression.length() - 1;
        while (i >= 0) {
            char c = expression.charAt(i);
            i = skipBackBrackets(expression, i);
            if (c != ' ') continue;
            if (expression.charAt(i) == '-' || expression.charAt(i) == '+') {
                return i;
            }
        }
        return -1;
    }
   
    private Function lowPrior(String expression) {
        int pos = skipTermBack(expression);
        if (pos != -1) {
            if (expression.charAt(pos) == '+') {
                return new BinOp(
                		lowPrior(expression.substring(0, pos)), 
                		highPrior(expression.substring(pos + 1)),
                		Bin.PLUS
                );
            } else {
                return new BinOp(
                		lowPrior(expression.substring(0, pos)), 
                		highPrior(expression.substring(pos + 1)),
                		Bin.MINUS
                );
            }
        }
        return highPrior(expression);
    }
   
    private Function highPrior(String expression) {
        int i = expression.length() - 1;
        while(i >= 0) {
            i = skipBackBrackets(expression, i);
            if (i <= 0) break;
            if (expression.charAt(i) == '*') {
                return new BinOp(
                		highPrior(expression.substring(0, i)), 
                		bracketPrior(expression.substring(i + 1)),
                		Bin.TIMES
                );
            } else if (expression.charAt(i) == '/') {
            	return new BinOp(
                		highPrior(expression.substring(0, i)), 
                		bracketPrior(expression.substring(i + 1)),
                		Bin.DIVISION
                );
            } else if (expression.charAt(i) == '%') {
            	return new BinOp(
                		highPrior(expression.substring(0, i)), 
                		bracketPrior(expression.substring(i + 1)),
                		Bin.MODUL
                );
            }
        }
        return bracketPrior(expression);
    }    
 
    private Function bracketPrior(String expression) {
        expression = expression.trim();
        if (expression.indexOf('(') != -1) {
                if (expression.charAt(0) == '-') {
                        return new BinOp(
                        		new TypeWrapper(Uno.CONST, "-1"),
                        		bracketPrior(expression.substring(1)),
                        		Bin.TIMES
                        );
                } else if (expression.charAt(0) == '+') {
                        expression = expression.substring(1);
                }
                return parseExpression(expression.substring(1, expression.length() - 1));
        }
        return varPrior(expression);
    }
 
    private Function varPrior(String expression) {
        if (expression.matches("-?\\+?\\d+")) {
            if (expression.charAt(0) == '+') {
                return new TypeWrapper(Uno.CONST, expression.substring(1));
            } else {
                return new TypeWrapper(Uno.CONST, expression);
            }
        } else if (expression.charAt(0) == '-') {
            return new BinOp(
            		new TypeWrapper(Uno.CONST, "-1"),
            		new TypeWrapper(Uno.VARIABLE, expression.substring(1)),
            		Bin.TIMES
            );
        } else if (expression.charAt(0) == '+') {
            expression = expression.substring(1);
        }
        return new TypeWrapper(Uno.VARIABLE, expression);
    }
}