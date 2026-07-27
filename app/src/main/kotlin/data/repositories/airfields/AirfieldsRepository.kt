package com.dunihuliapps.myglidingassistant.data.repositories.airfields

import com.dunihuliapps.myglidingassistant.data.model.Airfield
import com.dunihuliapps.myglidingassistant.domain.datasources.airfields.AirfieldsLocalDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AirfieldsRepository {
    fun getAllAirfields(): Flow<List<Airfield>>
    suspend fun findById(airfieldId: Long): Airfield?
    suspend fun insertAirfield(name: String, latitude: Double, longitude: Double, isHome: Boolean = false): Long
    suspend fun deleteAirfield(airfield: Airfield): Int
    suspend fun update(id: Long, name: String, latitude: Double, longitude: Double, isHome: Boolean = false): Int
}

class AirfieldsRepositoryImpl @Inject constructor(
    private val airfieldsLocalDatasource: AirfieldsLocalDatasource
) : AirfieldsRepository {

    override fun getAllAirfields(): Flow<List<Airfield>> {
        return airfieldsLocalDatasource.getAllAirfields()
    }

    override suspend fun findById(airfieldId: Long): Airfield? {
        return airfieldsLocalDatasource.findById(airfieldId)
    }

    override suspend fun insertAirfield(name: String, latitude: Double, longitude: Double, isHome: Boolean): Long {
        return airfieldsLocalDatasource.insertAirfield(
            Airfield(name = name, latitude = latitude, longitude = longitude, isHome = isHome)
        )
    }

    override suspend fun deleteAirfield(airfield: Airfield): Int {
        return airfieldsLocalDatasource.deleteAirfield(airfield)
    }

    override suspend fun update(id: Long, name: String, latitude: Double, longitude: Double, isHome: Boolean): Int {
        return airfieldsLocalDatasource.update(
            Airfield(id = id, name = name, latitude = latitude, longitude = longitude, isHome = isHome)
        )
    }
}
