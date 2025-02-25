package com.example.documentbank.ToDoAppFirebase.todo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentbank.R
import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import com.example.documentbank.ToDoAppFirebase.todo.utils.Priority

@Composable
fun ToDoItem(
    toDoUI: ToDoUI,
    onItemClicked: () -> Unit
) {

    val containerColor = when (toDoUI.priority) {
        Priority.LOW -> {
            colorResource(R.color.green)
        }

        Priority.MEDIUM -> {
            colorResource(R.color.yellow)

        }

        Priority.HIGH -> {
            colorResource(R.color.red)

        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )

    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            //title
            Text(
                text = toDoUI.title!!,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (toDoUI.description.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                //description
                Text(
                    text = toDoUI.description,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            Spacer(Modifier.height(15.dp))
            //date
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoUI.dateAdded!!,
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.End,

                )
        }
    }

}

val toDoList = listOf(
    ToDoUI(
        id = "1",
        title = "Record video for API",
        description = "I have to do it, it's important. Do it ASAP.",
        priority = Priority.HIGH,
        dateAdded = "24 Feb, 11:10 AM, 2025"
    ),
    ToDoUI(
        id = "2",
        title = "Write blog on Jetpack Compose",
        description = "Write an article explaining state management in Jetpack Compose.",
        priority = Priority.MEDIUM,
        dateAdded = "23 Feb, 4:30 PM, 2025"
    ),
    ToDoUI(
        id = "3",
        title = "Grocery Shopping",
        description = "Buy vegetables, milk, and snacks for the week.",
        priority = Priority.LOW,
        dateAdded = "22 Feb, 6:00 PM, 2025"
    ),
    ToDoUI(
        id = "4",
        title = "Fix authentication bug",
        description = "Users are facing login issues. Debug and fix it.",
        priority = Priority.HIGH,
        dateAdded = "21 Feb, 10:00 AM, 2025"
    ),
    ToDoUI(
        id = "5",
        title = "Meeting with product team",
        description = "Discuss upcoming features and project roadmap.",
        priority = Priority.MEDIUM,
        dateAdded = "20 Feb, 2:00 PM, 2025"
    ),
    ToDoUI(
        id = "6",
        title = "Workout session",
        description = "Complete 45 minutes of strength training.",
        priority = Priority.LOW,
        dateAdded = "19 Feb, 7:00 AM, 2025"
    ),
    ToDoUI(
        id = "7",
        title = "Prepare presentation for client",
        description = "Finalize slides and rehearse for tomorrow's meeting.",
        priority = Priority.HIGH,
        dateAdded = "18 Feb, 5:00 PM, 2025"
    ),
    ToDoUI(
        id = "8",
        title = "Update resume",
        description = "Add recent projects and skills to LinkedIn and resume.",
        priority = Priority.MEDIUM,
        dateAdded = "17 Feb, 8:00 PM, 2025"
    ),
    ToDoUI(
        id = "9",
        title = "Plan weekend trip",
        description = "Decide location, book hotel, and prepare itinerary.",
        priority = Priority.LOW,
        dateAdded = "16 Feb, 12:00 PM, 2025"
    ),
    ToDoUI(
        id = "10",
        title = "Doctor’s appointment",
        description = "Routine health check-up at 9:30 AM.",
        priority = Priority.HIGH,
        dateAdded = "15 Feb, 9:00 AM, 2025"
    ),
    ToDoUI(
        id = "11",
        title = "Reply to pending emails",
        description = "Check and respond to important work emails.",
        priority = Priority.MEDIUM,
        dateAdded = "14 Feb, 3:30 PM, 2025"
    ),
    ToDoUI(
        id = "12",
        title = "Car service",
        description = "Take the car to the service center for maintenance.",
        priority = Priority.LOW,
        dateAdded = "13 Feb, 10:00 AM, 2025"
    ),
    ToDoUI(
        id = "13",
        title = "Code review for PR #248",
        description = "Review pull request and provide feedback.",
        priority = Priority.HIGH,
        dateAdded = "12 Feb, 6:00 PM, 2025"
    ),
    ToDoUI(
        id = "14",
        title = "Read a book",
        description = "Complete 2 chapters of 'Atomic Habits'.",
        priority = Priority.LOW,
        dateAdded = "11 Feb, 9:00 PM, 2025"
    ),
    ToDoUI(
        id = "15",
        title = "Team stand-up meeting",
        description = "Discuss daily progress and blockers.",
        priority = Priority.MEDIUM,
        dateAdded = "10 Feb, 10:30 AM, 2025"
    )
)