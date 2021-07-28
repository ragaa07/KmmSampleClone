package com.jetbrains.kmm.androidApp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.jetbrains.androidApp.databinding.ActivityMainBinding
import com.jetbrains.kmm.androidApp.adapter.SwipeAdapter
import com.jetbrains.kmm.androidApp.ui.theme.KmmSampleTheme


class MainActivity2 : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmSampleTheme {
                val tasksGroups = listOf(
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
                TasksGroupsList(tasksGroups = tasksGroups)

            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroupsList(tasksGroups: List<Pair<String, MutableList<String>>>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        itemsIndexed(tasksGroups) { index, item ->
            val adapter = SwipeAdapter(tasksGroups[index].second)
            TasksGroup(tasksGroup = item, adapter = adapter)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroup(
    modifier: Modifier = Modifier,
    tasksGroup: Pair<String, MutableList<String>>,
    adapter: SwipeAdapter
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 8.dp
    ) {
        val context = LocalContext.current
        Column {
            Column {
                //Group Number
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
                // List of tasks With Swipe
                AndroidViewBinding(factory = ActivityMainBinding::inflate) {
                    this.list.adapter = adapter
                    this.list.layoutManager = LinearLayoutManager(context)

                    this.list.reduceItemAlphaOnSwiping = true
                    // Disable swipe to  the right
                    this.list.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
                    // Disable dragging to up
                    this.list.disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
                    this.list.disableDragDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)

                    val onItemSwipedListner = object : OnItemSwipeListener<String> {
                        override fun onItemSwiped(
                            position: Int,
                            direction: OnItemSwipeListener.SwipeDirection,
                            item: String
                        ): Boolean {
                            when (direction) {
                                OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT -> {
                                    Toast.makeText(context, "$item is done ", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else -> return false
                            }
                            return true
                        }
                    }
                    this.list.swipeListener = onItemSwipedListner
                }
            }
        }
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun TasksGroupsListPreview() {
    val tasksGroups = listOf(
        Pair(
            "group 1",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        Pair(
            "group 2",
            mutableListOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        )

    )
    TasksGroupsList(tasksGroups = tasksGroups)
}