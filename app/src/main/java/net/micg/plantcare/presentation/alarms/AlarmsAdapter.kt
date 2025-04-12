package net.micg.plantcare.presentation.alarms

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.R
import net.micg.plantcare.databinding.AlarmItemBinding
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.presentation.models.TimeConverter


class AlarmsAdapter(
    private val onToggleClick: (Alarm, Boolean) -> Unit,
    private val timeConverter: TimeConverter,
    private val onEditClick: (Alarm) -> Unit,
    private val onDeleteClick: (Alarm) -> Unit,
) : ListAdapter<Alarm, AlarmsAdapter.AlarmViewHolder>(AlarmDiffUtil()) {

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        // I know about "submitList()", but I still need to update all the items at the same time.
        // Also, I don't like the blinking of list items when updating.
        @SuppressLint("NotifyDataSetChanged")
        override fun run() {
            notifyDataSetChanged()
            handler.postDelayed(this, 60000)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlarmViewHolder(
        AlarmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onToggleClick,
        timeConverter,
    )

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handler.removeCallbacks(runnable)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        handler.post(runnable)
    }

    fun removeItem(position: Int) = with(currentList.toMutableList()) {
        removeAt(position)
        submitList(this)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) = with(currentList.toMutableList()) {
        val item = removeAt(fromPosition)
        add(toPosition, item)
        submitList(this)
    }

    inner class AlarmViewHolder(
        private val binding: AlarmItemBinding,
        private val onToggleClick: (Alarm, Boolean) -> Unit,
        private val timeConverter: TimeConverter,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) = with(binding) {
            name.text = alarm.name
            type.text = alarm.type
            time.text = alarm.getFormattedTime(timeConverter)

            with(switchButton) {
                setOnCheckedChangeListener(null)
                isChecked = alarm.isEnabled
                setOnCheckedChangeListener { _, isChecked -> onToggleClick(alarm, isChecked) }
            }

            root.setOnLongClickListener {
                showPopupMenu(it, alarm, adapterPosition)
                true
            }
        }

        private fun showPopupMenu(
            anchor: View, alarm: Alarm, position: Int
        ) = with(PopupMenu(ContextThemeWrapper(anchor.context, R.style.PopupMenuOverlay), anchor)) {
            inflate(R.menu.alarm_menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        onEditClick(alarm)
                        notifyItemChanged(position)
                        true
                    }
                    R.id.menu_delete -> {
                        onDeleteClick(alarm)
                        removeItem(position)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
            oldItem == newItem
    }
}
