package net.micg.plantcare.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.models.AlarmListItem
import net.micg.plantcare.models.AlarmViewType
import net.micg.plantcare.models.ArticlePartViewType
import net.micg.plantcare.ui.adapters.ArticlesAdapter.DayTitleElementViewHolder
import net.micg.plantcare.ui.adapters.ArticlesAdapter.LessonElementViewHolder
import net.micg.plantcare.ui.utils.AdaptersUtils

class AlarmsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<AlarmListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AlarmViewType.Water.ordinal -> LessonElementViewHolder(
                LessonItemBinding.inflate(layoutInflater, parent, false)
            )

            AlarmViewType.Fertilizer.ordinal -> DayTitleElementViewHolder(
                parent.context, DayItemBinding.inflate(layoutInflater, parent, false)
            )

            else -> error(AdaptersUtils.ERROR_MESSAGE)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayTitleElementViewHolder ->
                holder.onBind(data[position] as ScheduleListItem.DayTitleListItem)

            is LessonElementViewHolder ->
                holder.onBind(data[position] as LessonListItem)
        }
    }
}