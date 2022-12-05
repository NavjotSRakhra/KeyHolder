package ui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public GUI() {
        Controller controller = new Controller();

        JLabel timeLabel = new JLabel("Time (in seconds): ");
        JLabel keysLabel = new JLabel("Keys: ");

        JTextField timeTextField = new JTextField();
        timeTextField.setText("10");
        timeTextField.setInputVerifier(new NumericTextVerifier());

        JTextArea keysTextArea = new JTextArea();
        keysTextArea.setEnabled(false);
        keysTextArea.setPreferredSize(new Dimension(200, 50));
        keysTextArea.setLineWrap(true);

        JButton start = new JButton("Start");
        start.addActionListener(controller::actionOnStart);
        JButton stop = new JButton("Force Stop");
        stop.addActionListener(controller::actionOnStop);
        JButton keysToHoldDownButton = new JButton("Set keys to hold");
        keysToHoldDownButton.addActionListener(controller::setKeysToHoldDOwn);

        controller.setStop(stop);
        controller.setStart(start);
        controller.setKeysToHoldDownButton(keysToHoldDownButton);
        controller.setTimeTextField(timeTextField);
        controller.setKeysTextArea(keysTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(timeLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(timeTextField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(keysLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(keysTextArea, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 0.0;
        panel.add(keysToHoldDownButton, c);
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(start, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(stop, c);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JFrame frame = new JFrame("Key Holder");

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
