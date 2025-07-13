package com.loanmanagementapp.di

import android.content.Context
import com.loanmanagementapp.utils.resource.ResourceProvider
import com.google.gson.Gson
import com.loanmanagementapp.core.event.DefaultUiEventPublisher
import com.loanmanagementapp.core.event.UiEventPublisher
import com.loanmanagementapp.domain.repository.LoanRepository
import com.loanmanagementapp.data.service.LoanService
import com.loanmanagementapp.data.MockLoanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    /* @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("app_preferences")
        } */

    @Singleton
    @Provides
    fun provideEventPublisher(): DefaultUiEventPublisher {
        return UiEventPublisher()
    }

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Singleton
    fun provideLoanService(): LoanService = MockLoanService()

    @Provides
    @Singleton
    fun provideLoanRepository(loanService: LoanService): LoanRepository = LoanRepository(loanService)

    /* @Provides
    @Singleton
    fun provideAuthManager(
        securePrefs: SecureSharedPrefs
    ): AuthManager = AuthManagerImpl(securePrefs) */
} 