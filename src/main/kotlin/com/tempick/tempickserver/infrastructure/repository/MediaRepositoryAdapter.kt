package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.MediaFile
import com.tempick.tempickserver.domain.repository.MediaRepository
import org.springframework.stereotype.Repository


@Repository
class MediaRepositoryAdapter(
    private val jpa: MediaJpaRepository
) : MediaRepository {
    override fun save(mediaFile: MediaFile): MediaFile = jpa.save(mediaFile)
}
