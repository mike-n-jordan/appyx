package com.bumble.appyx.components.spotlight.ui.sliderscale

import androidx.compose.foundation.gestures.Orientation
import com.bumble.appyx.components.spotlight.SpotlightModel.State
import com.bumble.appyx.components.spotlight.SpotlightModel.State.ElementState.CREATED
import com.bumble.appyx.components.spotlight.SpotlightModel.State.ElementState.DESTROYED
import com.bumble.appyx.components.spotlight.SpotlightModel.State.ElementState.STANDARD
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.GenericFloatProperty
import com.bumble.appyx.interactions.core.ui.property.impl.GenericFloatProperty.Target
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.OutsideAlignment.Companion.InContainer
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.OutsideAlignment.Companion.OutsideBottom
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.OutsideAlignment.Companion.OutsideTop
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionAlignment
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseVisualisation

class SpotlightSliderScale<InteractionTarget : Any>(
    uiContext: UiContext,
    initialState: State<InteractionTarget>,
    @Suppress("UnusedPrivateMember")
    private val orientation: Orientation = Orientation.Horizontal, // TODO support RTL
) : BaseVisualisation<InteractionTarget, State<InteractionTarget>, MutableUiState, TargetUiState>(
    uiContext = uiContext
) {
    @Suppress("MaxLineLength")
    private val scrollX = GenericFloatProperty(
        coroutineScope = uiContext.coroutineScope,
        target = Target(initialState.activeIndex),
    )
    override val viewpointDimensions: List<Pair<(State<InteractionTarget>) -> Float, GenericFloatProperty>> =
        listOf(
            { state: State<InteractionTarget> -> state.activeIndex } to scrollX
        )

    private val created: TargetUiState = TargetUiState(
        positionAlignment = PositionAlignment.Target(OutsideTop),
        scale = Scale.Target(0f),
    )

    private val standard: TargetUiState = TargetUiState(
        positionAlignment = PositionAlignment.Target(InContainer),
        scale = Scale.Target(1f),
    )

    private val destroyed: TargetUiState = TargetUiState(
        positionAlignment = PositionAlignment.Target(OutsideBottom),
        scale = Scale.Target(0f),
    )

    override fun State<InteractionTarget>.toUiTargets(): List<MatchedTargetUiState<InteractionTarget, TargetUiState>> {
        return positions.flatMapIndexed { index, position ->
            position.elements.map {
                MatchedTargetUiState(
                    element = it.key,
                    targetUiState = TargetUiState(
                        base = when (it.value) {
                            CREATED -> created
                            STANDARD -> standard
                            DESTROYED -> destroyed
                        },
                        positionInList = index
                    )
                )
            }
        }
    }

    override fun mutableUiStateFor(uiContext: UiContext, targetUiState: TargetUiState): MutableUiState =
        targetUiState.toMutableState(uiContext, scrollX.renderValueFlow)
}

