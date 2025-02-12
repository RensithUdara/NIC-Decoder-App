import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.*;

public class ID_PROJECT {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NIC Information Finder");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel nicLabel = new JLabel("Enter NIC number:");
        JTextField nicField = new JTextField(15);
        JButton findButton = new JButton("Find");
        JLabel dobLabel = new JLabel("Date of Birth:");
        JLabel dobResult = new JLabel();
        JLabel ageLabel = new JLabel("Age:");
        JLabel ageResult = new JLabel();
        JLabel genderLabel = new JLabel("Gender:");
        JLabel genderResult = new JLabel();
        
        gbc.gridwidth = 1;
        frame.add(nicLabel, gbc);
        gbc.gridx = 1;
        frame.add(nicField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(findButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        frame.add(dobLabel, gbc);
        gbc.gridx = 1;
        frame.add(dobResult, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(ageLabel, gbc);
        gbc.gridx = 1;
        frame.add(ageResult, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(genderLabel, gbc);
        gbc.gridx = 1;
        frame.add(genderResult, gbc);
        
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nic = nicField.getText();
                if (nic.length() != 10 && nic.length() != 12) {
                    JOptionPane.showMessageDialog(frame, "Invalid NIC Number");
                    return;
                }
                String year;
                int dayText;
                if (nic.length() == 10) {
                    year = "19" + nic.substring(0, 2);
                    dayText = Integer.parseInt(nic.substring(2, 5));
                } else {
                    year = nic.substring(0, 4);
                    dayText = Integer.parseInt(nic.substring(4, 7));
                }
                String gender;
                if (dayText > 500) {
                    gender = "Female";
                    dayText -= 500;
                } else {
                    gender = "Male";
                }
                if (dayText < 1 || dayText > 366) {
                    JOptionPane.showMessageDialog(frame, "Invalid NIC Number");
                }
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                int[] daysInMonths = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                int monthIndex = 0;
                while (dayText > daysInMonths[monthIndex]) {
                    dayText -= daysInMonths[monthIndex];
                    monthIndex++;
                }
                String dob = year + "-" + months[monthIndex] + "-" + dayText;
                LocalDate birthDate = LocalDate.of(Integer.parseInt(year), monthIndex + 1, dayText);
                LocalDate today = LocalDate.now();
                int age = Period.between(birthDate, today).getYears();
                dobResult.setText(dob);
                ageResult.setText(age + " years");
                genderResult.setText(gender);
            }
        });
        
        frame.setVisible(true);
    }
}