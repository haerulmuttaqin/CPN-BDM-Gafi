package co.id.cpn.bdmgafi

import android.app.Application
import co.id.cpn.bdmgafi.di.viewModelModule
import co.id.cpn.data.di.*
import co.id.cpn.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    networkNewsModule,
                    databaseModule,
                    tempDatabaseModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}