package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Banner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BannerRepository: JpaRepository<Banner, Long>{
    @Query("SELECT COUNT(b) > 0 FROM Banner b WHERE b.displaySequence = :displaySequence AND b.id != :id AND b.isDeleted = false")
    fun existsBannerByDisplaySequenceAndIdNot(displaySequence: Int, id: Long): Boolean

    @Query("SELECT COUNT(b) > 0 FROM Banner b WHERE b.displaySequence = :displaySequence AND b.isDeleted = false")
    fun existsBannerByDisplaySequence(displaySequence: Int): Boolean

    @Query("SELECT b FROM Banner b WHERE b.id = :id AND b.isDeleted = false")
    fun findActiveBanner(id: Long): Banner?
}
