package playduin.newsfeed.ui.mvi;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseViewModel<T, S> extends ViewModel implements FragmentContract.ViewModel<T, S> {
    protected final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<T> stateHolder = new MutableLiveData<>();
    private final MutableLiveData<S> effectHolder = new MutableLiveData<>();

    public LiveData<T> getStateObservable() {
        return stateHolder;
    }

    public LiveData<S> getEffectObservable() {
        return effectHolder;
    }

    protected void setState(T state) {
        stateHolder.setValue(state);
    }

    protected void setAction(S action) {
        effectHolder.setValue(action);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {

    }
}
