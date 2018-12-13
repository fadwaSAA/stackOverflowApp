package com.example.fadwasa.stackoverflowapp.root;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Fadwasa on 06/12/2018 AD.
 */


@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestionScope {
}