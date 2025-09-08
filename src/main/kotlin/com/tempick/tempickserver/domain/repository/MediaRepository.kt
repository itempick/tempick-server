package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.MediaFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaRepository : JpaRepository<MediaFile, Long>