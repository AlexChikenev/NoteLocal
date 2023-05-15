package com.example.diploma.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface Dao {
    // ------------------ Selections methods ------------------------- //

    // Method for selection all data from Directory
    @Query("SELECT * FROM Directory")
    suspend fun getAllDirectory(): List<Directory>

    // Method for name selection from Directory by Id
    @Query ("SELECT name FROM Directory WHERE id = :directoryId")
    suspend fun getDirectoryName(directoryId: Int?) : String

    // Method for name selection from Project by Id
    @Query ("SELECT name FROM Project WHERE id = :projectId")
    suspend fun getProjectName(projectId: Int?) : String

    // Method for selection data from Projects where directoryId = id in Directory
    @Query ("SELECT * FROM Project WHERE directoryId = :directoryId")
    suspend fun getProjectsByDirectoryId(directoryId: Int?) : List<Project>

    // Method for selection data from Note where directoryId = id in Directory
    @Transaction
    @Query ("SELECT * FROM Note WHERE directoryId = :directoryId")
    suspend fun getAllNotesByDirectoryId(directoryId: Int?) : List<Note>


    // Method for selection data from Note where projectId = id in Project
    @Transaction
    @Query ("SELECT * FROM Note WHERE projectId = :projectId")
    suspend fun getAllNotesByProjectId(projectId: Int?) : List<Note>

    // Method for selection all data from Wish
    @Query("SELECT * FROM Wish")
    suspend fun getAllWishes() : List<Wish>
    // ------------------ Inserting methods ------------------------- //

    // Method for inserting data into Directory
    @Insert
    suspend fun insertIntoDirectory(directory: Directory)

    // Method for inserting data into Wish
    @Insert
    suspend fun insertIntoWish(wish: Wish)

    // Method for
    @Transaction
    @Query("SELECT * FROM Directory")
    fun getDirectoryToProject(): List<DirectoryToProject>

    // Method for insertion data into Project
    @Insert
    suspend fun insertIntoProject(project: Project)

    // Method for insertion data into Note
    @Insert
    suspend fun insertIntoNote(note: Note)

    // ------------------ Dangerous methods ------------------------- //
    @Query("DELETE FROM Directory")
    suspend fun deleteAllFromDirectory()


    @Query("DELETE FROM Project")
    suspend fun deleteAllFromDProject()

    @Query("DELETE FROM Note")
    suspend fun deleteAllFromNote()

    @Query("DELETE FROM sqlite_sequence WHERE name='Project'")
    suspend fun resetInProject()

    @Query("DELETE FROM sqlite_sequence WHERE name='Directory'")
    suspend fun resetInDirectory()

    @Query("DELETE FROM sqlite_sequence WHERE name='Note'")
    suspend fun resetInNote()
}
