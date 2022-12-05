package keyActions;

import action.Action;
import controllers.IllegalTimeValueException;
import controllers.TimeController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyHolder implements Runnable {
    private final Robot robot;
    private final List<Integer> keyEventKeyCodes;
    private Thread thread;
    private TimeController timeControllerNano = null;
    private int runningTimeSeconds;
    private Action actionOnStop;

    public KeyHolder() throws AWTException {
        robot = new Robot();
        keyEventKeyCodes = new ArrayList<>();
        runningTimeSeconds = 10;
    }

    public void setRunningTimeSeconds(int runningTimeSeconds) {
        this.runningTimeSeconds = runningTimeSeconds;
    }

    public void forceStop() {
        if (timeControllerNano != null)
            timeControllerNano.over();
    }

    public void addKey(int keycode) {
        keyEventKeyCodes.add(keycode);
    }

    public void clearAllKeys() {
        keyEventKeyCodes.clear();
    }

    public List<String> getKeyNameList() {
        List<String> ret = new ArrayList<>();
        for (Integer keyEventKeyCode : keyEventKeyCodes) {
            ret.add(KeyEvent.getKeyText(keyEventKeyCode));
        }
        return ret;
    }

    public void start() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void setActionOnStop(Action actionOnStop) {
        this.actionOnStop = actionOnStop;
    }

    @Override
    public void run() {
        try {
            timeControllerNano = new TimeController((long) (runningTimeSeconds * 1E+09));
        } catch (IllegalTimeValueException e) {
            throw new RuntimeException(e);
        }
        timeControllerNano.start();
        while (!timeControllerNano.isOver()) {
            for (Integer keyEventKeyCode : keyEventKeyCodes) {
                robot.keyPress(keyEventKeyCode);
            }
            robot.delay(20);
        }
        int i = 50;
        while (i-- > 0) {
            for (Integer keyEventKeyCode : keyEventKeyCodes) {
                robot.keyRelease(keyEventKeyCode);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        timeControllerNano = null;
        if (actionOnStop != null)
            actionOnStop.execute();
    }

    @Override
    public String toString() {
        return "keyEventKeyCodes: " +
                keyEventKeyCodes +
                "\n" +
                "runningTimeSeconds: " +
                runningTimeSeconds +
                "\n" +
                "timeController: [" +
                timeControllerNano +
                "]";
    }
}
