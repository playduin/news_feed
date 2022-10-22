package playduin.newsfeed.ui.mvi;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;

public class FragmentContract {
    public interface ViewModel<T, S> extends LifecycleObserver {
        void onStateChanged(Lifecycle.Event event);

        LiveData<T> getStateObservable();

        LiveData<S> getEffectObservable();
    }

    public interface View {

    }

    public interface Host {

    }
}
