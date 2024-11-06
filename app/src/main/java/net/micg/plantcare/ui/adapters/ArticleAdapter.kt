package net.micg.plantcare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.models.ArticlePartListItem
import net.micg.plantcare.models.ArticlePartViewType
import net.micg.plantcare.ui.adapters.ArticlesAdapter.DayTitleElementViewHolder
import net.micg.plantcare.ui.adapters.ArticlesAdapter.LessonElementViewHolder
import net.micg.plantcare.ui.utils.AdaptersUtils

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<ArticlePartListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ArticlePartViewType.Text.ordinal -> LessonElementViewHolder(
                LessonItemBinding.inflate(layoutInflater, parent, false)
            )

            ArticlePartViewType.Image.ordinal -> DayTitleElementViewHolder(
                parent.context, DayItemBinding.inflate(layoutInflater, parent, false)
            )

            else -> error(AdaptersUtils.ERROR_MESSAGE)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}