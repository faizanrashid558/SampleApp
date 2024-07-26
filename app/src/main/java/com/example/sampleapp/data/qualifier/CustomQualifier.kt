package com.example.sampleapp.data.qualifier

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class UserQualifier()

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class ClientQualifier()
