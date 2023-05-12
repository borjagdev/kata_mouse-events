package unit;

import io.reactivex.disposables.Disposable;
import mouse.Mouse;
import mouse.MouseAction;
import mouse.MouseEventListener;
import mouse.MouseEventType;
import org.junit.Test;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MouseEventsKataTests {
    private final Mouse mouse = new Mouse();

    static class MockEventListener implements MouseEventListener {
        private final Mouse mouse;
        private boolean wasLeftButtonPressed;
        private MouseEventType eventType;
        private Disposable clickSubscription;
        private boolean possibleDoubleClick;
        private long lastEvenTimestamp;
        private final long timeWindowInMillisecondsForDoubleClick = 500;

        MockEventListener(Mouse mouse) {
            this.mouse = mouse;
        }

        @Override
        public void handleMouseEvent() {
            clickSubscription = mouse.getClickEventsChannel().subscribe(event -> {
                if (event == MouseAction.LeftButtonPressed) {
                    wasLeftButtonPressed = true;
                }
                if (event == MouseAction.LeftButtonReleased) {
                    if (possibleDoubleClick && currentTimeMillis() - lastEvenTimestamp < timeWindowInMillisecondsForDoubleClick) {
                        this.eventType = MouseEventType.DoubleClick;
                        this.possibleDoubleClick = false;
                    } else if (wasLeftButtonPressed) {
                        this.eventType = MouseEventType.SingleClick;
                        this.possibleDoubleClick = true;
                    }
                    this.wasLeftButtonPressed = false;
                    this.lastEvenTimestamp = currentTimeMillis();
                }
            });
        }

        public MouseEventType getEventType() {
            return eventType;
        }

        public void stopHandlingEvents() {
            clickSubscription.dispose();
        }
    }

    // TODO:
    //  single click
    //     no click, several clicks after a long time, multiples clicks without release?
    // double click
    //     clicks, click + move + click != double click
    // triple click
    //     click + move + click != double click
    // drag
    //       move without press
    // drop
    //       no move


    @Test
    public void emit_single_click_event() {
        MockEventListener eventListener = new MockEventListener(mouse);
        eventListener.handleMouseEvent();

        mouse.pressLeftButton();
        mouse.releaseLeftButton();

        assertEquals(MouseEventType.SingleClick, eventListener.getEventType());
    }

    @Test
    public void not_emit_single_click_event() {
        MockEventListener eventListener = new MockEventListener(mouse);
        eventListener.handleMouseEvent();

        mouse.pressLeftButton();
        mouse.pressLeftButton();

        assertNull(eventListener.getEventType());
    }

    @Test
    public void emit_double_click_event() {
        MockEventListener eventListener = new MockEventListener(mouse);
        eventListener.handleMouseEvent();

        mouse.pressLeftButton();
        mouse.releaseLeftButton();
        mouse.pressLeftButton();
        mouse.releaseLeftButton();

        assertEquals(MouseEventType.DoubleClick, eventListener.getEventType());
    }

}