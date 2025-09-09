package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {
    @Query("SELECT FROM Board b WHERE b.id = :id AND b.isDeleted = false")
    fun findActiveBoardById(boardId: Long): Board?
}