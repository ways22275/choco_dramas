package com.weiwei.chocotv.ui.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weiwei.chocotv.data.DramaItem
import com.weiwei.chocotv.data.DramaRepository
import com.weiwei.chocotv.util.InjectorUtils.Companion.providerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class DramaViewModel : ViewModel() {

    private var repository: DramaRepository? = providerRepository()
    private var list : MutableLiveData<List<DramaItem>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun fetchList() {
        val disposable = repository!!.fetchList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { dramaList ->
                    list.value = dramaList
                },
                { e ->

                }
            )
        compositeDisposable.add(disposable)
    }

    fun getKeyWord() : String? {
        return repository?.getKeyWordFromSP()
    }

    fun setKeyWord(string : String) {
        repository?.setKeyWord(string)
    }

    fun getListLiveData() = list

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}