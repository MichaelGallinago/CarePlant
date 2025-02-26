package net.micg.plantcare.presentation.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.R
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import net.micg.plantcare.BuildConfig
import net.micg.plantcare.data.article.models.Article
import net.micg.plantcare.databinding.ArticleGridItemBinding

class ArticlesAdapter(
    private val onArticleClick: (Article) -> Unit,
) : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffUtil()) {
    private lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        parentContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleGridItemBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class ArticleViewHolder(private val binding: ArticleGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Article) = with(binding) {
            title.text = item.title
            root.setOnClickListener { onArticleClick(item) }
            image.load("$IMAGES_FOLDER${item.icon}") {
                crossfade(true)
                error(R.drawable.ic_no_image)
                placeholder(R.drawable.ic_flower_placeholder)
                transformations(TopRoundedCornersTransformation(parentContext, 16f))
                listener(
                    onStart = { image.scaleType = ImageView.ScaleType.CENTER_INSIDE },
                    onSuccess = { _, _ -> image.scaleType = ImageView.ScaleType.CENTER },
                    onError = { _, _ -> image.scaleType = ImageView.ScaleType.CENTER_INSIDE }
                )
            }
        }
    }

    class ArticleDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    companion object {
        private const val IMAGES_FOLDER = "${BuildConfig.WEB_STORAGE_URL}images/"
    }
}
