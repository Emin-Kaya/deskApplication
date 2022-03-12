package com.example.deskApplication.desk

import com.example.deskApplication.model.*
import com.example.deskApplication.service.DeskService
import com.example.deskApplication.service.LocationService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*
import java.util.*


@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@TestPropertySource(
    properties = [
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false"
    ]
)
class DeskControllerIt(
    @Autowired private var mockMvc: MockMvc,
    @Autowired private var deskService: DeskService,
    @Autowired private var locationService: LocationService,
) {
    lateinit var deskModel: DeskModel
    lateinit var deskModelRequest: DeskModelRequest

    @BeforeEach
    fun setUp() {
        val uuid : String =  UUID.randomUUID().toString()
        deskModel = DeskModel(uuid, "TestDesk123")
        deskModelRequest = DeskModelRequest("TestDesk123")
        deskService.save(deskModel)
    }

    @AfterEach
    fun deleteUp(){
        deskService.deleteAll()
    }


    @Test
    fun `should return all desks`() {
        mockMvc.get("/api/v1/desk")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return desk by id`() {

        mockMvc.get("/api/v1/desk/${deskModel.deskModelId}")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return desk by name`() {
        mockMvc.get("/api/v1/desk/name/${deskModel.name}")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should create desk`() {
        mockMvc.post("/api/v1/desk") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(deskModelRequest)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { "Create desk  ${deskModel.name}  successful" }
        }
    }

    @Test
    fun `should delete desk by id`() {
        mockMvc.delete("/api/v1/desk/${deskModel.deskModelId}")
            .andExpect {
                status { isOk() }
                content { "Delete desk  ${deskModel.name}  successful" }
            }
    }

    @Test
    fun `should update desk by id`() {
        mockMvc.put("/api/v1/desk/${deskModel.deskModelId}") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(deskModelRequest)
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content { "Update desk with id: ${deskModel.deskModelId} successful" }
            }
    }

    @Test
    fun `should add desk to location`() {
        val uuid : String =  UUID.randomUUID().toString()
        val locationModel = LocationModel(uuid, "TestRoom123", LocationModelEnum.ROOM)
        locationService.save(locationModel)


        mockMvc.put("/api/v1/desk/${deskModel.deskModelId}/${locationModel.locationModelId}")
            .andExpect {
                status { isCreated() }
                content { "Assigning desk with id ${deskModel.deskModelId} to location with id ${locationModel.locationModelId} is successful" }
            }
    }
}