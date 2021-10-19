package co.id.cpn.data.di

import androidx.room.Room
import co.id.cpn.data.local.AppDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module { 
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("bdm-gafi".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), AppDatabase::class.java,"real-bdm-gafi.db")
//            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single(override = true) { 
        get<AppDatabase>().distributionDao()
        get<AppDatabase>().regionDao()
        get<AppDatabase>().assetDao()
        get<AppDatabase>().customerDao()
        get<AppDatabase>().customerTypeDao()
        get<AppDatabase>().productOrderDao()
    }
}
