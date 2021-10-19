package co.id.cpn.domain.di
import co.id.cpn.domain.distribution.DistUseCase
import co.id.cpn.domain.distribution.DistUseCaseImpl
import co.id.cpn.domain.main.MainUseCase
import co.id.cpn.domain.main.MainUseCaseImpl
import co.id.cpn.domain.news.NewsUseCase
import co.id.cpn.domain.news.NewsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module { 
    single<MainUseCase> { MainUseCaseImpl(get()) }
    single<NewsUseCase> { NewsUseCaseImpl(get()) }
    single<DistUseCase> { DistUseCaseImpl(get()) }
}