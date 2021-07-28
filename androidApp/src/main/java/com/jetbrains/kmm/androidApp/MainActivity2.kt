package com.jetbrains.kmm.androidApp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetbrains.kmm.androidApp.ui.theme.KmmSampleTheme
import kotlinx.coroutines.flow.MutableStateFlow

const val TAG: String = "MainActivity2Tag"

// we should never do that and that's only for testing different implementation
// because the state should be in the view model
val tasksGroups = MutableStateFlow(
    listOf(
        Pair(
            "group 1",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        Pair(
            "group 2",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        Pair(
            "group 3",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        )
    )
)

class MainActivity2 : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmSampleTheme {
                val groups by tasksGroups.collectAsState()
                TasksGroupsList(groups)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroupsList(tasksGroups: List<Pair<String, List<String>>>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        itemsIndexed(tasksGroups) { groupIndex, item ->
            Log.d(TAG, "TasksGroupsList: $groupIndex")
            TasksGroup(tasksGroup = item, groupIndex = groupIndex)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroup(
    modifier: Modifier = Modifier,
    tasksGroup: Pair<String, List<String>>,
    groupIndex: Int
) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                Toast
                    .makeText(context, tasksGroup.first, Toast.LENGTH_SHORT)
                    .show()
            },
        elevation = 8.dp
    ) {
        val dismissState = remember(tasksGroup, groupIndex) {
            DismissState(DismissValue.Default) {
                Toast
                    .makeText(context, tasksGroup.first, Toast.LENGTH_SHORT)
                    .show()
                false
            }
        }
        SwipeToDismiss(
            state = dismissState,
            background = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .clickable {
                                Toast
                                    .makeText(context, tasksGroup.first, Toast.LENGTH_SHORT)
                                    .show()
                            },
                        imageVector = Icons.Outlined.Done,
                        contentDescription = null,

                        )
                }
            },
            dismissContent = {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = tasksGroup.first,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 24.sp
                        )
                    }
                    Column {
                        tasksGroup.second.forEachIndexed { taskIndexInTasksGroup, item ->
                            Log.d(TAG, "TasksGroup: $groupIndex")
                            TaskCard(
                                name = item,
                                taskIndexInTasksGroup = taskIndexInTasksGroup,
                                groupIndex = groupIndex
                            )
                        }
                    }
                }
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskCard(
    name: String,
    modifier: Modifier = Modifier,
    taskIndexInTasksGroup: Int,
    groupIndex: Int
) {
    val context = LocalContext.current
    val dismissState = remember(name, taskIndexInTasksGroup, groupIndex) {
        DismissState(DismissValue.Default) {
            Toast
                .makeText(context, "$name, $taskIndexInTasksGroup", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }
    SwipeToDismiss(state = dismissState, background = {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .clickable {
                        Toast
                            .makeText(context, "$name, $taskIndexInTasksGroup", Toast.LENGTH_SHORT)
                            .show()
                    },
                imageVector = Icons.Outlined.Done,
                contentDescription = null,

                )
        }
    }) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    Toast
                        .makeText(context, name, Toast.LENGTH_SHORT)
                        .show()
                },
            elevation = 5.dp,
            backgroundColor = Color.Gray

        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }

        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TasksGroupsListPreview() {
    val groups by tasksGroups.collectAsState()
    TasksGroupsList(groups)
}