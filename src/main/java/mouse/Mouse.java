package mouse;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    private final List<MouseEventListener> listeners = new ArrayList<>();
    private MouseEventRegistry currentStatus = MouseEventRegistry.initial;
    private Long currentStatusTimestamp = 0L;
    private final long timeWindowInMillisecondsForDoubleClick = 500;

    public void pressLeftButton(long currentTimeInMilliseconds) {
        if (currentStatus == MouseEventRegistry.leftButtonReleased &&
                (currentTimeInMilliseconds - currentStatusTimestamp <= timeWindowInMillisecondsForDoubleClick)
        ) {
            notifySubscribers(MouseEventType.DoubleClick);
            currentStatus = MouseEventRegistry.leftButtonPressedTwice;
        } else {
            currentStatus = MouseEventRegistry.leftButtonPressed;
        }
        currentStatusTimestamp = currentTimeInMilliseconds;
    }

    public void releaseLeftButton(long currentTimeInMilliseconds) {
        if (currentStatus == MouseEventRegistry.leftButtonPressed) {
            notifySubscribers(MouseEventType.SingleClick);
        }
        currentStatus = MouseEventRegistry.leftButtonReleased;
        currentStatusTimestamp = currentTimeInMilliseconds;
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
        leftButtonPressedTwice, leftButtonReleased
    }
}