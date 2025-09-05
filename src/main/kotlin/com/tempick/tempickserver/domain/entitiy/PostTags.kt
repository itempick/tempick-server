package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class PostTags(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = LAZY)
    var tag: Tag,

    @ManyToOne(fetch = LAZY)
    var post: Post,
): BaseDatetime()