package co.id.cpn.data.di

import androidx.room.Room
import co.id.cpn.data.local.TempDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val tempDatabaseModule = module { 
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("bdm-gafi".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), TempDatabase::class.java,"bdm-gafi.db")
//            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single(override = true) {
        get<TempDatabase>().assetDao()
        get<TempDatabase>().customerDao()
        get<TempDatabase>().customerTypeDao()
        get<TempDatabase>().productOrderDao()
    }
}
