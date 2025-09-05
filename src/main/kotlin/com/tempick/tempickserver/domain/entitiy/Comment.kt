package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class Comment(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @Column(nullable = false)
    var isDeleted: Boolean = false
): BaseDatetime()