package net.micg.plantcare.presentation.models

import android.webkit.JavascriptInterface
import net.micg.plantcare.presentation.article.ArticleFragment

class JsBridge(private val articleFragment: ArticleFragment) {
    @JavascriptInterface
    fun onSectionsStateChanged(allClosed: Boolean) {
        articleFragment.onSectionStateChanged(allClosed)
    }
}
