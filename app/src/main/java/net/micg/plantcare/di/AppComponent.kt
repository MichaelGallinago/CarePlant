package net.micg.plantcare.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.micg.plantcare.presentation.alarmCreation.AlarmCreationFragment
import net.micg.plantcare.presentation.alarms.AlarmsFragment
import net.micg.plantcare.presentation.article.ArticleFragment
import net.micg.plantcare.presentation.articles.ArticlesFragment

@AppComponentScope
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: AlarmsFragment)
    fun inject(fragment: ArticleFragment)
    fun inject(fragment: AlarmCreationFragment)
    fun inject(fragment: ArticlesFragment)
}
