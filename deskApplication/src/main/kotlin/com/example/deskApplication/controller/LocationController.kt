package com.example.deskApplication.controller

import com.example.deskApplication.model.DeskModel
import com.example.deskApplication.model.LocationModel
import com.example.deskApplication.model.LocationModelEnum
import com.example.deskApplication.model.LocationModelRequest
import com.example.deskApplication.service.LocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/location")
class LocationController(val locationService: LocationService) {

    @GetMapping
    fun getAllLocations(): ResponseEntity<List<LocationModel>> =
        ResponseEntity(locationService.findAllLocations(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getLocationById(@PathVariable id: String): ResponseEntity<LocationModel> =
        ResponseEntity(locationService.getLocationById(id), HttpStatus.OK)

    @GetMapping("/filter")
    fun getLocationByType(@RequestParam(required = true) type: String): ResponseEntity<List<LocationModel>> =
        ResponseEntity(locationService.getLocationByType(LocationModelEnum.valueOf(type.uppercase())), HttpStatus.OK)

    @PostMapping
    fun createLocation(@RequestBody locationModelRequest: LocationModelRequest): ResponseEntity<String> =
        ResponseEntity(locationService.createLocation(locationModelRequest), HttpStatus.CREATED)

    @DeleteMapping("/{id}")
    fun deleteLocation(@PathVariable id: String): ResponseEntity<String> =
        ResponseEntity(locationService.deleteLocationById(id), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateLocation(
        @PathVariable id: String,
        @RequestBody locationModelRequest: LocationModelRequest
    ): ResponseEntity<String> =
        ResponseEntity(locationService.updateLocation(id, locationModelRequest), HttpStatus.OK)

    @GetMapping("/allDesk/{id}")
    fun getAllDesksOfLocation(@PathVariable id: String): ResponseEntity<List<DeskModel>> =
        ResponseEntity(locationService.getAllDeskFromLocation(id), HttpStatus.OK)

}