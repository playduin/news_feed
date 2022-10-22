package playduin.newsfeed.ui.mvi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<
        VIEW extends FragmentContract.View,
        STATE extends ScreenState<VIEW>,
        EFFECT extends ScreenEffect<VIEW>,
        VIEW_MODEL extends FragmentContract.ViewModel<STATE, EFFECT>,
        HOST extends FragmentContract.Host>
        extends NavHostFragment implements FragmentContract.View, Observer<STATE>, LifecycleEventObserver {

    private VIEW_MODEL model;
    private HOST fragmentHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // keep the call back
        try {
            fragmentHost = (HOST) context;
        } catch (Throwable e) {
            final String hostClassName = ((Class) ((ParameterizedType) getClass().
                    getGenericSuperclass()).getActualTypeArguments()[1]).getCanonicalName();
            throw new RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // release the callback
        fragmentHost = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final VIEW_MODEL model = createModel();
        setModel(model);
        if (model != null) {
            getLifecycle().addObserver(this);
            getModel().getStateObservable().observe(this, this);
            getModel().getEffectObservable().observe(this, action -> action.visit((VIEW) this));
        }
    }

    protected abstract VIEW_MODEL createModel();

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        final VIEW_MODEL model = getModel();
        if (model != null) {
            model.onStateChanged(event);

            if (getLifecycle().getCurrentState().ordinal() <= Lifecycle.State.DESTROYED.ordinal()) {
                getLifecycle().removeObserver(this);
                model.getEffectObservable().removeObservers(this);
                model.getStateObservable().removeObservers(this);
            }
        }
    }

    @Override
    public void onChanged(STATE screenState) {
        screenState.visit((VIEW) this);
    }

    protected boolean hasHost() {
        return fragmentHost != null;
    }

    protected HOST getFragmentHost() {
        return fragmentHost;
    }

    protected VIEW_MODEL getModel() {
        return model;
    }

    protected void setModel(VIEW_MODEL model) {
        this.model = model;
    }
}
