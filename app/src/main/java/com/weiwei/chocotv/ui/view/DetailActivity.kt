package com.weiwei.chocotv.ui.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weiwei.chocotv.R
import com.weiwei.chocotv.data.DramaItem
import com.weiwei.chocotv.ui.base.BaseActivity
import com.weiwei.chocotv.util.*
import com.weiwei.chocotv.util.Constant.Bundle_Parcelable_Key
import com.weiwei.chocotv.util.glide.GlideApp
import com.weiwei.chocotv.util.glide.ImageLoader

class DetailActivity : BaseActivity(){

    lateinit var image : ImageView
    lateinit var title : TextView
    lateinit var dateAndView  : TextView
    lateinit var rate  : TextView

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        val dramaItem =
            intent.getParcelableExtra(Bundle_Parcelable_Key) as DramaItem
        val date = dramaItem.createdTime.getTimeZoneFormatDate()
        val totalViews = dramaItem.totalViews.getTotalViewsFormat()

        image = findViewById(R.id.imageView_detail)
        title = findViewById(R.id.textView_title_desc)
        dateAndView = findViewById(R.id.textView_date_and_views_desc)
        rate = findViewById(R.id.textView_ratting_desc)

        ImageLoader.imageLoader(image, dramaItem.thumb)
        title.text = dramaItem.name
        rate.text = dramaItem.rating.getRoundTo2DecimalPlaces().toString()
        dateAndView.text = getString(R.string.drama_date_and_views, date, totalViews)

    }

    override fun getEnterTransAnimType(): Int {
        return TRANSITION_ANIM_DEFAULT
    }

    override fun getLeaveTransAnimType(): Int {
        return TRANSITION_ANIM_DEFAULT
    }

}