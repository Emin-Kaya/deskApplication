package com.example.deskApplication.location

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
class LocationControllerIt(
    @Autowired private var mockMvc: MockMvc,
    @Autowired private var deskService: DeskService,
    @Autowired private var locationService: LocationService,
) {
    lateinit var locationModel: LocationModel
    lateinit var locationModelRequest: LocationModelRequest

    @BeforeEach
    fun setUp() {
        val uuid : String =  UUID.randomUUID().toString()
        locationModel = LocationModel(uuid, "TestRoom123", LocationModelEnum.ROOM)
        locationModelRequest = LocationModelRequest("TestRoom123", LocationModelEnum.ROOM)
        locationService.save(locationModel)
    }

    @AfterEach
    fun deleteUp(){
        deskService.deleteAll()
    }

    @Test
    fun `should return all locations`() {
        mockMvc.get("/api/v1/location")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return desk by location`() {
        mockMvc.get("/api/v1/location/${locationModel.locationModelId}")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return desks by type`() {

        mockMvc.get("/api/v1/location/filter/"){param("type", locationModel.type.toString())
        }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should create location`() {
        mockMvc.post("/api/v1/location") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(locationModelRequest)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { "Create location  ${locationModel.name}  successful" }
        }
    }

    @Test
    fun `should delete location by id`() {
        mockMvc.delete("/api/v1/location/${locationModel.locationModelId}")
            .andExpect {
                status { isOk() }
                content { "Delete location  ${locationModel.name}  successful" }
            }
    }

    @Test
    fun `should update location by id`() {
        mockMvc.put("/api/v1/location/${locationModel.locationModelId}") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(locationModelRequest)
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content { "Update location with id: ${locationModel.locationModelId} successful" }
            }
    }

    @Test
    fun `should return all desks with location id`(){
        mockMvc.get("/api/v1/location/allDesk/${locationModel.locationModelId}")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

}


