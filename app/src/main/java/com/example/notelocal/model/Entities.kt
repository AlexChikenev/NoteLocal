package com.example.diploma.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Directory")
data class Directory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

@Entity(tableName = "Project")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val directoryId: Int?,
    val name: String
)

@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val directoryId: Int?,
    val projectId: Int?,
    val name: String?,
    val content: String?
)

@Entity(tableName = "Wish")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

// ----------------------- Relationship -----------------------//
// Holding pairing between Directory and Project
data class DirectoryToProject(
    @Embedded val directory: Directory,
    @Relation(
        parentColumn = "id",
        entityColumn = "directoryId"
    )
    val projects: List<Project>
)

// Holding pairing between Directory and Notes
data class DirectoryToNote(
    @Embedded val directory: Directory,
    @Relation(
        parentColumn = "id",
        entityColumn = "directoryId"
    )
    val projects: List<Note>
)

// Holding pairing
data class ProjectToNote(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )
    val projects: List<Project>
)
