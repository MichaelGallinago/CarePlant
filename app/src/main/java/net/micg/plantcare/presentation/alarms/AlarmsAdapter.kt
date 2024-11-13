package net.micg.plantcare.presentation.alarms

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
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

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        @SuppressLint("NotifyDataSetChanged")
        override fun run() {
            notifyDataSetChanged()
            handler.postDelayed(this, 60000)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = AlarmItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun removeItem(position: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val currentList = currentList.toMutableList()
        val item = currentList.removeAt(fromPosition)
        currentList.add(toPosition, item)
        submitList(currentList)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handler.removeCallbacks(runnable)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        handler.post(runnable)
    }

    inner class AlarmViewHolder(private val binding: AlarmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm) {
            with(binding) {
                name.text = alarm.name
                type.text = alarm.getTypeLabel()
                time.text = alarm.getTimeFormatedUntilNextAlarm()
                switchButton.isChecked = alarm.isEnabled

                root.setOnClickListener { onAlarmClick(alarm) }
                switchButton.setOnCheckedChangeListener { _, isChecked ->
                    onToggleClick(alarm, isChecked)
                }
            }
        }
    }

    class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
            oldItem == newItem
    }
}