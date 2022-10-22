package playduin.newsfeed.ui.mvi;

public abstract class ScreenState<T extends FragmentContract.View> {
    public abstract void visit(T screen);
}
