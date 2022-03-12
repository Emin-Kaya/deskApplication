package com.example.deskApplication.controller

import com.example.deskApplication.model.DeskModel
import com.example.deskApplication.model.DeskModelRequest
import com.example.deskApplication.service.DeskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/desk")
class DeskController(val deskService: DeskService) {

    @GetMapping
    fun getAllDesks(): ResponseEntity<List<DeskModel>> =
        ResponseEntity(deskService.findAllDesks(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getDesk(@PathVariable id: String): ResponseEntity<DeskModel> =
        ResponseEntity(deskService.getDeskByID(id), HttpStatus.OK)

    @GetMapping("/name/{name}")
    fun getDeskByName(@PathVariable name: String): ResponseEntity<DeskModel> =
        ResponseEntity(deskService.getDeskByName(name), HttpStatus.OK)

    @PostMapping
    fun createDesk(@RequestBody deskModelRequest: DeskModelRequest): ResponseEntity<String> =
        ResponseEntity(deskService.createDesk(deskModelRequest), HttpStatus.CREATED)

    @DeleteMapping("/{id}")
    fun deleteDesk(@PathVariable id: String): ResponseEntity<String> =
        ResponseEntity(deskService.deleteDeskById(id), HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateDesk(@PathVariable id: String, @RequestBody deskModelRequest: DeskModelRequest): ResponseEntity<String> =
        ResponseEntity(deskService.updateDesk(id, deskModelRequest), HttpStatus.OK)

    @PutMapping("/{deskId}/{locationId}")
    fun assignDeskToLocation(@PathVariable deskId: String, @PathVariable locationId: String): ResponseEntity<String> =
        ResponseEntity(deskService.assignDeskToLocation(deskId, locationId), HttpStatus.CREATED)
}

