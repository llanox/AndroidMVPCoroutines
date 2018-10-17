package com.gabo.ramo.domain

interface InteractorCommand<U,T>{

    suspend fun execute(param :T) :U

}