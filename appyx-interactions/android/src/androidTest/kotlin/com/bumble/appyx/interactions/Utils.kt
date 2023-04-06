package com.bumble.appyx.interactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.bumble.appyx.interactions.core.model.BaseInteractionModel
import com.bumble.appyx.interactions.core.ui.helper.InteractionModelSetup
import com.bumble.appyx.interactions.sample.Children
import com.bumble.appyx.interactions.sample.SpotlightUi
import com.bumble.appyx.interactions.sample.TestDriveUi
import com.bumble.appyx.interactions.theme.appyx_dark
import com.bumble.appyx.transitionmodel.spotlight.Spotlight
import com.bumble.appyx.transitionmodel.testdrive.TestDrive
import com.bumble.appyx.transitionmodel.testdrive.TestDriveModel
import kotlin.random.Random

fun <InteractionTarget : Any> ComposeContentTestRule.setupSpotlight(
    spotlight: Spotlight<InteractionTarget>,
) {
    setContent {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = appyx_dark
        ) {
            InteractionModelSetup(spotlight)

            SpotlightUi(
                spotlight = spotlight,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun <InteractionTarget : Any> ComposeContentTestRule.setupTestDrive(
    testDrive: TestDrive<InteractionTarget>,
    testDriveModel: TestDriveModel<InteractionTarget>,
) {
    setContent {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = appyx_dark
        ) {
            InteractionModelSetup(testDrive)

            TestDriveUi(
                testDrive = testDrive,
                model = testDriveModel
            )
        }
    }
}

fun <InteractionTarget : Any, ModelState : Any> ComposeContentTestRule.setupInteractionModel(
    interactionModel: BaseInteractionModel<InteractionTarget, ModelState>,
    fraction: Float = 1.0f
) {
    setContent {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = appyx_dark
        ) {
            InteractionModelSetup(interactionModel)
            TestChildrenUi(
                fraction = fraction,
                interactionModel = interactionModel
            )
        }
    }
}

fun randomColor(): Color {
    val random = Random(System.currentTimeMillis())
    return Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
}


@Composable
private fun <NavTarget : Any, ModelState : Any> TestChildrenUi(
    fraction: Float = 1.0f,
    interactionModel: BaseInteractionModel<NavTarget, ModelState>
) {
    BoxWithConstraints {
        val padding = this.maxWidth * (1.0f - fraction) / 2
        Children(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = padding)
                .background(
                    color = randomColor()
                ),
            interactionModel = interactionModel,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "${it.element.interactionTarget}",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}
