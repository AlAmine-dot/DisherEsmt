package com.example.esmt.cours.disher.core.common.util.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}