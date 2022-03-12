package com.example.deskApplication.service

import com.example.deskApplication.exception.EntryNotFoundException
import com.example.deskApplication.model.DeskModel
import com.example.deskApplication.model.LocationModel
import com.example.deskApplication.model.LocationModelEnum
import com.example.deskApplication.model.LocationModelRequest
import com.example.deskApplication.repository.LocationRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository,
) {
    fun findAllLocations(): List<LocationModel> {
        return locationRepository.findAll().toList()
    }

    fun getLocationById(id: String): LocationModel {
        return locationRepository.findById(id)
            .orElseThrow { EntryNotFoundException("Location does not exist", id) }
    }

    fun getLocationByType(type: LocationModelEnum): List<LocationModel> {
        return locationRepository.findLocationModelByType(type).toList()
    }

    fun createLocation(locationModelRequest: LocationModelRequest): String {
        var locationModel = LocationModel(name = locationModelRequest.name, type = locationModelRequest.type)
        locationRepository.save(locationModel)
        return "Create Location  ${locationModel.name}  successful"
    }

    fun deleteLocationById(id: String): String {
        try {
            locationRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw  EntryNotFoundException("Location with id '$id' not found")
        }

        return "Delete location with id: $id successful"
    }


    fun updateLocation(id: String, locationModelRequest: LocationModelRequest): String {
        val locationModel = getLocationById(id)
        locationModel.name = locationModelRequest.name
        locationModel.type = locationModelRequest.type
        locationRepository.save(locationModel)
        return "Update location with id: $id successful"
    }

    fun getAllDeskFromLocation(id: String): List<DeskModel> {
        try {
            val location: LocationModel = getLocationById(id)
            return location.desks
        } catch (e: EmptyResultDataAccessException) {
            throw  EntryNotFoundException("Location with id '$id' not found")
        }
    }

    fun save(location : LocationModel){
        locationRepository.save(location)
    }
}