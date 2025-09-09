package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long>