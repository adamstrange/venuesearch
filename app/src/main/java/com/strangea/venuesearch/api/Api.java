package com.strangea.venuesearch.api;

import dagger.Component;

@Component(modules = ApiManagerModule.class)
public interface Api {
        ApiManager apiManager();
}
