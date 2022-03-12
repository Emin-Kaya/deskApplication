package com.example.deskApplication.repository

import com.example.deskApplication.model.DeskModel
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeskRepository : CrudRepository<DeskModel, String> {
    //@Query("select * from desk where name = :name ", nativeQuery = true)
    fun findDeskByName(@Param("name") name: String): Optional<DeskModel>

}
