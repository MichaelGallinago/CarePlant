package net.micg.plantcare.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.micg.plantcare.presentation.alarm.AlarmViewModel
import net.micg.plantcare.presentation.article.ArticleViewModel

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel::class)
    fun bindAlarmViewModel(vm: AlarmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    fun bindArticleViewModel(vm: ArticleViewModel): ViewModel
}
