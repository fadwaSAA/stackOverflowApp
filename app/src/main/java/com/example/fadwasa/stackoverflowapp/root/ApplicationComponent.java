package com.example.fadwasa.stackoverflowapp.root;

import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,  ApiModuleForInfo.class})
public interface ApplicationComponent {
}
