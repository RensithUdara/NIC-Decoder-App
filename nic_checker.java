import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.*;

public class ID_PROJECT {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NIC Information Finder");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        JPanel resultPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nicLabel = new JLabel("Enter NIC number:");
        JTextField nicField = new JTextField(15);
        JButton findButton = new JButton("Find Information");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nicLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(nicField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(findButton, gbc);

        JLabel dobLabel = new JLabel("Date of Birth:");
        JLabel dobResult = new JLabel();
        JLabel ageLabel = new JLabel("Age:");
        JLabel ageResult = new JLabel();
        JLabel genderLabel = new JLabel("Gender:");
        JLabel genderResult = new JLabel();

        resultPanel.add(dobLabel);
        resultPanel.add(dobResult);
        resultPanel.add(ageLabel);
        resultPanel.add(ageResult);
        resultPanel.add(genderLabel);
        resultPanel.add(genderResult);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nic = nicField.getText().trim();
                if (!isValidNIC(nic)) {
                    JOptionPane.showMessageDialog(frame, "Invalid NIC Number. Please enter a 10 or 12 digit NIC.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String[] dobInfo = calculateDOB(nic);
                    String dob = dobInfo[0];
                    String gender = dobInfo[1];

                    LocalDate birthDate = LocalDate.parse(dob);
                    LocalDate today = LocalDate.now();
                    int age = Period.between(birthDate, today).getYears();

                    dobResult.setText(dob);
                    ageResult.setText(age + " years");
                    genderResult.setText(gender);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred while processing the NIC.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static boolean isValidNIC(String nic) {
        return nic.length() == 10 || nic.length() == 12;
    }

    private static String[] calculateDOB(String nic) {
        String year;
        int dayText;
        if (nic.length() == 10) {
            year = "19" + nic.substring(0, 2);
            dayText = Integer.parseInt(nic.substring(2, 5));
        } else {
            year = nic.substring(0, 4);
            dayText = Integer.parseInt(nic.substring(4, 7));
        }

        String gender = (dayText > 500) ? "Female" : "Male";
        if (dayText > 500) {
            dayText -= 500;
        }

        if (dayText < 1 || dayText > 366) {
            throw new IllegalArgumentException("Invalid day value in NIC");
        }

        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int[] daysInMonths = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int monthIndex = 0;
        while (dayText > daysInMonths[monthIndex]) {
            dayText -= daysInMonths[monthIndex];
            monthIndex++;
        }

        String dob = year + "-" + months[monthIndex] + "-" + String.format("%02d", dayText);
        return new String[]{dob, gender};
    }
}