package com.mukesh.rickmortyfan.common

object Utils {
    fun getIdsFromUrlList(list: List<String>): List<String> {
        return list.map { it.substringAfterLast("/") }
    }
}