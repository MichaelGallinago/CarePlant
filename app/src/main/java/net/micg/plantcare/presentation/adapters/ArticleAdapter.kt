package net.micg.plantcare.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.databinding.ArticlePartImageItemBinding
import net.micg.plantcare.databinding.ArticlePartTextItemBinding
import net.micg.plantcare.data.models.ArticlePart
import net.micg.plantcare.data.models.ArticlePartViewType
import net.micg.plantcare.presentation.utils.AdaptersUtils

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<ArticlePart>()

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ArticlePart.TextItem -> ArticlePartViewType.Text.ordinal
            is ArticlePart.ImageItem -> ArticlePartViewType.Image.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ArticlePartViewType.Text.ordinal -> ArticlePartTextViewHolder(
                ArticlePartTextItemBinding.inflate(layoutInflater, parent, false)
            )

            ArticlePartViewType.Image.ordinal -> ArticlePartImageViewHolder(
                ArticlePartImageItemBinding.inflate(layoutInflater, parent, false)
            )

            else -> error(AdaptersUtils.ERROR_MESSAGE)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticlePartTextViewHolder ->
                holder.onBind(data[position] as ArticlePart.TextItem)

            is ArticlePartImageViewHolder ->
                holder.onBind(data[position] as ArticlePart.ImageItem)
        }
    }

    override fun getItemCount() = data.size

    inner class ArticlePartTextViewHolder(private val binding: ArticlePartTextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ArticlePart.TextItem) {
            binding.textView.text = item.text
        }
    }

    inner class ArticlePartImageViewHolder(private val binding: ArticlePartImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ArticlePart.ImageItem) {
            binding.imageView.setImageResource(item.imageResId)
        }
    }
}
