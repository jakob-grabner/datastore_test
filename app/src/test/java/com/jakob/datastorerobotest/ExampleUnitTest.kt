package com.jakob.datastorerobotest

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class ExampleDataSToreRobolectricTest {

    val context: Context by lazy { ApplicationProvider.getApplicationContext<Application>() }

    @Test
    fun renameTo_OverwritesExistingFile() {
        val myfolder = File(context.filesDir, "testRename")
        myfolder.mkdirs()

        val firstFile = File(myfolder, "first.txt").apply {
            createNewFile()
            writeText("first")
        }


        val secondFile = File(myfolder, "second.txt").apply {
            createNewFile()
            writeText("second")
        }

        val renameResult = firstFile.renameTo(File(myfolder, "second.txt"))


        val files = myfolder.listFiles()?.map {
            it.name
        }.orEmpty()

        assertThat(renameResult).isTrue()
        assertThat(files).containsExactly("second.txt")

    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @ExperimentalCoroutinesApi
    @Test
    fun dataStore_writeTwice_Test() : Unit = runBlocking{
        val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
        context.dataStore.edit { settings ->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
        }

        context.dataStore.edit { settings ->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }
}