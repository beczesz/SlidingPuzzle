package com.exarlabs.android.slidingpuzzle.business;

import javax.inject.Singleton;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.exarlabs.android.slidingpuzzle.ui.navigation.NavigationManager;

import dagger.Module;
import dagger.Provides;

/**
 * Generic main module providing application, resources and other singleton insatnces
 */
@Module
public class MainModule {
    private final Application app;

    public MainModule(Application application) {
        app = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    protected Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    protected NavigationManager provideNavigationManager() {
        return new NavigationManager();
    }

    @Provides
    @Singleton
    protected Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @Singleton
    protected SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(AppConstants.SP_NAME, ContextWrapper.MODE_PRIVATE);
    }
}
