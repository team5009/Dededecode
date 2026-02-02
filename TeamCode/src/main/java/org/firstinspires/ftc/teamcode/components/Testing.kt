package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.PI
import kotlin.math.withSign

class Testing(private val instance: LinearOpMode) {


    val intake = HyperionMotor(instance.hardwareMap, "CH")
    val hood_adjuster = instance.hardwareMap.get(Servo::class.java, "Hood")
    val shooter = instance.hardwareMap.get(DcMotorEx::class.java, "Shooter")
    val feeder = instance.hardwareMap.get(Servo::class.java, "Feeder")
    val push_r = instance.hardwareMap.get(Servo::class.java, "R")
    val push_l = instance.hardwareMap.get(Servo::class.java, "L")
    val breakbeam = Java_Beambreak().create_sensor("bb1", instance.hardwareMap, true)
    val hood = instance.hardwareMap.get(Servo::class.java, "Hood")

    val power_controller = ShooterPIDF(0.00002, -0.004)  // kP, kD
    val shot_controller = Shooter_Controller(0.0004, -0.004)

    val convert = (PI*4*1.6)/(28)


    val gain = 0.008
    var lastVelocity = 0.0

    init {
        shooter.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        intake.motor.direction = DcMotorSimple.Direction.REVERSE
        intake.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        shooter.direction = DcMotorSimple.Direction.REVERSE
    }
    fun init_auto(){
        lift(1.0)
        push_l(0.6)
        push_r(0.5)
    }

/*    fun power_mod(target: Double){
        val v_error = (target - (shooter.velocity * convert))
        val currentTime : Double = (System.nanoTime() / 1e9).toDouble();
        if (lastTime.equals(0.0)) lastTime = currentTime;
        period = currentTime - lastTime;
        lastTime = currentTime;
        shooter.power = min((shooter.power + (v_error / target) / 50.0), 1.0)
        lastVelocity = v_error
    }
 */
    fun power_mod() {
        shot_controller.power_mod(shooter)
    }

    fun hood(pos: Double) {
        hood.position = pos
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