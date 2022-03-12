package com.example.deskApplication.desk

import com.example.deskApplication.model.*
import com.example.deskApplication.repository.DeskRepository
import com.example.deskApplication.repository.LocationRepository
import com.example.deskApplication.service.DeskService
import com.example.deskApplication.service.LocationService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


class DeskServiceTest {
    val deskRepository: DeskRepository = mock()
    val locationRepository: LocationRepository = mock()
    val locationService: LocationService = mock()
    @Autowired
    private val deskService: DeskService = DeskService(deskRepository, locationService)

    @Test
    fun `find all desks`() {
        val expected = mutableListOf<DeskModel>()
        whenever(deskRepository.findAll()).thenReturn(expected)
        val actual = deskService.findAllDesks()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get desk by id`() {
        val expected: Optional<DeskModel> = Optional.of(DeskModel("1L", "Test123"))

        whenever(deskRepository.findById("1L")).thenReturn(expected)
        val actual: Optional<DeskModel>? = expected.get().deskModelId?.let { deskService.getDeskByID(it) }
            ?.let { Optional.of(it) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get desk by name`() {
        val expected: Optional<DeskModel> = Optional.of(DeskModel("1L", "Test123"))

        whenever(deskRepository.findDeskByName("Test123")).thenReturn(expected)
        val actual: Optional<DeskModel>? = expected.get().name?.let { deskService.getDeskByName(it) }
            ?.let { Optional.of(it) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `create desk`() {
        val uuid : String =  UUID.randomUUID().toString()
        val deskModel = DeskModel(uuid, "TestDesk123")
        val deskModelRequest = DeskModelRequest("TestDesk123")
        deskService.save(deskModel)

        doReturn(deskModel).whenever(deskRepository).save(any())
        deskService.createDesk(deskModelRequest)

        verify(deskRepository).save(deskModel)
    }


    @Test
    fun `delete desk by id`() {
        deskService.deleteDeskById("1L")
        verify(deskRepository, times(1)).deleteById("1L")
    }

    @Test
    fun `update desk by id`() {
        val deskModelRequest = DeskModelRequest(name = "Test123")
        val expected = DeskModel(name = deskModelRequest.name)

        whenever(deskRepository.findById("1L")).thenReturn(Optional.of(expected))
        doReturn(expected).whenever(deskRepository).save(any())

        deskService.updateDesk("1L", deskModelRequest)

        verify(deskRepository).findById("1L")
        verify(deskRepository).save(expected)
    }

    @Test
    fun `asign desk to location`(){
        val deskModelRequest = DeskModelRequest(name = "Test123")
        val deskModel = DeskModel(name = deskModelRequest.name)

        val locationModelRequest = LocationModelRequest(name = "Location123", type = LocationModelEnum.ROOM)
        val locationModel = LocationModel(name = locationModelRequest.name, type = locationModelRequest.type)

        whenever(deskRepository.findById("1L")).thenReturn(Optional.of(deskModel))
        whenever(locationRepository.findById("1L")).thenReturn(Optional.of(locationModel))
        doReturn(deskModel).whenever(deskRepository).save(any())

        deskService.assignDeskToLocation("1L", "1L")

        verify(deskRepository, times(1)).findById("1L")
        verify(locationService, times(1)).getLocationById("1L")
        verify(deskRepository, times(1)).save(deskModel)
    }
}