package playduin.newsfeed;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import playduin.newsfeed.models.AppModule;
import playduin.newsfeed.models.DefaultAppModule;

@HiltAndroidApp
public class App extends Application {
    private static App app;
    private AppModule appModule;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appModule = new DefaultAppModule();
    }

    public AppModule getAppModule() {
        return appModule;
    }
}
