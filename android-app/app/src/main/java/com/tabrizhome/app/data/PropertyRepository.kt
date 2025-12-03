package com.tabrizhome.app.data

import com.tabrizhome.app.data.remote.TabrizHomeApi
import com.tabrizhome.app.data.remote.dto.PropertyDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

data class Property(
    val id: Long,
    val title: String,
    val content: String,
    val excerpt: String,
    val imageUrl: String?,
    val price: String?,
    val area: String?,
    val rooms: String?,
    val city: String?,
    val district: String?,
    val date: String?
)

@Singleton
class PropertyRepository @Inject constructor(
    private val api: TabrizHomeApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchProperties(
        page: Int,
        perPage: Int,
        search: String?
    ): Result<List<Property>> = runCatching {
        withContext(ioDispatcher) {
            api.getProperties(page = page, perPage = perPage, search = search).map { it.toDomain() }
        }
    }

    suspend fun fetchProperty(id: Long): Result<Property> = runCatching {
        withContext(ioDispatcher) {
            api.getProperty(id).toDomain()
        }
    }
}

private fun PropertyDto.toDomain(): Property {
    val imageUrl = embedded?.media?.firstOrNull()?.sourceUrl
    return Property(
        id = id,
        title = title?.rendered.orEmpty(),
        content = content?.rendered.orEmpty(),
        excerpt = excerpt?.rendered.orEmpty(),
        imageUrl = imageUrl,
        price = meta?.price,
        area = meta?.area,
        rooms = meta?.rooms,
        city = meta?.city,
        district = meta?.district,
        date = date
    )
}
