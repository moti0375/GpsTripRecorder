package com.dunihuliapps.myglidingassistant.domain.datasources.airfields

import com.dunihuliapps.myglidingassistant.data.model.Airfield
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AirfieldsLocalDatasource {
    fun getAllAirfields(): Flow<List<Airfield>>
    suspend fun findById(airfieldId: Long): Airfield?
    suspend fun insertAirfield(airfield: Airfield): Long
    suspend fun deleteAirfield(airfield: Airfield): Int
    suspend fun update(airfield: Airfield): Int
}

class AirfieldsLocalDatasourceImpl @Inject constructor(
    private val airfieldsDao: AirfieldsDao
) : AirfieldsLocalDatasource {

    override fun getAllAirfields(): Flow<List<Airfield>> {
        return airfieldsDao.getAllAirfields()
    }

    override suspend fun findById(airfieldId: Long): Airfield? {
        return airfieldsDao.findById(airfieldId)
    }

    override suspend fun insertAirfield(airfield: Airfield): Long {
        return if (airfield.isHome) airfieldsDao.insertAsHome(airfield) else airfieldsDao.insert(airfield)
    }

    override suspend fun deleteAirfield(airfield: Airfield): Int {
        return airfieldsDao.delete(airfield)
    }

    override suspend fun update(airfield: Airfield): Int {
        return if (airfield.isHome) airfieldsDao.updateAsHome(airfield) else airfieldsDao.update(airfield)
    }
}
