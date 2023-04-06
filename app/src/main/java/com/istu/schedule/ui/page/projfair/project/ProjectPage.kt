package com.istu.schedule.ui.page.projfair.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.istu.schedule.R
import com.istu.schedule.domain.model.projfair.Project
import com.istu.schedule.ui.components.base.AppComposable
import com.istu.schedule.ui.components.base.CustomIndicator
import com.istu.schedule.ui.components.base.FilledButton
import com.istu.schedule.ui.components.base.SIScrollableTabRow
import com.istu.schedule.ui.components.base.SITabPosition
import com.istu.schedule.ui.components.base.TwoColumnText
import com.istu.schedule.ui.icons.People
import com.istu.schedule.ui.theme.HalfGray
import com.istu.schedule.util.toProjectDifficulty
import java.text.DateFormat

@Composable
fun ProjectPage(
    projectId: Int,
    navController: NavController,
    viewModel: ProjectViewModel = hiltViewModel(),
) {
    val project by viewModel.project.observeAsState(initial = null)
    viewModel.getProjectById(projectId)

    AppComposable(
        viewModel = viewModel,
        content = {
            ProjectPage(
                navController = navController,
                project = project,
            )
        },
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun ProjectPage(
    navController: NavController,
    project: Project?,
) {
    val pagerState = rememberPagerState()
    val pages = listOf("О проекте", "Список заявок")
    val indicator = @Composable { tabPositions: List<SITabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }

    BackdropScaffold(
        modifier = Modifier.statusBarsPadding(),
        appBar = {
            Text(
                modifier = Modifier.padding(15.dp),
                text = stringResource(id = R.string.projfair),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        peekHeight = 125.dp,
        gesturesEnabled = false,
        backLayerBackgroundColor = MaterialTheme.colorScheme.primary,
        backLayerContent = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 10.dp)
            ) {
                SIScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = indicator,
                    edgePadding = 0.dp,
                ) {
                    pages.forEachIndexed { index, title ->
                        Column(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(end = if (index != pages.size - 1) 20.dp else 0.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontSize = 18.sp,
                                ),
                                text = title,
                            )
                        }
                    }
                }
            }
        },
        frontLayerBackgroundColor = MaterialTheme.colorScheme.surface,
        frontLayerContent = {
            Column(
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 23.dp,
                    bottom = 50.dp,
                ),
            ) {
                Box(
                    modifier = Modifier.clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    ) {
                        navController.popBackStack()
                    },
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                        Text(
                            modifier = Modifier.padding(start = 9.dp),
                            text = stringResource(R.string.back_to_list),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                    }
                }
                project?.let { project ->
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        count = pages.size,
                        state = pagerState,
                    ) { page ->
                        when (page) {
                            0 -> ProjectInfo(project)
                            1 -> Text("2")
                        }
                    }
                } ?: run {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        },
    )
}

@Composable
fun ProjectInfo(
    project: Project
) {
    Column(
        modifier = Modifier
            .padding(top = 23.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.maximum_participants),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.People,
                        contentDescription = "People Icon",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = project.places.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = stringResource(R.string.project_status),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(
                                width = 1.45.dp,
                                MaterialTheme.colorScheme.primary,
                            ),
                            RoundedCornerShape(72.dp),
                        )
                        .padding(24.dp, 7.dp),
                ) {
                    Text(
                        text = project.state.state.uppercase(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = HalfGray,
        )
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            key = stringResource(R.string.project_manager),
            value = project.supervisorsNames,
        )

        project.customer.isNotBlank().let { isNotBlank ->
            if (isNotBlank) {
                TwoColumnText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    key = stringResource(R.string.customer),
                    value = project.customer,
                )

            }
        }
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            key = stringResource(R.string.timeline),
            value = "${
                DateFormat.getDateInstance(DateFormat.LONG).format(project.dateStart)
            } - ${DateFormat.getDateInstance(DateFormat.LONG).format(project.dateEnd)}",
        )

        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            key = stringResource(R.string.difficulty),
            value = project.difficulty.toProjectDifficulty(),
        )
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            key = stringResource(R.string.type_of_project),
            value = project.type.type,
        )
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp),
            key = stringResource(R.string.project_goal),
            value = project.goal,
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = HalfGray,
        )
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            key = stringResource(R.string.expected_result),
            value = project.productResult,
        )
        TwoColumnText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp),
            key = stringResource(R.string.requirements_for_participants),
            value = project.studyResult,
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = HalfGray,
        )
        Column(modifier = Modifier.padding(vertical = 22.dp)) {
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(R.string.project_idea),
            )
            Spacer(Modifier.height(11.dp))
            Text(
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
                text = project.description,
            )
        }
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = stringResource(R.string.required_skills),
        )
        FilledButton(
            modifier = Modifier
                .padding(top = 22.dp)
                .fillMaxWidth()
                .height(42.dp),
            text = stringResource(R.string.send_application),
        )
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}
