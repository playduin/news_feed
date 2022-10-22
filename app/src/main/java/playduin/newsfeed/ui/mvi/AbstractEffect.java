package playduin.newsfeed.ui.mvi;

public abstract class AbstractEffect<T extends FragmentContract.View> implements ScreenEffect<T> {
    private boolean isHandled = false;

    public void visit(T screen) {
        if (!isHandled) {
            handle(screen);
            isHandled = true;
        }
    }

    protected abstract void handle(T screen);
}
