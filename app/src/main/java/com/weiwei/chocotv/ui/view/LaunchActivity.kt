package com.weiwei.chocotv.ui.view

import android.content.Intent
import android.os.Bundle
import com.weiwei.chocotv.R
import com.weiwei.chocotv.ui.base.BaseActivity
import com.weiwei.chocotv.util.Constant.Bundle_Link_Key
import java.util.*


class LaunchActivity : BaseActivity() {

    override fun getContentViewLayoutID(): Int {
        return  R.layout.activity_launch
    }

    override fun initView(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        parseAppLink()
        Timer().run {
            val launchTask = object : TimerTask() {
                override fun run() {
                    finish()
                    startActivity(
                        Intent(this@LaunchActivity, MainActivity::class.java)
                            .putExtra(Bundle_Link_Key, parseAppLink())
                    )
                }
            }
            schedule(launchTask, 3000)
        }
    }

    override fun getEnterTransAnimType(): Int {
        return TRANSITION_ANIM_SLIDE_FROM_BOTTOM
    }

    override fun getLeaveTransAnimType(): Int {
        return TRANSITION_ANIM_SLIDE_FROM_BOTTOM
    }

    private fun parseAppLink() : String{
        return intent?.data?.path?.replace("/dramas/", "").toString()
    }
}
