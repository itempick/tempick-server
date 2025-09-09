package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Post (
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @Embedded
    var tag: Tag? = null,

    @OneToMany(mappedBy = "post")
    var comments: MutableSet<Comment> = mutableSetOf(),

    @Column(nullable = false)
    var isDeleted: Boolean = false,
): BaseDatetime()