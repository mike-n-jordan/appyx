package com.bumble.appyx.interactions.core.inputsource

import com.bumble.appyx.interactions.core.Operation
import com.bumble.appyx.interactions.core.TransitionModel

class InstantInputSource<NavTarget : Any, ModelState>(
    private val model: TransitionModel<NavTarget, ModelState>,
) : InputSource<NavTarget, ModelState> {


    override fun operation(operation: Operation<ModelState>, mode: TransitionModel.OperationMode) {
        when (mode) {
            TransitionModel.OperationMode.KEYFRAME -> {
                model.enqueue(operation)
                model.setProgress(progress = model.maxProgress)
            }
            TransitionModel.OperationMode.IMMEDIATE -> {
                model.updateState(operation)
            }
        }
    }
}
