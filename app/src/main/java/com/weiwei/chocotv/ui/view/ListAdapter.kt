package com.weiwei.chocotv.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weiwei.chocotv.R
import com.weiwei.chocotv.data.DramaItem
import com.weiwei.chocotv.ui.base.BaseViewHolder
import com.weiwei.chocotv.util.getRoundTo2DecimalPlaces
import com.weiwei.chocotv.util.getTimeZoneFormatDate
import com.weiwei.chocotv.util.glide.GlideApp
import com.weiwei.chocotv.util.glide.ImageLoader.imageLoader
import java.util.ArrayList

class ListAdapter(private val listener: ListAdapterListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>(), Filterable {

    private var list : MutableList<DramaItem> = mutableListOf()
    private var filteredList = ArrayList<DramaItem>()
    private var query : String = ""

    override fun getItemCount(): Int {
        return if (query.isEmpty()) list.size else filteredList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_drama, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is ViewHolder) {
            if (query.isNotEmpty() && filteredList.size > 0) {
                holder.bind(filteredList[position])
            } else {
                holder.bind(list[position])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList: ArrayList<DramaItem>?
                query = constraint.toString()
                if (query.isEmpty()) {
                    filteredList = null
                } else {
                    filteredList = ArrayList()
                    for (item in list) {
                        if (item.name?.toLowerCase()?.contains(query)!!) {
                            filteredList.add(item)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredList.clear()
                if (results.values != null) {
                    filteredList.addAll(results.values as ArrayList<DramaItem>)
                }
                notifyDataSetChanged()
            }
        }
    }

    fun setData (list : List<DramaItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    @Deprecated("only temp use")
    fun getDataByID (id : String): DramaItem? {
        for (item in list) {
            Log.d("AppLink", item.dramaId.toString())
            if (item.dramaId.toString().toLowerCase() == id)
                return item
        }
        return null
    }

    inner class ViewHolder(itemView: View): BaseViewHolder<DramaItem>(itemView) {
        private var image = itemView.findViewById<ImageView>(R.id.imageView_item)
        private var title = itemView.findViewById<TextView>(R.id.textView_title)
        private var rating = itemView.findViewById<TextView>(R.id.textView_rating)
        private var date = itemView.findViewById<TextView>(R.id.textView_date)

        override fun bind (item : DramaItem) {
            imageLoader(image, item.thumb)
            title.text = item.name
            rating.text = item.rating.getRoundTo2DecimalPlaces().toString()
            date.text = item.createdTime.getTimeZoneFormatDate()

            itemView.setOnClickListener { listener.onItemClick(item,image) }
        }

    }

    interface ListAdapterListener {
        fun onItemClick(item: DramaItem, imageView: View)
    }
}