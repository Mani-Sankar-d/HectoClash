package com.example.hectoclash

import SequenceDataItem
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LevelAdapter(
    val context: Hectolevel,
    var numberList: List<NumberData>,
    var sequencedata: List<SequenceDataItem>,
    private var unlockedLevel: Int
) : RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val levelNumberTextView: TextView = itemView.findViewById(R.id.levelnum)
        val levelNumberTextView1: TextView = itemView.findViewById(R.id.levelnum1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.levelrecviewdesign, parent, false)
        return LevelViewHolder(view)
    }

    override fun getItemCount(): Int = numberList.size

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val currentNumberData = numberList[position]
        val levelNumber = currentNumberData.value
        val sequenceItem = sequencedata.getOrNull(position)

        holder.levelNumberTextView.text = "$levelNumber"
        holder.levelNumberTextView1.text = levelNumber.toString()

        Log.d("LevelAdapter", "Binding level: $levelNumber, Unlocked level: $unlockedLevel")

        if (levelNumber <= unlockedLevel) {
            holder.itemView.alpha = 1f
            holder.itemView.isEnabled = true
            holder.itemView.setOnClickListener {
                Log.d("LevelAdapter", "Clicked unlocked level: $levelNumber")
                if (sequenceItem != null) {
                    val intent = Intent(context, LevelDetailActivity::class.java)
                    intent.putExtra("clickedLevel", levelNumber.toString())
                    intent.putExtra("Sequencedata", sequenceItem.sequence)
                    intent.putExtra("Soln_of_seq", sequenceItem.solution)
                    intent.putExtra("soln_operator_seq", ArrayList(sequenceItem.operator_sequence))
                    context.startActivityForResult(intent, 1001)
                    Log.d("LevelAdapter", "Starting LevelDetailActivity for level: $levelNumber")
                }
            }
        } else {
            holder.itemView.alpha = 0.3f
            holder.itemView.isEnabled = false
            holder.itemView.setOnClickListener {
                Toast.makeText(context, "❌ Level Locked! Complete previous levels.", Toast.LENGTH_SHORT).show()
                Log.d("LevelAdapter", "Clicked locked level: $levelNumber")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUnlockedLevel(newUnlockedLevel: Int) {
        Log.d("LevelAdapter", "Updating unlocked level to: $newUnlockedLevel")
        unlockedLevel = newUnlockedLevel
        notifyDataSetChanged()
    }
}