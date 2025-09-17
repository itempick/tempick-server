package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class Post (
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Embedded
    var tag: Tag? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    var board: Board,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var author: User,

    @Column(nullable = false)
    var exposePriority: Int = 0,

    @Column(nullable = false)
    var viewCount: Int = 0,

    @OneToMany(mappedBy = "post")
    var comments: MutableList<Comment> = mutableListOf(),

    @Column(nullable = false)
    var isDeleted: Boolean = false,
): BaseDatetime() {
    fun updateByAdmin(
        exposePriority: Int? = null,
        title: String,
        content: String,
        tagName: String,
        tagColor: String,
    ) {
        if (exposePriority != this.exposePriority && exposePriority != null) {
            this.exposePriority = exposePriority
        }

        if (title != this.title) {
            this.title = title
        }

        if (content != this.content) {
            this.content = content
        }

        this.tag = Tag(tagName, tagColor)
    }
}