package com.bumble.appyx.demos.sample3

import com.bumble.appyx.interactions.core.ui.property.impl.BackgroundColor
import com.bumble.appyx.interactions.core.ui.property.impl.RoundedCorners
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionOffset
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@Suppress("unused")
@MutableUiStateSpecs
class TargetUiState(
    val positionAlignment: PositionAlignment.Target,
    val positionOffset: PositionOffset.Target,
    val roundedCorners: RoundedCorners.Target = RoundedCorners.Target(10),
    val backgroundColor: BackgroundColor.Target,
)
