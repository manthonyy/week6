
package com.example.week6

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CampusViewModel : ViewModel() {

    private val _student = MutableStateFlow(
        StudentProfile(
            name = "Aditya Pratama",
            major = "Informatika",
            batch = "2024",
            avatar = "AP"
        )
    )

    val student = _student.asStateFlow()

    private val _communities = MutableStateFlow(
        listOf(
            Community(
                id = "1",
                name = "Google Developer Student Clubs",
                description = "Learn, build, and connect with other developers.",
                category = "Development",
                memberCount = 120
            ),
            Community(
                id = "2",
                name = "CyberSecurity Guild",
                description = "Explore cybersecurity, ethical hacking, and network security.",
                category = "Security",
                memberCount = 85
            ),
            Community(
                id = "3",
                name = "AI Society",
                description = "Discover artificial intelligence and machine learning.",
                category = "Artificial Intelligence",
                memberCount = 95
            ),
            Community(
                id = "4",
                name = "UI/UX Design Club",
                description = "Create beautiful and user-friendly digital experiences.",
                category = "Design",
                memberCount = 70
            ),
            Community(
                id = "5",
                name = "Web Development Club",
                description = "Learn frontend and backend web development.",
                category = "Web Development",
                memberCount = 110
            ),
            Community(
                id = "6",
                name = "Mobile Development Club",
                description = "Build Android and iOS mobile applications.",
                category = "Mobile Development",
                memberCount = 90
            )
        )
    )

    val communities = _communities.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(emptyList())

    val projects = _projects.asStateFlow()

    fun toggleCommunity(communityId: String) {
        _communities.update { currentCommunities ->
            currentCommunities.map { community ->
                if (community.id == communityId) {
                    community.copy(isJoined = !community.isJoined)
                } else {
                    community
                }
            }
        }
    }

    fun addProject(
        communityId: String,
        title: String,
        description: String,
        techStack: List<String>
    ) {
        val newProject = Project(
            id = UUID.randomUUID().toString(),
            communityId = communityId,
            title = title,
            description = description,
            techStack = techStack,
            status = ProjectStatus.IN_PROGRESS
        )

        _projects.update { currentProjects ->
            currentProjects + newProject
        }
    }

    fun deleteProject(projectId: String) {
        _projects.update { currentProjects ->
            currentProjects.filterNot { it.id == projectId }
        }
    }

    fun updateProjectStatus(projectId: String) {
        _projects.update { currentProjects ->
            currentProjects.map { project ->
                if (project.id == projectId) {
                    project.copy(
                        status = if (project.status == ProjectStatus.IN_PROGRESS) {
                            ProjectStatus.COMPLETED
                        } else {
                            ProjectStatus.IN_PROGRESS
                        }
                    )
                } else {
                    project
                }
            }
        }
    }

    fun getCommunity(communityId: String): Community? {
        return _communities.value.find { it.id == communityId }
    }

    fun getProject(projectId: String): Project? {
        return _projects.value.find { it.id == projectId }
    }

    fun getProjectsByCommunity(communityId: String): List<Project> {
        return _projects.value.filter {
            it.communityId == communityId
        }
    }

    fun getJoinedCommunitiesCount(): Int {
        return _communities.value.count { it.isJoined }
    }

    fun getTotalProjectsCount(): Int {
        return _projects.value.size
    }
}
