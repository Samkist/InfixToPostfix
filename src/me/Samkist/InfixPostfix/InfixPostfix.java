package me.Samkist.InfixPostfix;

import BreezySwing.GBFrame;

import javax.swing.*;

public class InfixPostfix extends GBFrame {

    private static JFrame frame = new InfixPostfix();
    private static StringBuffer mathLogs = new StringBuffer();
    private JTextField inputField = addTextField("", 1,1 ,2 ,1);
    private JButton inputButton = addButton("Input", 2, 1, 1, 1);
    private JButton resetButton = addButton("Reset", 2, 2, 1,1);
    private JButton exitButton = addButton("Exit", 3, 1, 2, 1);
    private JTextArea outputField = addTextArea("", 6,1,2,3);

    public static void main(String[] args) {
        frame.setSize(400, 400);
        frame.setTitle("Infix To Postfix Evaluator");
        frame.setVisible(true);
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(inputButton)) {
            String postfix = new Converter().convertInfixPostfix(inputField.getText());
            outputField.setText("");
            mathLogs.append("Postfix is " + postfix);
            mathLogs.append("\n" + new Evaluator().evaluate(postfix));
            outputField.setText(mathLogs.toString());
            mathLogs = new StringBuffer();
            inputField.grabFocus();
        }
        if(jButton.equals(resetButton)) {
            inputField.setText("");
            outputField.setText("");
            inputField.grabFocus();
        }
        if(jButton.equals(exitButton)) {
            System.exit(0);
        }
    }

    public static StringBuffer getMathLogs() {
        return mathLogs;
    }


}
