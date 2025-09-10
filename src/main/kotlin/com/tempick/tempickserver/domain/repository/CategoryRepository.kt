package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    @Query("" +
            "select distinct c from Category c " +
            "left join fetch c.boards b " +
            "where c.isDeleted = false " +
            "order by c.sequence" +
            "")
    fun findAllActiveCategories(): List<Category>

    @Query("select c from Category c where c.id = :id and c.isDeleted = false")
    fun findActiveCategory(id: Long): Category?

    @Query("select count(c) > 0 from Category c where c.sequence = :sequence and c.isDeleted = false")
    fun existsCategoryBySequence(sequence: Int): Boolean

    @Query("select count(c) > 0 from Category c where c.sequence = :sequence and c.id != :id and c.isDeleted = false")
    fun existsCategoryBySequenceAndIdNot(sequence: Int, id: Long): Boolean
}
