// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 1 - Infix Calculator

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class URCalculatorGUI{
    public static void main(String[] args) {
        JFrame f = new JFrame("URCalculator");
        f.setSize(500, 500); // 500 pixels by 500 pixels
        f.setLocation(300,200); // where on the screen it opens
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when clicking the X

        final JTextArea textArea = new JTextArea(10, 40); // where text is outputted (can be edited by the user)
        JScrollPane scrollPane = new JScrollPane(textArea);
        f.getContentPane().add(BorderLayout.CENTER, scrollPane); // puts it in the center
        
        final JTextField inputField = new JTextField(); // where user inputs expressions
        f.getContentPane().add(BorderLayout.NORTH, inputField); // puts it at the top

        final JButton button = new JButton("Calculate"); // click this after writing an expression in inputField
        f.getContentPane().add(BorderLayout.SOUTH, button); // puts it at the bottom
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(f.getWidth(), 80)); // changes size and font

        button.addActionListener(new ActionListener() { // waits for button to be pressed
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText().trim(); // retrieves expression from inputField
                if (!expression.isEmpty()) {
                    try {
                        String[] tokens = URCalculator.tokenize(expression);
                        URQueue<String> postfix = URCalculator.translate(tokens);
                        String ans = String.format("%.2f", URCalculator.postFixEval(postfix)); // same steps from URCalculator
                        textArea.append(expression + " = " + ans + "\n"); // shows answer in textArea
                    } catch (Exception ex) {
                        textArea.append("Error: " + ex.getMessage() + "\n");
                    }
                    inputField.setText(""); // clears inputField after calculating
                }
            }
        });
        f.setVisible(true);
    }
}