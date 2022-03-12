package com.example.deskApplication.repository

import com.example.deskApplication.model.LocationModel
import com.example.deskApplication.model.LocationModelEnum
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : CrudRepository<LocationModel, String> {
    // @Query("select * from locations where type = :type ", nativeQuery = true)
    fun findLocationModelByType(@Param("type") type: LocationModelEnum): List<LocationModel>
}


