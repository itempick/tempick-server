package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Tag (
    @Column(unique = true)
    var tagName: String,

    @Column(nullable = false)
    var tagColor: String,
)