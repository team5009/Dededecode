package org.firstinspires.ftc.teamcode.components

import kotlin.math.withSign

class ShooterPIDF (private val kP: Double, private val kD: Double){
    var targetPosition: Double = 0.0
    var last_error: Double = Double.NaN

    fun setTarget(target: Double) {
        targetPosition = target
    }

    private fun getPositionError(measuredPosition: Double): Double {
        return targetPosition - measuredPosition
    }

    fun update(measuredPosition: Double): Double {
        val error = getPositionError(measuredPosition)

        if (last_error == Double.NaN) {
            last_error = error
            return 0.0
        } else {
            val error_diff = last_error - error

            last_error = error
            val base_power = 0.0001 * measuredPosition + 0.0003 * error_diff
            val kF = if (base_power <= 0.1) {
                0.0
            } else {
                base_power - 1.0.withSign(base_power) * 0.02
            }
            return kP * error + kD * error_diff + kF
        }
    }

    fun reset() {
        last_error = Double.NaN
    }
}