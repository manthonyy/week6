
package com.example.week6

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubProjectsScreen(
    viewModel: CampusViewModel,
    communityId: String,
    onBack: () -> Unit,
    onOpenProject: (String) -> Unit
) {

    val communities by viewModel.communities.collectAsState()
    val allProjects by viewModel.projects.collectAsState()

    val community = communities.find {
        it.id == communityId
    }

    val communityProjects = allProjects.filter {
        it.communityId == communityId
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = community?.name ?: "Community Projects",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(
                            text = "←",
                            fontSize = 25.sp,
                            color = Color(0xFF172554)
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White
            ) {
                Text(
                    text = "+",
                    fontSize = 28.sp
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            Text(
                text = "Your Projects",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Manage your projects and track your progress.",
                fontSize = 13.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (communityProjects.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "No Projects Yet",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Start building something amazing!",
                            color = Color(0xFF64748B),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tap + to add your first project.",
                            color = Color(0xFF2563EB),
                            fontSize = 13.sp
                        )
                    }
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp)
                ) {

                    items(
                        items = communityProjects,
                        key = { it.id }
                    ) { project ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOpenProject(project.id)
                                },
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        Text(
                                            text = project.title,
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E293B)
                                        )

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Text(
                                            text = if (
                                                project.status == ProjectStatus.COMPLETED
                                            ) "✓ Completed" else "◷ In Progress",
                                            color = if (
                                                project.status == ProjectStatus.COMPLETED
                                            ) Color(0xFF16A34A)
                                            else Color(0xFFD97706),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteProject(project.id)
                                        }
                                    ) {
                                        Text(
                                            text = "−",
                                            color = Color(0xFFEF4444),
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = project.description,
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B),
                                    lineHeight = 19.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {

                                    project.techStack.take(3).forEach { tech ->

                                        Surface(
                                            color = Color(0xFFEFF6FF),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = tech,
                                                color = Color(0xFF2563EB),
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 5.dp
                                                )
                                            )
                                        }
                                    }

                                    if (project.techStack.size > 3) {
                                        Text(
                                            text = "+${project.techStack.size - 3}",
                                            color = Color(0xFF64748B),
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "View project details →",
                                    color = Color(0xFF2563EB),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {

        AddProjectDialog(
            onDismiss = {
                showDialog = false
            },
            onSave = { title, description, techStack ->

                viewModel.addProject(
                    communityId = communityId,
                    title = title,
                    description = description,
                    techStack = techStack
                )

                showDialog = false
            }
        )
    }
}

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, List<String>) -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val techOptions = listOf(
        "Kotlin",
        "Jetpack Compose",
        "Firebase",
        "Ktor",
        "Room",
        "Python",
        "Java",
        "MySQL"
    )

    val selectedTech = remember {
        mutableStateListOf<String>()
    }

    val isValid = title.isNotBlank() &&
            description.isNotBlank() &&
            selectedTech.isNotEmpty()

    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),

        title = {
            Text(
                text = "Add Project",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
        },

        text = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = "Project Title",
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    placeholder = {
                        Text("Enter project title")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    placeholder = {
                        Text("Describe your project...")
                    },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Tech Stack",
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Select one or more technologies",
                    color = Color(0xFF64748B),
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                techOptions.chunked(2).forEach { techRow ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        techRow.forEach { tech ->

                            FilterChip(
                                selected = selectedTech.contains(tech),
                                onClick = {
                                    if (selectedTech.contains(tech)) {
                                        selectedTech.remove(tech)
                                    } else {
                                        selectedTech.add(tech)
                                    }
                                },
                                label = {
                                    Text(
                                        text = tech,
                                        fontSize = 11.sp
                                    )
                                }
                            )
                        }
                    }
                }
            }
        },

        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        title.trim(),
                        description.trim(),
                        selectedTech.toList()
                    )
                },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB)
                )
            ) {
                Text("Save")
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel",
                    color = Color(0xFFEF4444)
                )
            }
        }
    )
}
