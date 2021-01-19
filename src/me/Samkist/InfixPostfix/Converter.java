package me.Samkist.InfixPostfix;


import java.util.*;

public class Converter {
    private final Stack<String> operands = new Stack<>();
    private final HashMap<String, String> functions = new HashMap<>();

    public Converter() {
        functions.put("add", "+");
        functions.put("subtract", "-");
        functions.put("multiply", "*");
        functions.put("divide", "/");
        functions.put("exponent", "^");
        functions.put("opening", "(");
        functions.put("closing", ")");
    }

    public String convertInfixPostfix(String exp) {
        exp = exp.replaceAll("\\s", "");
        StringBuffer strBuffer = new StringBuffer();;
        List<String> values = Arrays.asList(exp.split(""));
        int x = 0;
        while(x < values.size()) {
            if(Character.isDigit(values.get(x).charAt(0))) {
                StringBuffer numBuf = new StringBuffer();
                while(x < values.size() && !isOperand(values.get(x))) {
                    numBuf.append(values.get(x));
                    x++;
                }
                try {
                    strBuffer.append(Double.parseDouble(numBuf.toString()) + " ");
                } catch (Exception e) {
                    System.out.println("Error");
                    continue;
                }
            } else {
                Precedence precedence;
                if(isStackEmpty()) {
                    precedence = Precedence.HIGHER;
                } else {
                    precedence = hasPrecedence(values.get(x), operands.peek());
                }
                if(values.get(x).equals("(")) {
                    operands.push("(");
                    x++;
                    continue;
                } else if(values.get(x).equals(")")) {
                    while(!isStackEmpty()) {
                        if(operands.peek().equals("(")) {
                            operands.pop();
                            break;
                        }
                        String pop = operands.pop();
                        strBuffer.append(pop + " ");
                    }
                    x++;
                    continue;
                } else if(precedence.equals(Precedence.HIGHER)) {
                    operands.push(values.get(x));
                } else if(precedence.equals(Precedence.LOWER)) {
                    if(!isStackEmpty()) {
                        if(operands.peek().equals("(")) {
                            x++;
                            continue;
                        }
                        String pop = operands.pop();
                        strBuffer.append(pop + " ");
                        operands.push(values.get(x));
                    }
                } else if(precedence.equals(Precedence.SAME)) {
                    String pop = operands.pop();
                    strBuffer.append(pop + " ");
                    operands.push(values.get(x));
                }
                x++;
            }
        }
        strBuffer.append(operands.pop());
        return strBuffer.toString();
    }

    public boolean isOperand(String s) {
        s.replaceAll("\\s", "");
        return functions.values().stream().anyMatch(str -> str.equals(s));
    }

    private boolean isStackEmpty() {
        try {
            operands.peek();
        } catch(Exception e) {
            return true;
        }
        return false;
    }

    public Precedence hasPrecedence(String x, String y) {
        if(y.equals("(")) {
            return Precedence.HIGHER;
        }
        if(x.equals(functions.get("exponent"))) {
            if(y.equals(functions.get("exponent"))) {
                return Precedence.SAME;
            } else {
                return Precedence.HIGHER;
            }
        } else if(y.equals(functions.get("exponent"))) {
            return Precedence.LOWER;
        } else if(isMultiplyOrDivide(x)) {
            if(isMultiplyOrDivide(y)) return Precedence.SAME;
            return Precedence.HIGHER;
        }  else if(isAddOrSubtract(x)) {
            if(isAddOrSubtract(y)) return Precedence.SAME;
            if(isMultiplyOrDivide(y)) return Precedence.LOWER;
        }
        return Precedence.HIGHER;
    }

    private boolean isMultiplyOrDivide(String x) {
        return x.equals(functions.get("multiply")) || x.equals(functions.get("divide"));
    }

    private boolean isAddOrSubtract(String x) {
        return x.equals(functions.get("add")) || x.equals(functions.get("subtract"));
    }

    private enum Precedence {
        LOWER, SAME, HIGHER
    }
}
