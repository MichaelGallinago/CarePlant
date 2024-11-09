package net.micg.plantcare.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.data.models.Alarm

class AlarmAdapter(
    private val onAlarmClick: (Alarm) -> Unit,
    private val onToggleClick: (Alarm, Boolean) -> Unit
) : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    inner class AlarmViewHolder(private val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm) {
            binding.apply {
                alarmName.text = alarm.name
                alarmType.text = alarm.type
                alarmTime.text = alarm.time
                alarmDate.text = alarm.date
                alarmSwitch.isChecked = alarm.isEnabled

                root.setOnClickListener { onAlarmClick(alarm) }
                alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
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