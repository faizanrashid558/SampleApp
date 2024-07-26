package com.example.sampleapp.domain.repository

import com.example.sampleapp.data.utils.Logger
import com.example.sampleapp.domain.interfaces.Userinterface
import javax.inject.Inject


class UserRepository @Inject constructor(private val logger: Logger):Userinterface{
    init {
        logger.logd("object created-1")
    }

    override fun saveUser(name:String, password:String){
        logger.logd("$name . $password")
    }
}

class ClientRepository @Inject constructor (private val logger: Logger):Userinterface{
    init {
        logger.logd("object created-2")
    }

    override fun saveUser(name:String, password:String){
        logger.logd("$name . $password")
    }
}