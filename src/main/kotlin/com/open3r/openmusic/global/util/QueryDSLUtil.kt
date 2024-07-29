package com.open3r.openmusic.global.util

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

inline fun <reified T> JPAQuery<T>.orderBy(sort: Sort): JPAQuery<T> {
    sort.forEach {
        val path = PathBuilder(T::class.java, "songEntity")

        orderBy(
            OrderSpecifier(
                if (it.isAscending) Order.ASC else Order.DESC,
                path[it.property] as Expression<Comparable<*>>
            )
        )
    }

    return this
}

fun <T> JPAQuery<T>.paginate(pageable: Pageable): JPAQuery<T> = apply {
    offset(pageable.offset)
    limit(pageable.pageSize.toLong())
}