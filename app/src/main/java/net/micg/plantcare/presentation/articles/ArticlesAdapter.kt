package net.micg.plantcare.presentation.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.databinding.ArticleItemBinding

class ArticlesAdapter(
    private val onArticleClick: (Article) -> Unit
) : ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class ArticleViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Article) {
            binding.name.text = item.title
            binding.root.setOnClickListener { onArticleClick(item) }
        }
    }

    class ArticleDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}
