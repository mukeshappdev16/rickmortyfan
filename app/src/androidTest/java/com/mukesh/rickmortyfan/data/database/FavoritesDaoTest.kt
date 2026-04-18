package com.mukesh.rickmortyfan.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mukesh.rickmortyfan.data.database.entity.CharacterEntity
import com.mukesh.rickmortyfan.data.database.entity.FavoritesDao
import com.mukesh.rickmortyfan.domain.modal.character.Location
import com.mukesh.rickmortyfan.domain.modal.character.Origin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class FavoritesDaoTest {

    private lateinit var database: FavoritesDatabase
    private lateinit var dao: FavoritesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoritesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoritesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addAndGetFavoriteCharacter() = runTest {
        val character = CharacterEntity(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "",
            episode = emptyList(),
            url = "",
            created = ""
        )
        dao.addFavoriteCharacter(character)

        val favorites = dao.getFavoriteCharacters().first()
        assertEquals(1, favorites.size)
        assertEquals("Rick", favorites[0].name)
    }

    @Test
    fun removeFavoriteCharacter() = runTest {
        val character = CharacterEntity(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "",
            episode = emptyList(),
            url = "",
            created = ""
        )
        dao.addFavoriteCharacter(character)
        dao.removeFavoriteCharacter(character)

        val favorites = dao.getFavoriteCharacters().first()
        assertTrue(favorites.isEmpty())
    }

    @Test
    fun isFavoriteCharacterPresent() = runTest {
        val character = CharacterEntity(
            id = 1,
            name = "Rick",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "",
            episode = emptyList(),
            url = "",
            created = ""
        )
        dao.addFavoriteCharacter(character)

        assertTrue(dao.isFavoriteCharacterPresent(1))
        assertFalse(dao.isFavoriteCharacterPresent(2))
    }
}
