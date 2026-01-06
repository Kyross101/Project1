import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PayrollSystem extends JFrame implements ActionListener {
    private final JTextField nameField, hoursField, rateField, deductionField;
    private final JTextArea displayArea;
    private final JButton computeBtn, saveBtn, viewBtn, clearBtn;

    private final String FILE_NAME = "payroll.txt";

    public PayrollSystem() {
        setTitle("Employee Payroll System");
        setSize(650, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        inputPanel.add(new JLabel("Employee Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Hours Worked:"));
        hoursField = new JTextField();
        inputPanel.add(hoursField);

        inputPanel.add(new JLabel("Rate per Hour:"));
        rateField = new JTextField();
        inputPanel.add(rateField);

        inputPanel.add(new JLabel("Deductions:"));
        deductionField = new JTextField("0");
        inputPanel.add(deductionField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        computeBtn = new JButton("Compute");
        saveBtn = new JButton("Save");
        viewBtn = new JButton("View Records");
        clearBtn = new JButton("Clear");
        buttonPanel.add(computeBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(clearBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Payroll Summary"));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        computeBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        clearBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == computeBtn) computePayroll();
        else if (e.getSource() == saveBtn) saveRecord();
        else if (e.getSource() == viewBtn) viewRecords();
        else if (e.getSource() == clearBtn) displayArea.setText("");
    }

    private void computePayroll() {
        try {
            String name = nameField.getText();
            double hours = Double.parseDouble(hoursField.getText());
            double rate = Double.parseDouble(rateField.getText());
            double deduction = Double.parseDouble(deductionField.getText());

            double gross = hours * rate;
            double net = gross - deduction;

            displayArea.setText("EMPLOYEE PAYSLIP\n\n");
            displayArea.append("Name: " + name + "\n");
            displayArea.append("Hours Worked: " + hours + "\n");
            displayArea.append("Rate per Hour: ₱" + rate + "\n");
            displayArea.append("Gross Pay: ₱" + gross + "\n");
            displayArea.append("Deductions: ₱" + deduction + "\n");
            displayArea.append("Net Pay: ₱" + net + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please input valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveRecord() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(displayArea.getText());
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Record saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewRecords() {
        displayArea.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                displayArea.append(line + "\n");
            }
        } catch (IOException ex) {
            displayArea.setText("No records found.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayrollSystem().setVisible(true));
    }
}