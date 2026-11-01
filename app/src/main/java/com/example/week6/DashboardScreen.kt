
package com.example.week6

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

private val Navy = Color(0xFF172554)
private val Blue = Color(0xFF3B82F6)
private val LightBlue = Color(0xFFEFF6FF)
private val DarkText = Color(0xFF1E293B)
private val GrayText = Color(0xFF64748B)

@Composable
fun DashboardScreen(
    viewModel: CampusViewModel
) {

    val student by viewModel.student.collectAsState()
    val communities by viewModel.communities.collectAsState()
    val projects by viewModel.projects.collectAsState()

    val joinedCommunities = communities.filter { it.isJoined }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
    ) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Navy,
                    shape = RoundedCornerShape(
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp
                    )
                )
                .padding(24.dp)
        ) {

            Text(
                text = "CAMPUS TECH HUB",
                color = Color(0xFF93C5FD),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Hello, ${student.name.split(" ").first()}!",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Let's build something amazing today.",
                color = Color(0xFFCBD5E1),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF253B6E),
                        RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(
                            Color(0xFF60A5FA),
                            RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.avatar,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = student.name,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${student.major} • Class of ${student.batch}",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Statistics
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Your Overview",
                color = DarkText,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                StatisticCard(
                    title = "Joined Clubs",
                    count = joinedCommunities.size,
                    icon = "◎",
                    modifier = Modifier.weight(1f)
                )

                StatisticCard(
                    title = "Total Projects",
                    count = projects.size,
                    icon = "⌘",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Joined Communities
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Communities",
                    color = DarkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${joinedCommunities.size} joined",
                    color = Blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (joinedCommunities.isEmpty()) {

                EmptyState(
                    message = "Belum bergabung dengan komunitas."
                )

            } else {

                joinedCommunities.forEach { community ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = community.name,
                                color = DarkText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = community.category,
                                color = Blue,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = community.description,
                                color = GrayText,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // My Projects
            Text(
                text = "My Projects",
                color = DarkText,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (projects.isEmpty()) {

                EmptyState(
                    message = "Belum ada proyek yang dicatat."
                )

            } else {

                projects.forEach { project ->

                    val community = communities.find {
                        it.id == project.communityId
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = project.title,
                                color = DarkText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = community?.name ?: "Unknown Community",
                                color = Blue,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = project.description,
                                color = GrayText,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = if (
                                    project.status == ProjectStatus.COMPLETED
                                ) "✓ Completed" else "◷ In Progress",
                                color = if (
                                    project.status == ProjectStatus.COMPLETED
                                ) Color(0xFF16A34A) else Color(0xFFD97706),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


// Statistic Card

@Composable
fun StatisticCard(
    title: String,
    count: Int,
    icon: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
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

            Text(
                text = icon,
                color = Blue,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = count.toString(),
                color = DarkText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                color = GrayText,
                fontSize = 12.sp
            )
        }
    }
}


// Empty State

@Composable
fun EmptyState(
    message: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBlue
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = message,
                color = GrayText,
                fontSize = 13.sp
            )
        }
    }
}
