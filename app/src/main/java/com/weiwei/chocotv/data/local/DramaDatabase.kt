package com.weiwei.chocotv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weiwei.chocotv.data.DramaItem

@Database(entities = [DramaItem::class], version = 1)
abstract class DramaDatabase : RoomDatabase() {

    abstract fun dramaModel(): DramaDao
}