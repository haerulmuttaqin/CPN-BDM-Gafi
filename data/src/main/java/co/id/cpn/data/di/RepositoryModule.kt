package co.id.cpn.data.di

import co.id.cpn.data.repository.DistRepositoryImpl
import co.id.cpn.data.repository.MainRepositoryImpl
import co.id.cpn.data.repository.NewsRepositoryImpl
import co.id.cpn.domain.distribution.DistRepository
import co.id.cpn.domain.main.MainRepository
import co.id.cpn.domain.news.NewsRepository
import org.koin.dsl.module

val repositoryModule = module { 
    single<MainRepository> { MainRepositoryImpl(get(), get(), get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    single<DistRepository> { DistRepositoryImpl(get()) }
}