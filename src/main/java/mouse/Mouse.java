package mouse;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    private final List<MouseEventListener> listeners = new ArrayList<>();
    private MouseEventRegistry currentStatus = MouseEventRegistry.initial;
    private final long timeWindowInMillisecondsForDoubleClick = 500;

    public void pressLeftButton(long currentTimeInMilliseconds) {
        currentStatus = MouseEventRegistry.leftButtonPressed;
    }

    public void releaseLeftButton(long currentTimeInMilliseconds) {
        if (currentStatus == MouseEventRegistry.leftButtonPressed) {
            notifySubscribers(MouseEventType.SingleClick);
        }
        currentStatus = MouseEventRegistry.leftButtonReleased;
    }

    public void move(MousePointerCoordinates from, MousePointerCoordinates to, long
            currentTimeInMilliseconds) {
        /*... implement this method ...*/
    }

    public void subscribe(MouseEventListener listener) {
        listeners.add(listener);
    }

    private void notifySubscribers(MouseEventType eventType) {
        listeners.forEach(listener -> listener.handleMouseEvent(eventType));
    }


    private enum MouseEventRegistry {
        initial,
        leftButtonPressed,
        leftButtonReleased
    }
}