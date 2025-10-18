package org.firstinspires.ftc.teamcode.components

import androidx.core.util.toHalf
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlin.math.PI

class testing(private val instance: LinearOpMode) {
    val intake = HyperionMotor(instance.hardwareMap, "CH")
    val shooter = HyperionMotor(instance.hardwareMap, "Shooter")
    val feeder = instance.hardwareMap.get(Servo::class.java, "Feeder")
    val push_r = instance.hardwareMap.get(Servo::class.java, "R")
    val push_l = instance.hardwareMap.get(Servo::class.java, "L")

    val convert = (PI*4)/(28*1.6)

    init {
        shooter.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intake.motor.direction = DcMotorSimple.Direction.FORWARD
        intake.motor.direction = DcMotorSimple.Direction.REVERSE
        shooter.motor.direction = DcMotorSimple.Direction.FORWARD
        shooter.motor.direction = DcMotorSimple.Direction.REVERSE
    }
    fun init_auto(){
        lift(1.0)
        push_l(0.5)
        push_r(0.5)
    }

    fun power_mod(target: Double){
        shooter.power = shooter.power + (((target - (shooter.velocity * convert)) / target)) / 100.0
    }
    fun lift(position: Double){
        feeder.position = position
    }
    fun push_r(position: Double){
        push_r.position = position
    }
    fun push_l(position: Double){
        push_l.position = position
    }
    fun velocity():Double{
        return shooter.velocity * convert
    }
    fun rpm():Double{
        return shooter.velocity / 28 * 60
    }
}