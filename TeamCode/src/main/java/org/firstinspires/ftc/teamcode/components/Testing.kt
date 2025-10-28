package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.core.PIDFController
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.PI
import kotlin.math.min

class Testing(private val instance: LinearOpMode) {
    val intake = HyperionMotor(instance.hardwareMap, "CH")
    val shooter = HyperionMotor(instance.hardwareMap, "Shooter")
    val feeder = instance.hardwareMap.get(Servo::class.java, "Feeder")
    val push_r = instance.hardwareMap.get(Servo::class.java, "R")
    val push_l = instance.hardwareMap.get(Servo::class.java, "L")

    val convert = (PI*4)/(28*1.6)

    var lastVelocity = 0.0
    var lastTime: Double = 0.0
    var period: Double = 0.0

    init {
        shooter.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intake.motor.direction = DcMotorSimple.Direction.FORWARD
        intake.motor.direction = DcMotorSimple.Direction.REVERSE
        shooter.motor.direction = DcMotorSimple.Direction.FORWARD
        shooter.motor.direction = DcMotorSimple.Direction.REVERSE
    }
    fun init_auto(){
        lift(1.0)
        push_l(0.6)
        push_r(0.5)
    }

    fun power_mod(target: Double){
        val v_error = (target - (shooter.velocity * convert))
        val currentTime : Double = (System.nanoTime() / 1e9).toDouble();
        if (lastTime.equals(0.0)) lastTime = currentTime;
        period = currentTime - lastTime;
        lastTime = currentTime;
        shooter.setPowerWithTol(min((shooter.power + (v_error / target) / 50.0), 1.0))
        lastVelocity = v_error
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
    companion object{
        val shooting = AtomicReference(false)
    }
}