package com.weiwei.chocotv.ui.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weiwei.chocotv.R

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        // for overridePendingTransition
        const val TRANSITION_ANIM_DEFAULT = 0
        const val TRANSITION_ANIM_SLIDE_FROM_RIGHT = 1
        const val TRANSITION_ANIM_SLIDE_FROM_LEFT = 2
        const val TRANSITION_ANIM_SLIDE_FROM_BOTTOM = 3
        const val TRANSITION_ANIM_FADE = 4
    }

    /**
     * 獲取佈局id
     */
    protected abstract fun getContentViewLayoutID(): Int
    /**
     * 初始化控制元件
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    private var mEnterTransAnimType : Int? = null
    private var mLeaveTransAnimType : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEnterTransAnimType  = getEnterTransAnimType()
        mLeaveTransAnimType = getLeaveTransAnimType()
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID())
            initView(savedInstanceState)
        }
        overrideEnterTransAnim()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing)
            overrideLeaveTransAnim()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun getEnterTransAnimType() : Int

    abstract fun getLeaveTransAnimType() : Int

    private fun overrideEnterTransAnim () {
        var enterType = TRANSITION_ANIM_DEFAULT
        var exitType = TRANSITION_ANIM_DEFAULT

        when (mEnterTransAnimType) {
            TRANSITION_ANIM_SLIDE_FROM_RIGHT -> {
                enterType = R.anim.slide_in_right
                exitType = R.anim.stay
            }
            TRANSITION_ANIM_SLIDE_FROM_LEFT -> {
                enterType = R.anim.slide_in_left
                exitType = R.anim.stay
            }
            TRANSITION_ANIM_SLIDE_FROM_BOTTOM -> {
                enterType = R.anim.slide_in_bottom
                exitType = R.anim.stay
            }
            TRANSITION_ANIM_FADE -> {
                enterType = R.anim.fade_in
                exitType = R.anim.fade_out
            }
        }
        overridePendingTransition(enterType, exitType)
    }

    private fun overrideLeaveTransAnim () {
        var leaveType = TRANSITION_ANIM_DEFAULT
        var exitType = TRANSITION_ANIM_DEFAULT

        when (mLeaveTransAnimType) {
            TRANSITION_ANIM_SLIDE_FROM_RIGHT -> {
                leaveType = R.anim.stay
                exitType = R.anim.slide_out_right
            }
            TRANSITION_ANIM_SLIDE_FROM_LEFT -> {
                leaveType = R.anim.stay
                exitType = R.anim.slide_out_left
            }
            TRANSITION_ANIM_SLIDE_FROM_BOTTOM -> {
                leaveType = R.anim.stay
                exitType = R.anim.slide_out_bottom
            }
            TRANSITION_ANIM_FADE -> {
                leaveType = R.anim.fade_out
                exitType = R.anim.fade_in
            }
        }
        overridePendingTransition(leaveType, exitType)
    }

    protected fun log (tag : String , msg : String) {
        Log.d(tag, msg)
    }

    protected fun toast(desc: String) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show()
    }
}