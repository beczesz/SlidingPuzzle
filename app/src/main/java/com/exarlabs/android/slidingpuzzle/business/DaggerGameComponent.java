package com.exarlabs.android.slidingpuzzle.business;

import javax.inject.Singleton;

import android.app.Application;

import dagger.Component;

/**
 * DAgger nterface which connects all the modules
 * Created by becze on 9/17/2015.
 */
@Singleton
@Component(modules = { BoardModule.class, MainModule.class })
public interface DaggerGameComponent extends DaggerComponentGraph {

    final class Initializer {

        public static DaggerGameComponent init(Application app) {

            //@formatter:off
            return DaggerDaggerGameComponent.builder()
                            .boardModule(new BoardModule())
                            .mainModule(new MainModule(app))
                            .build();
            //@formatter:on
        }

    }

}
