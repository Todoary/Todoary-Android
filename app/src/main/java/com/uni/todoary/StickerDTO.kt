package com.uni.todoary.feature.main.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SetSticker(
    @SerializedName("created") val created: List<CreatedSticker>?,
    @SerializedName("modified") val modified: List<ModifiedSticker>?,
    @SerializedName("deleted") val deleted: List<DeletedSticker>?
)

data class CreatedSticker(
    @SerializedName("stickerId") val stickerId: Int,
    @SerializedName("locationX") val locationX: Double,
    @SerializedName("locationY") val locationY: Double,
    @SerializedName("width") val width: Double,
    @SerializedName("height") val height: Double,
    @SerializedName("rotation") val rotation: Double,
    @SerializedName("flipped") val flipped: Boolean
)

data class ModifiedSticker(
    @SerializedName("Id") val Id: Int,
    @SerializedName("locationX") val locationX: Double,
    @SerializedName("locationY") val locationY: Double,
    @SerializedName("width") val width: Double,
    @SerializedName("height") val height: Double,
    @SerializedName("rotation") val rotation: Double,
    @SerializedName("flipped") val flipped: Boolean
)

data class DeletedSticker(
    @SerializedName("Id") val Id: Int
)

data class GetSticker(
    @SerializedName("id") val id: Long,
    @SerializedName("diaryId") val diaryId: Long,
    @SerializedName("stickerId") val stickerId: Int,
    @SerializedName("locationX") val locationX: Double,
    @SerializedName("locationY") val locationY: Double,
    @SerializedName("width") val width: Double,
    @SerializedName("height") val height: Double,
    @SerializedName("rotation") val rotation: Double,
    @SerializedName("flipped") val flipped: Boolean,
    @SerializedName("created_date") val createDate: String
)
