package com.exarlabs.android.slidingpuzzle.business;

import javax.inject.Singleton;

import android.app.Application;

import dagger.Component;

/**
 * DAgger nterface which connects all the modules
 * Created by becze on 9/17/2015.
 */
@Singleton
@Component(modules = { BoardModule.class, MainModule.class, PersistenceModule.class })
public interface DaggerGameComponent extends DaggerComponentGraph {

    final class Initializer {

        public static DaggerGameComponent init(Application app) {

            //@formatter:off
            return DaggerDaggerGameComponent.builder()
                            .mainModule(new MainModule(app))
                            .boardModule(new BoardModule())
                            .persistenceModule(new PersistenceModule(app))
                            .build();
            //@formatter:on
        }

    }

}
