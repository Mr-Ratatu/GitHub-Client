package com.github.client.di

import com.github.client.data.repository.ProfileRepository
import com.github.client.data.repository.UserListRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { ProfileRepository(get()) }

    single { UserListRepository(get()) }

}