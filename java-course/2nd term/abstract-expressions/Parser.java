public class Parser {

    public Functions parseExpression(String rest) {
        return lowPrior(rest);

    }
    
    private int skipBrackets(String expression, int i) {
    	if (expression.charAt(i++) == '(') {
    		int balance = 1;
        	while (balance > 0) {
        		if (expression.charAt(i) == '(') {
                    balance++;
                } else if (expression.charAt(i) == ')') {
                    balance--;
                }
                i++;
        	}
    	}
    	return i;
    }
    
    private int findNextPrior(String expression, int pos) {
    	while (pos < expression.length()) {
    		if (expression.charAt(pos) == '+' || 
    				expression.charAt(pos) == '-') {
    			return pos;
    		}
    		pos = skipBrackets(expression, pos);
    	}
    	return -1;
    }

    private Functions lowPrior(String rest) {
        int i = 0;
        char c = '*';
        while(i < rest.length()) {
            if (rest.charAt(i) != ' ') {
                c = rest.charAt(i);
            }
            i = skipBrackets(rest, i);
            if (i >= rest.length()) break;
            if (Character.toString(c).matches("\\*?/?%?")) continue;
            if (rest.charAt(i) == '+') {
                return new Plus(lowPrior(rest.substring(0, i)), lowPrior(rest.substring(i + 1)));
            } else if (rest.charAt(i) == '-' && findNextPrior(rest, i + 1) == -1) {
                return new Minus(lowPrior(rest.substring(0, i)), lowPrior(rest.substring(i + 1)));
            }
        }
        return highPrior(rest);
    }

 

    private Functions highPrior(String rest) {
        int i = 0;
        while(i < rest.length()) {
        	i = skipBrackets(rest, i);
            if (i >= rest.length()) break;
            if (rest.charAt(i) == '*') {
                return new Times(highPrior(rest.substring(0, i)), highPrior(rest.substring(i + 1)));
            } else if (rest.charAt(i) == '/') {
                return new Division(highPrior(rest.substring(0, i)), highPrior(rest.substring(i + 1)));
            } else if (rest.charAt(i) == '%') {
                return new Modul(highPrior(rest.substring(0, i)), highPrior(rest.substring(i + 1)));
            }
        }
        return bracketPrior(rest);
    }    

    private Functions unarySignReduction(Functions f, String expression, int left, int right) {
    	return null;
    }
    
    private Functions bracketPrior(String rest) {
        rest = rest.trim();
        if (rest.indexOf('(') != -1) {
        	if (rest.charAt(0) == '-') {
        		return new Times(new Const(-1), bracketPrior(rest.substring(1)));
        	} else if (rest.charAt(0) == '+') {
        		rest = rest.substring(1);
        	}
        	return parseExpression(rest.substring(1, rest.length() - 1));
        }
        return varPrior(rest);

    }

    private Functions varPrior(String rest) {
        if (rest.matches("-?\\d+")) {
            return new Const(Integer.parseInt(rest));
        } else if (rest.charAt(0) == '-') {
        	return new Times(new Const(-1), new Variable(rest.substring(1)));
        } else if (rest.charAt(0) == '+') {
        	rest = rest.substring(1);
        }
        return new Variable(rest);
    }

}