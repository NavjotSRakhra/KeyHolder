package ui;

import javax.swing.*;
import java.awt.*;

public class NumericTextVerifier extends InputVerifier {
//    @Override
//    public boolean shouldYieldFocus(JComponent input) {
//        ((JTextField) input).setText("10");
//        return true;
//    }

    @Override
    public boolean verify(JComponent input) {
        try {

            Integer.parseInt(((JTextField) input).getText());
        } catch (IllegalArgumentException e) {
            Toolkit.getDefaultToolkit().beep();
            return false;
        }
        return true;
    }
}
