package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.MediaFile

interface MediaRepository {
    fun save(mediaFile: MediaFile): MediaFile
}