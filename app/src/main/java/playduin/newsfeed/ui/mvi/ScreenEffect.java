package playduin.newsfeed.ui.mvi;

public interface ScreenEffect<S extends FragmentContract.View> {
    void visit(S screen);
}
