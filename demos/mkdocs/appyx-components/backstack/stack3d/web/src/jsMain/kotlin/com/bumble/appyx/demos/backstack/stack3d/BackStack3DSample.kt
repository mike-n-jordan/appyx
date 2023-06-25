package com.bumble.appyx.demos.backstack.stack3d

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.stack3d.BackStack3D
import com.bumble.appyx.interactions.core.model.BaseInteractionModel
import com.bumble.appyx.demos.common.AppyxWebSample
import com.bumble.appyx.demos.common.InteractionTarget

@Composable
fun BackStack3DSample(
    screenWidthPx: Int,
    screenHeightPx: Int,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val model = remember {
        BackStackModel<InteractionTarget>(
            initialTarget = InteractionTarget.Element(),
            savedStateMap = null
        )
    }
    val backStack =
        BackStack(
            scope = coroutineScope,
            model = model,
            motionController = { BackStack3D(it) },
            gestureFactory = { BackStack3D.Gestures(it) }
        )
    val actions = mapOf(
        "Pop" to { backStack.pop() },
        "Push" to { backStack.push(InteractionTarget.Element()) }
    )
    AppyxWebSample(
        screenWidthPx = screenWidthPx,
        screenHeightPx = screenHeightPx,
        interactionModel = backStack.unsafeCast<BaseInteractionModel<InteractionTarget, Any>>(),
        actions = actions,
        modifier = modifier,
    )
}