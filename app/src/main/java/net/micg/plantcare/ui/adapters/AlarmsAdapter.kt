package net.micg.plantcare.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.micg.plantcare.models.AlarmListItem

class AlarmsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<AlarmListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}