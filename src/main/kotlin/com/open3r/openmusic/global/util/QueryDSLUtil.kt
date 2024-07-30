package com.open3r.openmusic.global.util

import com.open3r.openmusic.domain.song.domain.entity.QSongEntity.songEntity
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

inline fun <reified T> JPAQuery<T>.orderBy(sort: Sort): JPAQuery<T> {
    orderBy(*sort.map {
        val property = it.property
        val path = Expressions.path(Comparable::class.java, songEntity, property)

        when (it.direction) {
            Sort.Direction.ASC -> OrderSpecifier(Order.ASC, path)
            Sort.Direction.DESC -> OrderSpecifier(Order.DESC, path)
        }
    }.toList().toTypedArray())

    return this
}

fun <T> JPAQuery<T>.paginate(pageable: Pageable): JPAQuery<T> {
    offset(pageable.offset)
    limit(pageable.pageSize.toLong())

    return this
}