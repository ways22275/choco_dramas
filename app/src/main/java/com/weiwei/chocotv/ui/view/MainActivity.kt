package com.weiwei.chocotv.ui.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weiwei.chocotv.R
import com.weiwei.chocotv.data.DramaItem
import com.weiwei.chocotv.ui.base.BaseActivity
import com.weiwei.chocotv.ui.widget.GridItemDecoration
import com.weiwei.chocotv.ui.widget.GridRecyclerView
import com.weiwei.chocotv.util.ConnectReceiver
import com.weiwei.chocotv.util.Constant.Bundle_Link_Key
import com.weiwei.chocotv.util.Constant.Bundle_Parcelable_Key
import com.weiwei.chocotv.util.sharePreference.SharePreferenceManager.putNetworkEnable


class MainActivity : BaseActivity() , SwipeRefreshLayout.OnRefreshListener,
    ListAdapter.ListAdapterListener, SearchView.OnQueryTextListener,
    ConnectReceiver.ConnectivityReceiverListener{

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recycleView : GridRecyclerView
    private lateinit var searchView : SearchView

    private lateinit var viewModel: DramaViewModel
    private lateinit var adapter : ListAdapter

    private var connectReceiver = ConnectReceiver();

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(savedInstanceState: Bundle?) {
        registerReceiver(connectReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        viewModel = ViewModelProviders.of(this).get(DramaViewModel::class.java)
        initViewData()

        swipeRefreshLayout = findViewById(R.id.swipeRefreshView)
        swipeRefreshLayout.setOnRefreshListener(this)

        adapter = ListAdapter(this)

        recycleView = findViewById(R.id.recycleView)
        recycleView.also {
                it.adapter = adapter
                it.layoutManager = GridLayoutManager(this, 2)
                it.addItemDecoration(GridItemDecoration(10, 2)) }


        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(this)
    }

    override fun onStart() {
        super.onStart()
        connectReceiver.receiverListener = this
    }

    override fun onStop() {
        super.onStop()
        connectReceiver.receiverListener = null
    }

    override fun getEnterTransAnimType() : Int {
        return TRANSITION_ANIM_DEFAULT
    }

    override fun getLeaveTransAnimType() : Int {
        return TRANSITION_ANIM_DEFAULT
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.fetchList()
    }

    override fun onItemClick(item: DramaItem, imageView : View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity, imageView, ViewCompat.getTransitionName(imageView)!!
        )
        val intent= Intent(this@MainActivity, DetailActivity::class.java).also {
            it.putExtra(Bundle_Parcelable_Key, item)
        }
        startActivity(intent, options.toBundle())
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()) {
            viewModel.setKeyWord(p0)
            adapter.filter.filter(p0)
            swipeRefreshLayout.isEnabled = false
        }
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0.isNullOrEmpty()) {
            adapter.filter.filter("")
            viewModel.setKeyWord("")
            swipeRefreshLayout.isEnabled = true
        }
        searchView.clearFocus()
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        putNetworkEnable(isConnected)
    }

    private fun initViewData() {
        viewModel.getListLiveData().observe(this, Observer {
            runAnimation()
            adapter.setData(it)
            val keyword = viewModel.getKeyWord()
            if (!keyword.isNullOrEmpty()) {
                searchView.setQuery(keyword, true)
            }
            searchView.clearFocus()
        })
        viewModel.getItemLiveData().observe(this, Observer {
            gotoDetailPage(it)
        })
        viewModel.fetchList()

        val dramaID = intent.getStringExtra(Bundle_Link_Key)
        if (!dramaID.isNullOrEmpty()) {
            viewModel.getItemByID(dramaID)
        }
    }

    private fun runAnimation() {
        val controller =
            AnimationUtils.loadLayoutAnimation(this, R.anim.grid_layout_animation_fall_down)
        recycleView.layoutAnimation = controller
        adapter.notifyDataSetChanged()
        recycleView.scheduleLayoutAnimation()
    }

    private fun gotoDetailPage(item : DramaItem) {
        val intent= Intent(this@MainActivity, DetailActivity::class.java).also {
            it.putExtra(Bundle_Parcelable_Key, item)
        }
        startActivity(intent)
    }
}
