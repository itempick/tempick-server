package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    @Query("select c from Category c where c.isDeleted = false")
    fun findAllActiveCategories(): List<Category>

    @Query("select c from Category c where c.id = :id and c.isDeleted = false")
    fun findActiveCategory(Id: Long): Category?
}