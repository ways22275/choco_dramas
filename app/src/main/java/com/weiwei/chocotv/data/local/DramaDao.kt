package com.weiwei.chocotv.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weiwei.chocotv.data.DramaItem
import com.weiwei.chocotv.util.Constant.Table_Drama
import io.reactivex.Single

@Dao
abstract class DramaDao : BaseDao<DramaItem> {

    @Query("SELECT * FROM $Table_Drama")
    abstract fun getList(): Single<List<DramaItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<DramaItem>)
}