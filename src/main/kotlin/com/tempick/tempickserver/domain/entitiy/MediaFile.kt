package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class MediaFile(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    val fileUrl: String,
    val name: String,
) {
    companion object {
        fun create(fileUrl: String, name: String): MediaFile {
            return MediaFile(
                fileUrl = fileUrl,
                name = name
            )
        }
    }
}