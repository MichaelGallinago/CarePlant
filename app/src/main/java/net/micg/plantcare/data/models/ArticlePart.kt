package net.micg.plantcare.data.models

sealed class ArticlePart {
    data class TextItem(val text: String) : ArticlePart()
    data class ImageItem(val imageResId: Int) : ArticlePart()
}
