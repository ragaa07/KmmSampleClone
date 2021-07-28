package com.jetbrains.kmm.androidApp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
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
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe


class MainActivity2 : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmSampleTheme {
                val tasksGroups = listOf(
                    Pair(
                        "group 1",
                        listOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
                    ),
                    Pair(
                        "group 2",
                        listOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
                    )
                )
                TasksGroupsList(tasksGroups)
            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroupsList(tasksGroups: List<Pair<String, List<String>>>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        itemsIndexed(tasksGroups) { _, item ->
            TasksGroup(tasksGroup = item)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TasksGroup(modifier: Modifier = Modifier, tasksGroup: Pair<String, List<String>>) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 8.dp
    ) {
        val context = LocalContext.current
        Column {
            RevealSwipe(
                modifier = Modifier.padding(vertical = 5.dp),
                directions = setOf(
                    //        RevealDirection.StartToEnd,
                    RevealDirection.EndToStart
                ),
                hiddenContentStart = {
                    Icon(
                        modifier = Modifier.padding(horizontal = 25.dp),
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                hiddenContentEnd = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .clickable {
                                Toast
                                    .makeText(context, tasksGroup.first, Toast.LENGTH_SHORT)
                                    .show()
                            },
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,

                        )
                }
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
                        tasksGroup.second.forEachIndexed { index, item ->
                            TaskCard(name = item, index = index + 1)
                        }
                    }

                }

            }

        }

    }
}

@ExperimentalMaterialApi
@Composable
fun TaskCard(
    name: String,
    modifier: Modifier = Modifier,
    index: Int
) {
    val context = LocalContext.current
    RevealSwipe(
        modifier = Modifier.padding(vertical = 5.dp),
        directions = setOf(
            //        RevealDirection.StartToEnd,
            RevealDirection.EndToStart
        ),
        hiddenContentStart = {
        },
        hiddenContentEnd = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable {
                        Toast
                            .makeText(context, "$index", Toast.LENGTH_SHORT)
                            .show()
                    },
                imageVector = Icons.Outlined.Add,
                contentDescription = null,

                )

        }
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
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
    val tasksGroups = listOf(
        Pair(
            "group 1",
            listOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        ),
        Pair(
            "group 2",
            listOf("Do HomeWork", "Go to Gym ", "arrange the bed", "take launch")
        )
    )
    TasksGroupsList(tasksGroups)
}