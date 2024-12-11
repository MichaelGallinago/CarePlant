package net.micg.plantcare.presentation.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.R
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import net.micg.plantcare.BuildConfig
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.databinding.ArticleGridItemBinding

class ArticlesAdapter(
    private val onArticleClick: (Article) -> Unit,
) : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
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
            image.load("${BuildConfig.BASE_URL}images/${item.icon}") {
                crossfade(true)
                error(R.drawable.ic_no_image)
                placeholder(R.drawable.ic_flower_placeholder)
                transformations(CircleCropTransformation())
            }
        }
    }

    class ArticleDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}
