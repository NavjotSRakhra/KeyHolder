package ui;

import keyActions.KeyHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller {
    private final KeyHolder keyHolder;
    private JButton start;
    private JButton stop;
    private JTextField timeTextField;
    private JButton keysToHoldDownButton;
    private JTextArea keysTextArea;

    Controller() {
        try {
            keyHolder = new KeyHolder();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        keyHolder.setActionOnStop(this::actionOnKeyHolderStop);
    }

    public void setKeysTextArea(JTextArea keysTextArea) {
        this.keysTextArea = keysTextArea;
    }

    public void setKeysToHoldDownButton(JButton keysToHoldDownButton) {
        this.keysToHoldDownButton = keysToHoldDownButton;
    }

    public void setStart(JButton start) {
        this.start = start;
    }

    public void setStop(JButton stop) {
        this.stop = stop;
    }

    public void setTimeTextField(JTextField timeTextField) {
        this.timeTextField = timeTextField;
    }

    public void actionOnKeyHolderStop() {
        start.setEnabled(true);
        timeTextField.setEnabled(true);
        keysToHoldDownButton.setEnabled(true);
    }

    public void actionOnStart(ActionEvent e) {
        if (keysToHoldDownButton != null && timeTextField != null && stop != null && start != null) {
            start.setEnabled(false);
            timeTextField.setEnabled(false);
            keysToHoldDownButton.setEnabled(false);
            keyHolder.setRunningTimeSeconds(Integer.parseInt(timeTextField.getText()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            keyHolder.start();
        }
    }

    public void actionOnStop(ActionEvent e) {
        keyHolder.forceStop();
    }

    public void setKeysToHoldDOwn(ActionEvent e) {
        keyHolder.clearAllKeys();

        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Listening keys...", true);
        JLabel label = new JLabel("Listened Keys: ");
        JTextArea listenedKeys = new JTextArea();
        JButton reset = new JButton("Reset");
        JButton ok = new JButton("Ok");
        JPanel panel = new JPanel();

        ok.addActionListener(ev -> dialog.setVisible(false));
        reset.addActionListener(ev -> {
            keyHolder.clearAllKeys();
            listenedKeys.setText("");
            keysTextArea.setText("");
        });

        ok.setFocusable(false);
        reset.setFocusable(false);

        listenedKeys.setLineWrap(true);
        listenedKeys.setFocusable(false);
        listenedKeys.setEnabled(false);

        dialog.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyHolder.addKey(e.getKeyCode());
                listenedKeys.setText(keyHolder.getKeyNameList().toString());
                keysTextArea.setText(keyHolder.getKeyNameList().toString());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        listenedKeys.setPreferredSize(new Dimension(200, 50));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(label, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(listenedKeys, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(ok, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(reset, c);

        dialog.add(panel);

        dialog.pack();
        dialog.setVisible(true);
    }

    void debugPrint() {
        System.out.println(keyHolder);
    }
}
