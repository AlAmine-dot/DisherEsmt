package com.example.esmt.cours.disher.core.presentation.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.graphs.Graph
import com.example.esmt.cours.disher.core.util.OnBoardingPage
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.animation.core.animateDpAsState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingContent(
    pages: List<OnBoardingPage> = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third),
    navController: NavHostController,
    welcomeViewModel: OnBoardingViewModel = hiltViewModel(),
    applicationName: String = "Disher",
) {
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 16.dp,
                backgroundColor = Color.White,
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_disher_white),
                                contentDescription = "",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(60.dp)
                            )
                            Text(
                                text = applicationName,
                                color = MeltyGreen,
                                style = MaterialTheme.typography.h3,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 30.sp
                            )
                        }

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                // elevation = 16.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (pagerState.currentPage == pages.lastIndex) {
                            FinishButton(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .weight(1f),
                                //pagerState = viewPagerState
                            ) {
                                welcomeViewModel.saveOnBoardingState(completed = true)
                                navController.popBackStack()
                                navController.navigate(Graph.HOME.route)
                            }
                        }
                        HorizontalPagerIndicator(
                            activeColor = MeltyGreen,
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .weight(1f),
                        )
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(state = pagerState, count = pages.size) { page ->
                    PagerScreen(onBoardingPage = pages[page])
                }
            }
        },
    )
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = onBoardingPage.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = onBoardingPage.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            style = MaterialTheme.typography.h6,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = onBoardingPage.title,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
        )
    }
}

//@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(MeltyGreen)
        ) {
            Text(text = "Finish", color = Color.White)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Third)
    }
}