package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.core.PIDFController
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.TurretPID
import java.util.HashMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.collections.set
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.sin
import kotlin.math.sqrt


class Turret(private val instance: LinearOpMode, alliance: Selector.alliance, val bot: Robot) {
    //hardwareMap
    val fly_top = HyperionMotor(instance.hardwareMap, "Top")
    val fly_bottom = HyperionMotor(instance.hardwareMap, "Bottom")
    val hood = instance.hardwareMap.get(Servo::class.java, "Hood")
    val left_spin = instance.hardwareMap.get(CRServoImplEx::class.java, "LS")
    val right_spin = instance.hardwareMap.get(CRServoImplEx::class.java, "RS")
    val turret_en = instance.hardwareMap.get(AnalogInput::class.java, "turret_en")
    val breakbeam = Java_Beambreak().create_sensor("bb1", instance.hardwareMap, true)
    val ll = Limelight(instance.hardwareMap, alliance, true)

    //pidf
    val turret_pid = PIDFController(TurretPID.kP, TurretPID.kI, TurretPID.kD, TurretPID.kF)

    //vars
    val height = 15.0 // ask sam
    val min_hood = 10.5
    val max_hood = 45.0
    val current_voltage = turret_en.voltage
    var last_voltage = 0.0
    val current_rot = bot.otos.position.rot
    var last_rot = 0.0
    var current_pose = PI/2.0
    val pos_max = PI/4

    //conversion vals
    val fly_convert = (PI*2.835)/28
    val turret_conv = 1.0/3.0

    //dictionary val
    val turret_dict = HashMap<Int, Double>()

    init {
        ll.llstart()
        fly_top.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        fly_top.motor.direction = DcMotorSimple.Direction.FORWARD
        fly_top.motor.direction = DcMotorSimple.Direction.REVERSE
        fly_bottom.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        fly_bottom.motor.direction = DcMotorSimple.Direction.FORWARD
        fly_bottom.motor.direction = DcMotorSimple.Direction.REVERSE

        //DICTIONARY

        for (i in 0..171 step 3) {
            val rad : Double = (i) * PI / 180.0
            if (i > 0 && i < 171.0) {
                val speed = fly_top.velocity * fly_convert * 66/40
                val hood = pos_max + abs(sin(rad) * pos_max)
                turret_dict[i] = speed
            }else{
                turret_dict[i] = 0.0 //help me
            }
        }
    }
    fun init_auto(){
    }
    fun init_teleOp(){
    }
    fun update_index(): Int {
        if (current_voltage > 2.8 && last_voltage < 1.0 && index.get() < 2) {
            index.set(index.get() + 1)
        } else if (last_voltage > 2.8 && current_voltage < 1.0 && index.get() > 0) {
            index.set(index.get() - 1)
        }
        last_voltage = current_voltage

        return index.get()
    }
    fun current_pose_angle(): Double {
        return (current_voltage + index.get() * 3.3) * 360 / (2*PI) / 9.9
    }
    fun set_hood_angle(distance: Double, speed: Double){
        val hood_angle = asin((distance * 1.5 * 9.81) / (speed * speed) ) / 2.0
        hood.position = (hood_angle - min_hood) / (max_hood - min_hood)
    }
    fun go_to(targ: Double) {
        var output = 0.0

        output = turret_pid.directCalculate(ll.angle() - targ)

        if(ll.angle() == 361.0 || current_pose_angle() < 0.0 || current_pose_angle() > 180.0){
            left_spin.power = 0.0
            right_spin.power = 0.0
        }else {
            left_spin.power = output
            right_spin.power = output
        }
    }
    companion object{
        var index = AtomicReference(0)
    }
}