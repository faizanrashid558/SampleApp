package com.example.sampleapp.di

import androidx.navigation.Navigator.Name
import com.example.sampleapp.data.qualifier.ClientQualifier
import com.example.sampleapp.data.qualifier.UserQualifier
import com.example.sampleapp.data.utils.Logger
import com.example.sampleapp.domain.interfaces.Userinterface
import com.example.sampleapp.domain.repository.ClientRepository
import com.example.sampleapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
abstract class AppModule {

//    @Provides
//    fun getUserInterface(): Userinterface {
//        return ClientRepository(Logger())
//    }
    // Named Qualifier and Custom Qualifier
    //Bind only work if Hilt know how to make its constructor
    @Binds
    @UserQualifier
    abstract fun getUserInterface(userRepository: UserRepository): Userinterface
    @Binds
    @ClientQualifier
    abstract fun getClientInterface(clientRepository: ClientRepository): Userinterface

}