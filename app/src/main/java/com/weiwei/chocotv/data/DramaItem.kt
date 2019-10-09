package com.weiwei.chocotv.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.weiwei.chocotv.util.Constant.Table_Drama
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Table_Drama)
@Parcelize
data class DramaItem(

    @SerializedName("drama_id")
    @PrimaryKey
    @ColumnInfo
    val dramaId: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("total_views")
    @ColumnInfo
    val totalViews: Int,

    @SerializedName("created_at")
    @ColumnInfo
    val createdTime: String?,

    @SerializedName("thumb")
    @ColumnInfo
    val thumb: String?,

    @SerializedName("rating")
    @ColumnInfo
    val rating: Float
) : Parcelable