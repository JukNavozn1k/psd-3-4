package src.main.java.com.smallinteger;

import javax.swing.*;
import java.awt.*;


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

        // Создаем текстовое поле для отображения
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        add(display, gbc);

        // Создаем панель с кнопками
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Определяем кнопки
        String[] buttonLabels = {
            "(", ")", "C", "<-",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "%", "=", "+"
        };

        // Добавляем кнопки на панель
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonPanel.add(button);
            
            button.addActionListener(e -> handleButton(label));
        }

        gbc.gridy = 1;
        gbc.weighty = 0.85;
        add(buttonPanel, gbc);

        // Начальный размер окна
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void handleButton(String label) {
        switch (label) {
            case "C":
                display.setText("");
                break;
            case "<-":
                String text = display.getText();
                if (!text.isEmpty()) {
                    display.setText(text.substring(0, text.length() - 1));
                }
                break;
            case "=":
                calculateResult();
                break;
            default:
                if (startNewInput) {
                    display.setText("");
                    startNewInput = false;
                }
                display.setText(display.getText() + label);
        }
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
                // Устанавливаем Look and Feel системы
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SmallIntegerGUICalculator().setVisible(true);
        });
    }
}
