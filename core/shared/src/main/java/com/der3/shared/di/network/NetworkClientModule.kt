package com.der3.shared.di.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.der3.shared.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.der3.shared.domain.model.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.UnknownHostException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkClientModule {


    @Provides
    @Singleton
    fun chuckerInterceptor(
        @ApplicationContext context: Context
    ) = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()


    @Singleton
    @Provides
    fun provideJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false // it removes nulls from JSON
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = false
        prettyPrint = true
        coerceInputValues = true
        allowStructuredMapKeys = true
    }


    @Provides
    @Singleton
    fun provideHttpClient(
        json: Json,
        chuckerInterceptor: ChuckerInterceptor
    ) : HttpClient = HttpClient(OkHttp) {
        engine {
            addInterceptor(chuckerInterceptor)
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 120_000 // 2 minutes
            requestTimeoutMillis = 120_000
        }


        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            level = if (BuildConfig.DEBUG)
                LogLevel.ALL else
                LogLevel.NONE
            logger = object : Logger {
                override fun log(message: String) {
                    if (BuildConfig.DEBUG) {
                        println("HttpClient: $message")
                    }
                }
            }
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            header("Accept", "application/json")
            header("User-Agent", "PrayerTimesApp/1.0")

            // Add compression support
            header("Accept-Encoding", "gzip, zstd")
        }


        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = when (exception) {
                    is RedirectResponseException -> {
                        val code = exception.response.status.value
                        NetworkException.UnknownNetworkException(exception.message, code)
                    }

                    is ClientRequestException -> {
                        val code = exception.response.status.value
                        val message = exception.message
                        when (code) {
                            400 -> NetworkException.BadRequestException(message, code)
                            401 -> NetworkException.UnauthorizedException(message, code)
                            403 -> NetworkException.ForbiddenException(message, code)
                            404 -> NetworkException.NotFoundException(message, code)
                            else -> NetworkException.UnknownNetworkException(message, code)
                        }
                    }

                    is ServerResponseException -> {
                        val code = exception.response.status.value
                        val message = exception.message
                        when (code) {
                            500 -> NetworkException.InternalServerException(message, code)
                            503 -> NetworkException.ServiceUnavailableException(message, code)
                            else -> NetworkException.UnknownNetworkException(message, code)
                        }
                    }

                    is UnknownHostException -> NetworkException.NoInternetException()
                    is io.ktor.client.network.sockets.ConnectTimeoutException,
                    is io.ktor.client.network.sockets.SocketTimeoutException -> NetworkException.TimeoutException()

                    else -> NetworkException.UnknownNetworkException(exception.message, null)
                }
                throw clientException
            }
        }

    }


}