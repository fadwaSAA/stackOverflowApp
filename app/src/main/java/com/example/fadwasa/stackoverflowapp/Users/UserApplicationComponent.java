package com.example.fadwasa.stackoverflowapp.Users;

import com.example.fadwasa.stackoverflowapp.http.ApiModuleForInfo;
import com.example.fadwasa.stackoverflowapp.root.ApplicationComponent;
import com.example.fadwasa.stackoverflowapp.root.UserScope;

import dagger.Component;

/**
 * Created by Fadwasa on 04/12/2018 AD.
 */



@Component( dependencies =  ApplicationComponent.class, modules = {  ApiModuleForInfo.class,UsersInfoModule.class })
@UserScope
public interface UserApplicationComponent {

    void inject(UsersActivity target);



}