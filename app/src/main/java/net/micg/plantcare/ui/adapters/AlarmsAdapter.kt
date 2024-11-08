package net.micg.plantcare.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.databinding.AlarmItemBinding
import net.micg.plantcare.models.AlarmListItem

class AlarmsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<AlarmListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlarmItemBinding.inflate(layoutInflater, parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlarmViewHolder) {
            holder.onBind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    inner class AlarmViewHolder(private val binding: AlarmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: AlarmListItem) {
            with(binding) {
                name.text = item.name
                type.text = item.type
                time.text = item.time
                date.text = item.date
            }
        }
    }
}