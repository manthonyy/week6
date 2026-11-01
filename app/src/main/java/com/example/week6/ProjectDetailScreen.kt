
package com.example.week6

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DetailBlue = Color(0xFF3B82F6)
private val DetailNavy = Color(0xFF172554)
private val DetailBackground = Color(0xFFF4F6FB)
private val DetailGray = Color(0xFF64748B)
private val DetailGreen = Color(0xFF16A34A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    viewModel: CampusViewModel,
    projectId: String,
    onBack: () -> Unit
) {
    val projects by viewModel.projects.collectAsState()
    val communities by viewModel.communities.collectAsState()

    val project = projects.find { it.id == projectId }
    val community = communities.find { it.id == project?.communityId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Project Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = DetailBackground
    ) { innerPadding ->

        if (project == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Project not found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DetailNavy
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "CAMPUS TECH HUB / PROJECT",
                            color = Color(0xFF93C5FD),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = project.title,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 34.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = community?.name ?: "Unknown Community",
                            color = Color(0xFFCBD5E1),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        Surface(
                            color = if (
                                project.status == ProjectStatus.COMPLETED
                            ) {
                                Color(0xFF166534)
                            } else {
                                Color(0xFF1D4ED8)
                            },
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text(
                                text = if (
                                    project.status == ProjectStatus.COMPLETED
                                ) {
                                    "✓  Completed"
                                } else {
                                    "●  In Progress"
                                },
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                )
                            )
                        }
                    }
                }
                DetailSection(title = "About this Project") {
                    Text(
                        text = project.description,
                        color = DetailGray,
                        fontSize = 14.sp,
                        lineHeight = 23.sp
                    )
                }
                DetailSection(title = "Technology Stack") {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(project.techStack) { technology ->
                            Surface(
                                color = Color(0xFFEFF6FF),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = technology,
                                    color = DetailBlue,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(
                                        horizontal = 13.dp,
                                        vertical = 9.dp
                                    )
                                )
                            }
                        }
                    }
                }
                DetailSection(title = "Project Progress") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (
                                    project.status == ProjectStatus.COMPLETED
                                ) {
                                    "Project Completed"
                                } else {
                                    "Currently In Progress"
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (
                                    project.status == ProjectStatus.COMPLETED
                                ) {
                                    DetailGreen
                                } else {
                                    DetailBlue
                                }
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "Update the status when your project is finished.",
                                fontSize = 12.sp,
                                color = DetailGray,
                                lineHeight = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = project.status == ProjectStatus.COMPLETED,
                            onCheckedChange = {
                                viewModel.updateProjectStatus(project.id)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = DetailNavy
            )

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}
