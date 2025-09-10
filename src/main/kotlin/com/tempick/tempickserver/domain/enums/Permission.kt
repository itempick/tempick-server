package com.tempick.tempickserver.domain.enums

enum class Permission(val ko: String) {
    ADMIN("어드민만 작성, 읽을 수 있음"),
    ALL("모든 유저가 읽고, 작성할 수 있음"),
    PRIVATE("어드민만 읽고 작성할 수 있음"),
}