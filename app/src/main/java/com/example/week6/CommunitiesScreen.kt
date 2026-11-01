
package com.example.week6

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CommunitiesScreen(
    viewModel: CampusViewModel,
    onOpenCommunity: (String) -> Unit
) {

    val communities by viewModel.communities.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF172554),
                    RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                )
                .padding(24.dp)
        ) {

            Text(
                text = "EXPLORE",
                color = Color(0xFF93C5FD),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Communities",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Find your people. Build your future.",
                color = Color(0xFFCBD5E1),
                fontSize = 13.sp
            )
        }

        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Discover Clubs",
                    color = Color(0xFF1E293B),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${communities.size} communities",
                    color = Color(0xFF3B82F6),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {

                items(
                    items = communities,
                    key = { it.id }
                ) { community ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                if (community.isJoined) {

                                    onOpenCommunity(community.id)

                                } else {

                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Harap bergabung ke komunitas terlebih dahulu!"
                                        )
                                    }
                                }
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

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            Color(0xFFEFF6FF),
                                            RoundedCornerShape(14.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = community.name.first().toString(),
                                        color = Color(0xFF2563EB),
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = community.name,
                                        color = Color(0xFF1E293B),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = community.category,
                                        color = Color(0xFF3B82F6),
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = community.description,
                                color = Color(0xFF64748B),
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "👥 ${community.memberCount} members",
                                    color = Color(0xFF64748B),
                                    fontSize = 12.sp
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = {
                                        viewModel.toggleCommunity(community.id)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (community.isJoined) {
                                            Color(0xFFEF4444)
                                        } else {
                                            Color(0xFF2563EB)
                                        }
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(
                                        horizontal = 18.dp,
                                        vertical = 8.dp
                                    )
                                ) {

                                    Text(
                                        text = if (community.isJoined) {
                                            "Joined"
                                        } else {
                                            "Join"
                                        },
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            if (community.isJoined) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Tap card to view projects →",
                                    color = Color(0xFF16A34A),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}
