package mouse;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Mouse {
    private final PublishSubject<MouseAction> clickSubject = PublishSubject.create();

    public void pressLeftButton() {
        clickSubject.onNext(MouseAction.LeftButtonPressed);
    }

    public void releaseLeftButton() {
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