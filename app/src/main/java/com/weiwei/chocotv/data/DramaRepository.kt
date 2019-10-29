package com.weiwei.chocotv.data

import com.weiwei.chocotv.data.local.DramaDao
import com.weiwei.chocotv.data.remote.DramaService
import com.weiwei.chocotv.util.sharePreference.SharePreferenceManager.getKeyWord
import com.weiwei.chocotv.util.sharePreference.SharePreferenceManager.putKeyWord
import io.reactivex.Single

class DramaRepository(private var dramaModel : DramaDao, private var dramaService: DramaService) {

    fun fetchList(): Single<List<DramaItem>?> {
        return dramaService.getDramaList()
            .doOnSuccess { dramaList ->
                setList(dramaList)
            }
            .onErrorResumeNext {
                it.printStackTrace()
                getListFromLocal()
            }
    }

    private fun getListFromLocal() = dramaModel.getList()

    fun getItemFromLocal(id : String) : Single<DramaItem>{
        return dramaModel.getItemByID(id)
    }

    private fun setList(dramas: List<DramaItem>?) = dramas?.let { dramaModel.insertAll(it) }

    fun getKeyWordFromSP() : String? {
        return getKeyWord()
    }

    fun setKeyWord(string : String) {
        putKeyWord(string)
    }
}