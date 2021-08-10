package com.jetbrains.kmm.androidApp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetbrains.androidApp.R
import com.jetbrains.kmm.androidApp.ui.theme.KmmSampleTheme
import kotlinx.coroutines.flow.MutableStateFlow

const val TAG: String = "MainActivity2Tag"

data class TasksGroup(
    var groupName: String,
    var tasks: MutableList<String>,
    var color: Color = Color.White
)

// we should never do that and that's only for testing different implementation
// because the state should be in the view model
val tasksGroups = MutableStateFlow(
    listOf(
        TasksGroup(
            "group 1",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        TasksGroup(
            "group 2",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        TasksGroup(
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
                TasksGroupsBothSidesSwiping(groups) { groupIndex, taskIndexInGroup ->
                    Log.d(
                        TAG,
                        "onCreate: swipe listener groupIndex=$groupIndex, taskIndex=$taskIndexInGroup"
                    )
                    if (groupIndex != null) {
                        val newList = mutableListOf<TasksGroup>()
                        tasksGroups.value.forEachIndexed { index, tasksGroup ->
                            val newTasksList = tasksGroup.tasks.toList().toMutableList()
                            val tasksGroupName =
                                if (index == groupIndex)
                                    tasksGroups.value[index].groupName + "Changed"
                                else
                                    tasksGroups.value[index].groupName
                            val newTaskGroup = TasksGroup(tasksGroupName, newTasksList)
                            if (taskIndexInGroup != null) {
                                val taskDescription = if (index == groupIndex)
                                    tasksGroups.value[index].tasks[taskIndexInGroup] + "Changed"
                                else
                                    tasksGroups.value[index].tasks[taskIndexInGroup]
                                newTasksList[taskIndexInGroup] = taskDescription
                            }
                            newTaskGroup.color = if (index == groupIndex) {
                                Color.Magenta
                            } else {
                                tasksGroups.value[index].color
                            }
                            newList.add(index, newTaskGroup)
                        }
                        tasksGroups.value = newList
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroup(
    modifier: Modifier = Modifier,
    tasksGroup: TasksGroup,
    groupIndex: Int,
    onSwipe: (groupIndex: Int?, taskIndexIngGroup: Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(7.dp)
            .background(tasksGroup.color)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = tasksGroup.groupName,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp
            )
        }
        Column {
            tasksGroup.tasks.forEachIndexed { taskIndexInTasksGroup, item ->
                Log.d(TAG, "TasksGroup: $groupIndex")
                DismissibleTask(
                    modifier = Modifier.padding(top = 7.dp),
                    name = item,
                    taskIndexInTasksGroup = taskIndexInTasksGroup,
                    groupIndex = groupIndex,
                    onSwipe = onSwipe
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DismissibleTask(
    name: String,
    modifier: Modifier = Modifier,
    taskIndexInTasksGroup: Int,
    groupIndex: Int,
    onSwipe: (groupIndex: Int?, taskIndexIngGroup: Int?) -> Unit
) {
    val context = LocalContext.current
    val dismissState = rememberDismissState(
        confirmStateChange = {
            onSwipe(groupIndex, taskIndexInTasksGroup)
            Toast
                .makeText(context, "Item Swiped", Toast.LENGTH_SHORT)
                .show()
            false
        }
    )
    DismissibleContainer(
        dismissState = dismissState
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(context, name, Toast.LENGTH_SHORT)
                        .show()
                },
            elevation = 1.dp,
            backgroundColor = Color.DarkGray

        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun TasksGroupsBothSidesSwiping(
    groups: List<TasksGroup>,
    onSwipe: (groupIndex: Int?, taskIndexIngGroup: Int?) -> Unit
) {
    val context = LocalContext.current
    // This is an example of a list of dismissible items, similar to what you would see in an
    // email app. Swiping left reveals a 'delete' icon and swiping right reveals a 'done' icon.
    // The background will start as grey, but once the dismiss threshold is reached, the colour
    // will animate to red if you're swiping left or green if you're swiping right. When you let
    // go, the item will animate out of the way if you're swiping left (like deleting an email) or
    // back to its default position if you're swiping right (like marking an email as read/unread).
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .background(Color.LightGray)
    ) {
        itemsIndexed(groups) { groupIndex, tasksGroup ->

            val dismissState = rememberDismissState(
                confirmStateChange = {
                    Toast
                        .makeText(context, "Group Swiped", Toast.LENGTH_SHORT)
                        .show()
                    onSwipe(groupIndex, null)
                    false
                },
                initialValue = DismissValue.Default
            )
            DismissibleContainer(dismissState = dismissState) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            Toast
                                .makeText(context, tasksGroup.groupName, Toast.LENGTH_SHORT)
                                .show()
                        },
                    elevation = 1.dp
                ) {
                    TasksGroup(
                        tasksGroup = tasksGroup,
                        groupIndex = groupIndex,
                        onSwipe = onSwipe
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun DismissibleContainer(
    modifier: Modifier = Modifier,
    dismissState: DismissState,
    dismissContent: @Composable() (RowScope.() -> Unit)
) {
    SwipeToDismiss (
        modifier = modifier,
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.25f)
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.White
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.65f else 1f
            )
            val swipingText = when (direction) {
                DismissDirection.StartToEnd -> stringResource(id = R.string.mark_as_completed)
                DismissDirection.EndToStart -> stringResource(id = R.string.delete_task)
            }
            val iconAlignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color, shape = RoundedCornerShape(5.dp)),
            ) {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .align(iconAlignment)
                        .scale(scale)
                )
                Text(
                    text = swipingText,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }

        },
        dismissContent = dismissContent
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ListPreview() {
    val groups by tasksGroups.collectAsState()
    TasksGroupsBothSidesSwiping(groups) { groupIndex, taskIndexInGroup ->

    }
}