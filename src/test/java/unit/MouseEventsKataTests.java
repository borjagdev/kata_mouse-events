package unit;

import mouse.Mouse;
import mouse.MouseEventListener;
import mouse.MouseEventType;
import org.junit.Test;
import org.junit.runner.RunWith;

import static mouse.MouseEventType.SingleClick;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MouseEventsKataTests {
    private MouseEventListenerSpy mouseEventListenerSpy = new MouseEventListenerSpy();
    private final Mouse mouse = new Mouse();

    // TODO:
    //  single click
    //     no click, several clicks after a long time, multiples clicks without release?
    //  double click
    //     clicks, click + move + click != double click
    //  triple click
    //      click + move + click != double click
    //  drag
    //        move without press
    //  drop
    //       no move

    @Test
    public void not_notify_a_single_click_when_there_is_no_release() {
        long currentTimestamp = System.currentTimeMillis();
        mouse.subscribe(mouseEventListenerSpy);

        mouse.pressLeftButton(currentTimestamp);

        assertNull(mouseEventListenerSpy.handlerCalledWith);
    }

    @Test
    public void notify_a_single_click() {
        long currentTimestamp = System.currentTimeMillis();
        mouse.subscribe(mouseEventListenerSpy);

        mouse.pressLeftButton(currentTimestamp);
        mouse.releaseLeftButton(currentTimestamp + 1);

        assertEquals(SingleClick, mouseEventListenerSpy.handlerCalledWith);
    }

    @Test
    public void notify_a_single_click_when_release_time_is_long_after_click() {
        long currentTimestamp = System.currentTimeMillis();
        mouse.subscribe(mouseEventListenerSpy);

        mouse.pressLeftButton(currentTimestamp);
        mouse.releaseLeftButton(currentTimestamp + 1000);

        assertEquals(SingleClick, mouseEventListenerSpy.handlerCalledWith);
    }



    class MouseEventListenerSpy implements MouseEventListener {
        public MouseEventType handlerCalledWith;

        @Override
        public void handleMouseEvent(MouseEventType eventType) {
            handlerCalledWith = eventType;
        }
    }
}