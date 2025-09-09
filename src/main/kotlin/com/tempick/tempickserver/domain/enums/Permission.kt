package com.tempick.tempickserver.domain.enums

enum class Permission(string: String) {
    ADMIN_WRITE("어드민 글쓰기 권한"), ADMIN_READ("어드민 읽기 권한"), USER_WRITE("사용자 글쓰기 권한"), USER_READ("사용자 읽기 권한")
}