package com.example.deskApplication.model

import com.fasterxml.jackson.annotation.JsonBackReference
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@NoArgsConstructor
@Entity(name = "locations")
@Table(name = "locations")
class LocationModel(
    @Id
    @Column
    var locationModelId: String? = UUID.randomUUID().toString(),

    @Column
    var name: String? = null,

    @Column
    @Enumerated(EnumType.STRING)
    var type: LocationModelEnum? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deskLocation", cascade = [CascadeType.ALL])
    @JsonBackReference
    var desks: MutableList<DeskModel> = ArrayList()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationModel

        if (locationModelId != other.locationModelId) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (desks != other.desks) return false

        return true
    }

    override fun hashCode(): Int {
        var result = locationModelId?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + desks.hashCode()
        return result
    }
}

data class LocationModelRequest(
    val name: String,
    val type: LocationModelEnum
)

enum class LocationModelEnum {
    ROOM, LAB
}