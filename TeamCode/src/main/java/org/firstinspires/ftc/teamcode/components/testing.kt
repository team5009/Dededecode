package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY

class testing(private val instance: LinearOpMode) {
    val intake = HyperionMotor(instance.hardwareMap, "CH")
    val shooter = HyperionMotor(instance.hardwareMap, "Shooter")

    init {
        intake.motor.direction = DcMotorSimple.Direction.FORWARD
        intake.motor.direction = DcMotorSimple.Direction.REVERSE
        shooter.motor.direction = DcMotorSimple.Direction.FORWARD
        shooter.motor.direction = DcMotorSimple.Direction.REVERSE
    }
}