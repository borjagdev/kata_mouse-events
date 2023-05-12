package unit;

import io.reactivex.disposables.Disposable;
import mouse.Mouse;
import mouse.MouseAction;
import mouse.MouseEventListener;
import mouse.MouseEventType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MouseEventsKataTests {
    private final Mouse mouse = new Mouse();

    class MockEventListener implements MouseEventListener {
        private final Mouse mouse;
        private boolean wasLeftButtonPressed;
        private MouseEventType eventType;
        private Disposable clickSubscription;

        MockEventListener(Mouse mouse) {
            this.mouse = mouse;
        }

        @Override
        public void handleMouseEvent() {
            clickSubscription = mouse.getClickEventsChannel().subscribe(event -> {
                if (event == MouseAction.LeftButtonPressed) {
                    wasLeftButtonPressed = true;
                }
                else if (event == MouseAction.LeftButtonReleased && wasLeftButtonPressed) {
                    this.eventType = MouseEventType.SingleClick;
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

        mouse.pressLeftButton(0);
        mouse.releaseLeftButton(1);

        assertEquals(MouseEventType.SingleClick, eventListener.getEventType());
    }
}