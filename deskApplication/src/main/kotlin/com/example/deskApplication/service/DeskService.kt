package com.example.deskApplication.service

import com.example.deskApplication.exception.EntryNotFoundException
import com.example.deskApplication.model.DeskModel
import com.example.deskApplication.model.DeskModelRequest
import com.example.deskApplication.model.LocationModel
import com.example.deskApplication.repository.DeskRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class DeskService(
    private val deskRepository: DeskRepository,
    private val locationService: LocationService
) {

    fun findAllDesks(): List<DeskModel> {
        return deskRepository.findAll().toList()
    }

    fun getDeskByID(id: String): DeskModel =
        deskRepository.findById(id).orElseThrow { EntryNotFoundException("Desk with id '$id' not found") }

    fun getDeskByName(name: String): DeskModel {
        return deskRepository.findDeskByName(name)
            .orElseThrow { EntryNotFoundException("Desk with name '$name' not found") }
    }

    fun createDesk(deskModelRequest: DeskModelRequest): String {
        val deskModel = DeskModel(name = deskModelRequest.name)
        deskRepository.save(deskModel)
        return "Create desk  ${deskModel.name}  successful"
    }
    fun deleteDeskById(id: String): String {
        try {
            deskRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw  EntryNotFoundException("Desk with id '$id' not found")
        }

        return "Delete desk with id: $id successful"
    }

    fun updateDesk(id: String, deskModelRequest: DeskModelRequest): String {
        val deskModel = getDeskByID(id)
        deskModel.name = deskModelRequest.name
        deskRepository.save(deskModel)
        return "Update desk with id: $id successful"
    }

    fun assignDeskToLocation(deskId: String, locationId: String): String {
        val desk: DeskModel = getDeskByID(deskId)
        val location: LocationModel = locationService.getLocationById(locationId)

        desk.deskLocation = location
        deskRepository.save(desk)
        return "Assigning desk with id $deskId to location with id $locationId is successful"
    }

    fun deleteAll() {
        deskRepository.deleteAll()
    }

    fun save(deskModel: DeskModel){
        deskRepository.save(deskModel)
    }

}