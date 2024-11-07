package net.micg.plantcare.models

sealed class ArticlePartListItem {
    data class TextItem(val text: String) : ArticlePartListItem()
    data class ImageItem(val imageResId: Int) : ArticlePartListItem()
}
