package edu.ucne.puntuacion.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.puntuacion.data.local.database.PuntuacionDb
import edu.ucne.puntuacion.data.remote.PuntuacionApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val BASE_URL = "https://xn--reseasapiap2-cmdpbggdeyembjcz-80c.canadacentral-01.azurewebsites.net/"

    fun providePuntuacionDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            PuntuacionDb::class.java,
            "Puntuacion.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providePuntuacionApi(moshi:Moshi): PuntuacionApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PuntuacionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReseñaDao(puntuacionDb: PuntuacionDb) = puntuacionDb.reseñaDao()

    @Provides
    @Singleton
    fun provideClienteDao(puntuacionDb: PuntuacionDb) = puntuacionDb.clienteDao()

    @Provides
    @Singleton
    fun provideProductDao(puntuacionDb: PuntuacionDb) = puntuacionDb.productoDao()



}