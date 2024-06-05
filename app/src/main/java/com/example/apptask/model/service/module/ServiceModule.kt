
package com.example.apptask.model.service.module

import com.example.apptask.model.service.AccountService
import com.example.apptask.model.service.ConfigurationService
import com.example.apptask.model.service.LogService
import com.example.apptask.model.service.StorageService
import com.example.apptask.model.service.impl.AccountServiceImpl
import com.example.apptask.model.service.impl.ConfigurationServiceImpl
import com.example.apptask.model.service.impl.LogServiceImpl
import com.example.apptask.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

  @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

  @Binds
  abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}
