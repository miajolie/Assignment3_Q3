package com.example.assignment3_q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//New Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactListScreen()
        }
    }
}

data class Contact(val name: String, val phone: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen() {
    val contacts = generateContacts()
    val groupedContacts = contacts.groupBy { it.name.first().uppercaseChar() }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Show FAB when scrolled past item 10
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 10 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            groupedContacts.forEach { (letter, contactsForLetter) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF6366F1))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = letter.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                items(contactsForLetter) { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar circle
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFE0E7FF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = contact.name.first().toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6366F1)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = contact.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = contact.phone,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }

        // Floating Action Button
        if (showFab) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                containerColor = Color(0xFF6366F1),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("↑", fontSize = 24.sp, color = Color.White)
            }
        }
    }
}

fun generateContacts(): List<Contact> {
    val firstNames = listOf(
        "Alice", "Amanda", "Andrew", "Anna", "Anthony",
        "Ben", "Betty", "Brian", "Brittany", "Bruce",
        "Carl", "Carol", "Catherine", "Charles", "Chris",
        "Daniel", "David", "Diana", "Donald", "Donna",
        "Edward", "Elizabeth", "Emily", "Emma", "Eric",
        "Frank", "Fred", "Fiona", "Faith", "Felix",
        "George", "Grace", "Gary", "Gina", "Greg",
        "Hannah", "Harry", "Helen", "Henry", "Holly",
        "Ian", "Irene", "Isaac", "Isabella", "Ivan",
        "Jack", "James", "Jane", "Jason", "Jennifer",
        "Karen", "Katherine", "Keith", "Kelly", "Kevin",
        "Laura", "Lauren", "Leo", "Linda", "Lisa",
        "Mark", "Mary", "Matthew", "Megan", "Michael",
        "Nancy", "Nathan", "Nicole", "Noah", "Nora",
        "Oliver", "Olivia", "Oscar", "Owen", "Ophelia",
        "Patrick", "Paul", "Peter", "Philip", "Patricia",
        "Quinn", "Quincy", "Quentin", "Queenie", "Quinton",
        "Rachel", "Rebecca", "Richard", "Robert", "Ryan",
        "Samuel", "Sandra", "Sarah", "Scott", "Sean",
        "Thomas", "Timothy", "Tina", "Todd", "Tracy",
        "Uma", "Ulysses", "Unity", "Ursula", "Upton",
        "Victor", "Victoria", "Vincent", "Violet", "Vivian",
        "Walter", "Wendy", "William", "Willow", "Wyatt",
        "Xavier", "Xander", "Xena", "Xiomara", "Xyla",
        "Yara", "Yasmin", "Yolanda", "Yvonne", "Yuri",
        "Zachary", "Zoe", "Zara", "Zelda", "Zion"
    )

    return firstNames.mapIndexed { index, name ->
        Contact(
            name = name,
            phone = "(555) ${(100..999).random()}-${(1000..9999).random()}"
        )
    }.sortedBy { it.name }
}