package me.Samkist.InfixPostfix;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Evaluator {
    private Stack<Double> numbers = new Stack<>();
    private final ArrayList<String> functions = new ArrayList<>();
    private final String ADD = "+";
    private final String SUBTRACT = "-";
    private final String MULTIPLY = "*";
    private final String DIVIDE = "/";
    private final String EXPONENT = "^";

    public Evaluator() {
        functions.add("+");
        functions.add("-");
        functions.add("*");
        functions.add("/");
        functions.add("^");
    }

    public double evaluate(String exp) {
        List<String> values = Arrays.asList(exp.split(" "));
        values.forEach(v -> {
            try {
                double num = Double.parseDouble(v);
                numbers.push(num);
            } catch(Exception e) {
                if(isOperand(v)) {
                    if(numbers.size() >= 2) {
                        double y = numbers.pop();
                        double x = numbers.pop();
                        switch(v) {
                            case ADD:
                                numbers.push(add(x, y));
                                break;
                            case SUBTRACT:
                                numbers.push(subtract(x, y));
                                break;
                            case MULTIPLY:
                                numbers.push(multiply(x, y));
                                break;
                            case DIVIDE:
                                numbers.push(divide(x, y));
                                break;
                            case EXPONENT:
                                numbers.push(exponent(x, y));
                                break;
                        }
                    } else {
                        System.out.println("Error");
                        System.exit(0);
                    }
                }
            }
        });
        try {
            return numbers.pop();
        } catch(Exception e) {
            System.out.println("Error");
            return 0;
        }
    }


    private double add(double x, double y) {
        InfixPostfix.getMathLogs().append("\nAdded " + x + " to " + y);
        return x + y;
    }

    private double subtract(double x, double y) {
        InfixPostfix.getMathLogs().append("\nSubtracted " + y + " from " + x);
        return x - y;
    }

    private double multiply(double x, double y) {
        InfixPostfix.getMathLogs().append("\nMultiplied " + x + " by " + y);
        return x * y;
    }

    private double divide(double x, double y) {
        InfixPostfix.getMathLogs().append("\nDivided " + x + " by " + y);
        return x / y;
    }

    private double exponent(double x, double y) {
        InfixPostfix.getMathLogs().append("\nRaised " + x + " to " + y);
        return Math.pow(x, y);
    }

    private boolean isOperand(String s) {
        return functions.stream().anyMatch(str -> str.equals(s));
    }
}
