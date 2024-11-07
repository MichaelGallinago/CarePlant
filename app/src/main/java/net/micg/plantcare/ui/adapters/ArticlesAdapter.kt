package net.micg.plantcare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.databinding.ArticleItemBinding
import net.micg.plantcare.models.ArticleListItem

class ArticlesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<ArticleListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBinding.inflate(layoutInflater, parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TextViewHolder) {
            holder.onBind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    inner class TextViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ArticleListItem) {
            binding.name.text = item.name
        }
    }
}
