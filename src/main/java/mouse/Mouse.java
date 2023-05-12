package mouse;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Mouse {
    private final PublishSubject<MouseAction> clickSubject = PublishSubject.create();
    private final long timeWindowInMillisecondsForDoubleClick = 500;

    public void pressLeftButton(long currentTimeInMilliseconds) {
        clickSubject.onNext(MouseAction.LeftButtonPressed);
    }

    public void releaseLeftButton(long currentTimeInMilliseconds) {
        clickSubject.onNext(MouseAction.LeftButtonReleased);
    }

    public void move(MousePointerCoordinates from, MousePointerCoordinates to, long
            currentTimeInMilliseconds) {
        /*... implement this method ...*/
    }

    public Observable<MouseAction> getClickEventsChannel() {
        return clickSubject.hide();
    }
}