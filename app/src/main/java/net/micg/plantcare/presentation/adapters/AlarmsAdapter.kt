package net.micg.plantcare.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.data.models.alarm.Alarm
import net.micg.plantcare.databinding.AlarmItemBinding

class AlarmsAdapter(
    private val onAlarmClick: (Alarm) -> Unit,
    private val onToggleClick: (Alarm, Boolean) -> Unit
) : ListAdapter<Alarm, AlarmsAdapter.AlarmViewHolder>(AlarmDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = AlarmItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    inner class AlarmViewHolder(private val binding: AlarmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm) {
            binding.apply {
                name.text = alarm.name
                type.text = alarm.getTypeLabel()
                time.text = alarm.getTimeFormatted()
                days.text = alarm.getDaysOfWeekLabel()
                switchButton.isChecked = alarm.isEnabled

                root.setOnClickListener { onAlarmClick(alarm) }
                switchButton.setOnCheckedChangeListener { _, isChecked ->
                    onToggleClick(alarm, isChecked)
                }
            }
        }
    }

    class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }
    }
}