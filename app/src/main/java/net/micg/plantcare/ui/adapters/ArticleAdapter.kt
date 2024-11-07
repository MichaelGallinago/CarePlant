package net.micg.plantcare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.databinding.ArticlePartImageItemBinding
import net.micg.plantcare.databinding.ArticlePartTextItemBinding
import net.micg.plantcare.models.ArticlePartListItem
import net.micg.plantcare.models.ArticlePartViewType
import net.micg.plantcare.ui.utils.AdaptersUtils

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<ArticlePartListItem>()

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ArticlePartListItem.TextItem -> ArticlePartViewType.Text.ordinal
            is ArticlePartListItem.ImageItem -> ArticlePartViewType.Image.ordinal
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
                holder.onBind(data[position] as ArticlePartListItem.TextItem)

            is ArticlePartImageViewHolder ->
                holder.onBind(data[position] as ArticlePartListItem.ImageItem)
        }
    }

    override fun getItemCount() = data.size

    inner class ArticlePartTextViewHolder(private val binding: ArticlePartTextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ArticlePartListItem.TextItem) {
            binding.textView.text = item.text
        }
    }

    inner class ArticlePartImageViewHolder(private val binding: ArticlePartImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ArticlePartListItem.ImageItem) {
            binding.imageView.setImageResource(item.imageResId)
        }
    }
}
