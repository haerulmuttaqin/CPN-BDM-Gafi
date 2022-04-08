package co.id.cpn.bdmgafi.di

import co.id.cpn.bdmgafi.ui.customer.CustomerViewModel
import co.id.cpn.bdmgafi.ui.dashboard.DashboardViewModel
import co.id.cpn.bdmgafi.ui.login.LoginViewModel
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.bdmgafi.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CustomerViewModel(get()) }
    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get(), get(), get()) }
}