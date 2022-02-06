package fold;



public class Calculator {
	
	private static final String[] ops = {"+","-","*","/"};
	
	private static String replaceAt(String str,int at,char replacement) {
        char[] arr = str.toCharArray();
        for(int i=0;i<arr.length;i++) {
            if(i==at) {
                arr[i] = replacement;
                break;
            }
        }
        return new String(arr);
    }
    
    public static double operation(String expression, String op) {
        
        if(!"+-*/".contains(op) || expression.matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
            if (expression.contains("m")) {
                expression = expression.replaceAll("m","-");
            }
            return Double.parseDouble(expression);
        }
        if (expression.charAt(0) == '-') {
            expression = replaceAt(expression,0,'m');// 5*(-1
        }
        
        if (op=="+" || !(op=="*"||op=="/")) {
            for(int i=1;i<expression.length();i++) {
                char chr = expression.charAt(i);
                char chr_prev = expression.charAt(i-1);
                if (chr == '-') {
                    if ("+*/".contains(chr_prev+"")) {
                        expression = replaceAt(expression,i,'m');
                    }
                }
            }
        }
        int index_op = String.join("",ops).indexOf(op);
        if(!expression.contains(op) && index_op<3) 
        return operation(expression,index_op<0?"o":ops[index_op+1]);
        if(!(op=="+"||op=="-")) {
            expression = expression.replaceAll("m","-");
        }
        String[] by_op = expression.split(op=="+"||op=="*"?"\\"+op:op);
        double result=0;
        switch (op) {
            case "+":
                result = 0;
                for(String ex : by_op) {
                    result += operation(ex,"-");
                }
                return result;
            case "-":
                result = operation(by_op[0],"*");
                for(int i=1;i<by_op.length;i++) {
                    // String ex = by_op[i];
                    result -= operation(by_op[i],"*");
                }
                return result;
            case "*":
                result = 1;
                for(String ex : by_op) {
                    result *= operation(ex,"/");
                }
                return result;
            case "/":
                result = operation(by_op[0],"o");
                for(int i=1;i<by_op.length;i++) {
                    // const ex = by_op[i];
                    result /= operation(by_op[i],"o");
                }
                return result;
        }
        return result;
    }

    public static double eval(String expression) {
        if(expression.matches("^-?[0-9]\\d*(\\.\\d+)?$")) return Double.parseDouble(expression);
        String simple_expression = "";
        for(int i=0;i<expression.length();i++) {
            char chr = expression.charAt(i);
            if (chr == '(') {
                String rec_expression = "";
                int nesting = 1;
                do {
                    i++;
                    rec_expression += expression.charAt(i);
                    if (expression.charAt(i) == '(')
                        nesting++;
                    if (expression.charAt(i) == ')') 
                        nesting--;
                } while (nesting>0);
                rec_expression = rec_expression.substring(0,rec_expression.length()-1);
                rec_expression = eval(rec_expression)+"";
                simple_expression += rec_expression;
            } else {
                simple_expression += chr;
            }
        }
        return operation(simple_expression,"+");
    }
}
