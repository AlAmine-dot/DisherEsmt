package com.example.esmt.cours.disher.core.presentation.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.example.esmt.cours.disher.core.common.util.OnBoardingPage
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        HorizontalPager(
            modifier = Modifier.weight(10f).background(Color.White),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            activeColor = MeltyGreen,
            pagerState = pagerState
        )
        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState
        ) {
            welcomeViewModel.saveOnBoardingState(completed = true)
            navController.popBackStack()
            navController.navigate(Graph.HOME.route)
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.disher_branding_white),
            contentDescription = "Disher branding",
            modifier = Modifier.width(230.dp)
        )

        Text(
            text = onBoardingPage.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            style = MaterialTheme.typography.h4,
            color = DarkTurquoise,
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
@ExperimentalAnimationApi
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                modifier = Modifier
                    .widthIn(240.dp)
                    .heightIn(100.dp)
                    .padding(bottom = 5.dp),
                onClick = onClick,
                shape = RoundedCornerShape(70.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MeltyGreen),
            ) {
                Text(
                    text = "Let's dish !",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
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