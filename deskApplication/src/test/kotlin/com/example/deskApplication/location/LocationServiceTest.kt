package com.example.deskApplication.location

import com.example.deskApplication.model.DeskModel
import com.example.deskApplication.model.LocationModel
import com.example.deskApplication.model.LocationModelEnum
import com.example.deskApplication.model.LocationModelRequest
import com.example.deskApplication.repository.LocationRepository
import com.example.deskApplication.service.LocationService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.collections.List

class LocationServiceTest {

    val locationRepository: LocationRepository = mock()

    @Autowired
    private val locationService: LocationService = LocationService(locationRepository)


    @Test
    fun `find all locations`() {
        val expected = mutableListOf<LocationModel>()
        whenever(locationRepository.findAll()).thenReturn(expected)
        val actual = locationService.findAllLocations()
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get location by id`() {
        val expected: Optional<LocationModel> = Optional.of(LocationModel("1L", "Test123", LocationModelEnum.ROOM))
        whenever(locationRepository.findById("1L")).thenReturn(expected)
        val actual: Optional<LocationModel>? =
            expected.get().locationModelId?.let { locationService.getLocationById(it) }?.let { Optional.of(it) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `get location by type`() {
        val locationModel: LocationModel = LocationModel("1L", "Test123", LocationModelEnum.ROOM)
        val expected : List<LocationModel> = listOf(locationModel)


        whenever(locationRepository.findLocationModelByType(LocationModelEnum.ROOM)).thenReturn(expected)
        val actual: List<LocationModel>? = locationModel.type?.let { locationService.getLocationByType(it) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `create location`(){
        val uuid : String =  UUID.randomUUID().toString()
        val locationModel = LocationModel(uuid, "TestRoom123", LocationModelEnum.ROOM)
        val locationModelRequest = LocationModelRequest("TestRoom123", LocationModelEnum.ROOM)
        locationService.save(locationModel)

        doReturn(locationModel).whenever(locationRepository).save(any())
        locationService.createLocation(locationModelRequest)


        verify(locationRepository).save(locationModel)

    }

    @Test
    fun `delete location by id`(){
        locationService.deleteLocationById("1L")
        verify(locationRepository, times(1)).deleteById("1L")
    }

    @Test
    fun `update location by id`(){
        val locationModelRequest = LocationModelRequest("Test123", LocationModelEnum.ROOM)
        val expected = LocationModel(name = locationModelRequest.name, type = locationModelRequest.type)

        whenever(locationRepository.findById("1L")).thenReturn(Optional.of(expected))
        doReturn(expected).whenever(locationRepository).save(any())

        locationService.updateLocation("1L", locationModelRequest)

        verify(locationRepository).findById("1L")
        verify(locationRepository).save(expected)
    }

    @Test
    fun `get all desks from location`(){

        val locationModel: Optional<LocationModel> = Optional.of(LocationModel("1L", "Test123", LocationModelEnum.ROOM))
        val expected = locationModel.get().desks


        whenever(locationRepository.findById("1L")).thenReturn(locationModel)
        val actual: List<DeskModel>? = locationModel.get().locationModelId?.let { locationService.getAllDeskFromLocation(it) }

        Assertions.assertEquals(expected, actual)
    }


}