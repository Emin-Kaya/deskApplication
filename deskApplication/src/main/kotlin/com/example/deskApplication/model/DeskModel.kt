package com.example.deskApplication.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.*
import javax.persistence.*

@Entity(name = "desk")
@Table(name = "desk")
class DeskModel(

    @Id
    @Column
    var deskModelId: String? = UUID.randomUUID().toString(),

    @Column
    var name: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "deskLocation_id")
    @JsonManagedReference
    var deskLocation: LocationModel? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeskModel

        if (deskModelId != other.deskModelId) return false
        if (name != other.name) return false
        if (deskLocation != other.deskLocation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deskModelId?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (deskLocation?.hashCode() ?: 0)
        return result
    }
}


data class DeskModelRequest(
    val name: String
)


