package com.hearing.aitchat.selector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearing.aitchat.R
import com.hearing.aitchat.common.IEnhanceData

/**
 * @author liujiadong
 * @since 2020/10/21
 */
class DataAdapter : RecyclerView.Adapter<DataAdapter.DataHolder>() {
    private var data = mutableListOf<IEnhanceData>()
    var onDataChooseListener: OnDataChooseListener? = null

    fun setData(data: List<IEnhanceData>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val holder = DataHolder.create(parent)
        holder.onDataChooseListener = onDataChooseListener
        return holder
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        if (position >= 0 && position < data.size) {
            holder.fillData(data[position])
        }
    }

    interface OnDataChooseListener {
        fun select(data: IEnhanceData)
    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup): DataHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_selector_item, parent, false)
                return DataHolder(view)
            }
        }

        private val labelTv = itemView.findViewById<TextView>(R.id.label_tv)
        var onDataChooseListener: OnDataChooseListener? = null

        fun fillData(data: IEnhanceData) {
            itemView.setOnClickListener {
                onDataChooseListener?.select(data)
            }
            labelTv.text = data.label()
        }
    }
}