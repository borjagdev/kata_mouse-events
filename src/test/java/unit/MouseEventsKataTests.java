package unit;

import mouse.Mouse;
import mouse.MouseEventListener;
import mouse.MouseEventType;
import mouse.MousePointerCoordinates;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static mouse.MouseEventType.SingleClick;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public void single_click() {
        long currentTimestamp = System.currentTimeMillis();
        mouse.subscribe(mouseEventListenerSpy);

        mouse.pressLeftButton(currentTimestamp);
        mouse.releaseLeftButton(currentTimestamp + 1);

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