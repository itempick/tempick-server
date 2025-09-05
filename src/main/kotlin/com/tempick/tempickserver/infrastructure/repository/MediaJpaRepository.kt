package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.MediaFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaJpaRepository : JpaRepository<MediaFile, Long>