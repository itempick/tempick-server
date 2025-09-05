package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<User, Long>
