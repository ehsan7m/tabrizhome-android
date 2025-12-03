package com.tabrizhome.app.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rendered(
    @Json(name = "rendered") val rendered: String?
)

@JsonClass(generateAdapter = true)
data class MediaItem(
    @Json(name = "source_url") val sourceUrl: String?
)

@JsonClass(generateAdapter = true)
data class Embedded(
    @Json(name = "wp:featuredmedia") val media: List<MediaItem>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class PropertyMeta(
    @Json(name = "price") val price: String? = null,
    @Json(name = "area") val area: String? = null,
    @Json(name = "rooms") val rooms: String? = null,
    @Json(name = "city") val city: String? = null,
    @Json(name = "district") val district: String? = null
)

@JsonClass(generateAdapter = true)
data class PropertyDto(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: Rendered? = null,
    @Json(name = "content") val content: Rendered? = null,
    @Json(name = "excerpt") val excerpt: Rendered? = null,
    @Json(name = "_embedded") val embedded: Embedded? = null,
    @Json(name = "meta") val meta: PropertyMeta? = null,
    @Json(name = "date") val date: String? = null
)
