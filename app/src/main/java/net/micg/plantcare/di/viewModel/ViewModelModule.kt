package net.micg.plantcare.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.micg.plantcare.di.AppComponentScope
import net.micg.plantcare.presentation.alarmCreation.AlarmCreationViewModel
import net.micg.plantcare.presentation.alarms.AlarmsViewModel
import net.micg.plantcare.presentation.article.ArticleViewModel
import net.micg.plantcare.presentation.articles.ArticlesViewModel

@Module
interface ViewModelModule {
    @AppComponentScope
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmCreationViewModel::class)
    fun bindAlarmCreationViewModel(vm: AlarmCreationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmsViewModel::class)
    fun bindAlarmsViewModel(vm: AlarmsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    fun bindArticlesViewModel(vm: ArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    fun bindArticleViewModel(vm: ArticleViewModel): ViewModel
}
