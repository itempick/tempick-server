package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.id = :boardId AND b.isDeleted = false")
    fun findActiveBoardById(boardId: Long): Board?

    @Query("SELECT count(b) > 0 FROM Board b WHERE b.name = :boardName AND b.isDeleted = false")
    fun existsBoardByName(boardName: String): Boolean

    @Modifying
    @Query("update Board b set b.isDeleted = true where b.category.id = :categoryId and b.isDeleted = false")
    fun deleteAllBoardsByCategoryId(categoryId: Long): Int
}