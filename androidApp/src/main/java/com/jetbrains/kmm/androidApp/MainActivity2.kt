package com.jetbrains.kmm.androidApp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
fun TasksGroupWithoutCard(
    modifier: Modifier = Modifier,
    tasksGroup: Pair<String, List<String>>,
    groupIndex: Int
) {
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
                TaskCardWithDismissible(
                    name = item,
                    taskIndexInTasksGroup = taskIndexInTasksGroup,
                    groupIndex = groupIndex
                )
            }
        }
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
            elevation = 1.dp,
            backgroundColor = Color.LightGray

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
@Composable
fun TaskCardWithDismissible(
    name: String,
    modifier: Modifier = Modifier,
    taskIndexInTasksGroup: Int,
    groupIndex: Int
) {
    val context = LocalContext.current
    var unread by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            Toast
                .makeText(context, "Item Swiped", Toast.LENGTH_SHORT)
                .show()
            if (it == DismissValue.DismissedToEnd) unread = !unread
            it != DismissValue.DismissedToEnd
        }
    )
    DismissibleComposableContainer(dismissState)
    {
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
            elevation = 1.dp,
            backgroundColor = Color.LightGray

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
@Composable
private fun TasksGroupsListBothSidesSwiping(groups: List<Pair<String, MutableList<String>>>) {
    val context = LocalContext.current
    // This is an example of a list of dismissible items, similar to what you would see in an
    // email app. Swiping left reveals a 'delete' icon and swiping right reveals a 'done' icon.
    // The background will start as grey, but once the dismiss threshold is reached, the colour
    // will animate to red if you're swiping left or green if you're swiping right. When you let
    // go, the item will animate out of the way if you're swiping left (like deleting an email) or
    // back to its default position if you're swiping right (like marking an email as read/unread).
    LazyColumn(modifier = Modifier.padding(bottom = 5.dp)) {
        itemsIndexed(groups) { groupIndex, tasksGroup ->
            var unread by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd) unread = !unread
                    it != DismissValue.DismissedToEnd
                }
            )
            DismissibleComposableContainer(dismissState) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            Toast
                                .makeText(context, tasksGroup.first, Toast.LENGTH_SHORT)
                                .show()
                        },
                    elevation = animateDpAsState(
                        if (dismissState.dismissDirection != null) 5.dp else 1.dp
                    ).value
                ) {
                    TasksGroupWithoutCard(tasksGroup = tasksGroup, groupIndex = groupIndex)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun DismissibleComposableContainer(
    dismissState: DismissState,
    dismissContent: @Composable() (RowScope.() -> Unit)
) {

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp),
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.25f)
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.LightGray
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.5f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 5.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = dismissContent
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TasksGroupsListPreview() {
    val groups by tasksGroups.collectAsState()
    TasksGroupsList(groups)
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ListPreview() {
    val groups by tasksGroups.collectAsState()
    TasksGroupsListBothSidesSwiping(groups)
}