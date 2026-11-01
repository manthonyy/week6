package com.example.week6

enum class ProjectStatus {
    IN_PROGRESS,
    COMPLETED
}

data class Project(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS
)
