package main.java.com.smallinteger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SmallIntegerGUICalculator extends JFrame {
    private JTextField display;
    private final ExpressionEvaluator evaluator;
    private boolean startNewInput = false;

    public SmallIntegerGUICalculator() {
        evaluator = new ExpressionEvaluator();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("SmallInteger Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(300, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

      
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(true);
        // Enter handling
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleButton("=");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        add(display, gbc);

        // btn layout
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] buttonLabels = {
            "(", ")", "C", "<-",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "%", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonPanel.add(button);
            button.addActionListener(e -> handleButton(label));
        }

        gbc.gridy = 1;
        gbc.weighty = 0.85;
        add(buttonPanel, gbc);

        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void handleButton(String label) {
        if (startNewInput && !label.equals("=")) {
            display.setText("");
            startNewInput = false;
        }

        String currentText = display.getText();

        switch (label) {
            case "C":
                display.setText("");
                break;
            case "<-":
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
                break;
            case "=":
                calculateResult();
                break;
            default:
                if (isOperator(label)) {
                    if (!currentText.isEmpty() && isOperator(
                            String.valueOf(currentText.charAt(currentText.length() - 1)))) {
                        currentText = currentText.substring(0, currentText.length() - 1);
                        display.setText(currentText + label);
                        return;
                    }
                }
                display.setText(currentText + label);
        }
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%");
    }

    private void calculateResult() {
        try {
            String expression = display.getText();
            SmallInteger result = evaluator.evaluate(expression);
            display.setText(result.toString());
            startNewInput = true;
        } catch (Exception e) {
            display.setText("Error: " + e.getMessage());
            startNewInput = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SmallIntegerGUICalculator().setVisible(true);
        });
    }
}
