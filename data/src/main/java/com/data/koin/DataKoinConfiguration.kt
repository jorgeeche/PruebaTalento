package com.data.koin

import com.data.AuthInterceptor
import com.data.BuildConfig
import com.data.operations.contact.ContactRepositoryImpl
import com.data.operations.marvel.MarvelRemoteDataSource
import com.data.operations.marvel.MarvelRepositoryImpl
import com.data.operations.login.LoginRemoteDataSource
import com.data.operations.marvel.MarvelDataSource
import com.data.operations.user.UserRepositoryImpl
import com.data.operations.padel.PadelInMemoryDataSource
import com.data.operations.padel.PadelPreferencesDataSource
import com.data.operations.padel.contributed.MarvelRetrofit
import com.data.operations.padel.contributed.UserRetrofit
import com.data.operations.prefs.KeyStoreManager
import com.data.operations.prefs.SecurePreferences
import com.data.operations.prefs.SecurePreferencesImpl
import com.data.operations.remote.RemoteDataSourceExecutor
import com.data.operations.remote.ResponseParser
import com.data.operations.user.UserInMemoryDataSource
import com.data.operations.user.UserPreferencesDataSource
import com.domain.operations.contact.ContactRepository
import com.domain.operations.marvel.MarvelRepository
import com.domain.operations.user.UserRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class DataKoinConfiguration(private val baseUrl: String) {

    companion object {
        private const val CONNECT_TIMEOUT = 15L
        private const val READ_TIMEOUT = 15L
    }

    fun getModule() = module {

        single<SecurePreferences> { SecurePreferencesImpl(androidContext(), get(), get()) }
        single { KeyStoreManager() }

        // Repositories
        single<ContactRepository> { ContactRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get()) }
        single<MarvelRepository> { MarvelRepositoryImpl(get(),get(),get()) }


        // In memory data sources
        single { UserInMemoryDataSource() }
        single { PadelInMemoryDataSource() }

        // Preferences data sources
        single { UserPreferencesDataSource(get()) }
        single { PadelPreferencesDataSource() }

        // Remote data sources
        single { RemoteDataSourceExecutor(get()) }
        single{ MarvelDataSource() }
        single{ MarvelRemoteDataSource(get(),get()) }
        single{ LoginRemoteDataSource(get(), get()) }

        // Retrofit
        single { SSLConfig(BuildConfig.FLAVOR, androidContext()) }
        single { AuthInterceptor(get(), get()) }

        single("talentoClient") { createOkHttpClient(get(), get()) }
        single("retrofit") { createRetrofit(get("talentoClient")) }

        single { createRetrofitImplementation<MarvelRetrofit>(get("retrofit")) }
        single { createRetrofitImplementation<UserRetrofit>(get("retrofit")) }

        // Response parsers
        single { ResponseParser(get()) }

    }

    private fun createOkHttpClient(sslConfig: SSLConfig, authInterceptor: AuthInterceptor): OkHttpClient {
        val certificateSha256 = sslConfig.extractCertificate()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return if (certificateSha256 != null) {
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .certificatePinner(buildCertificatePinner(certificateSha256))
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()
        }
    }



    private fun buildCertificatePinner(sha256: String): CertificatePinner {
        return CertificatePinner.Builder()
            .add(baseUrl, sha256)
            .build()
    }


    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = createRetrofit(okHttpClient, baseUrl)


    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    private inline fun <reified T> createRetrofitImplementation(retrofit: Retrofit): T = retrofit.create(T::class.java)

}
