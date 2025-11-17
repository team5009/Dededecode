package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.hardware.HyperionMotor
import ca.helios5009.hyperion.misc.constants.Alliance
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.PI

class Turret(private val instance: LinearOpMode, alliance: Selector.alliance) {
    //hardwareMap
    val fly = HyperionMotor(instance.hardwareMap, "Shooter")
    val hood = instance.hardwareMap.get(Servo::class.java, "Hood")
    val left_spin = instance.hardwareMap.get(Servo::class.java, "LS")
    val right_spin = instance.hardwareMap.get(Servo::class.java, "RS")
    val breakbeam = Java_Beambreak().create_sensor("bb1", instance.hardwareMap, true)
    //val ll = Limelight(instance.hardwareMap, alliance)

    //conversion vars
    val convert = (PI*4)/(28*1.6)

    init{
        fly.motor.direction = DcMotorSimple.Direction.FORWARD
        fly.motor.direction = DcMotorSimple.Direction.REVERSE
    }
    fun tracking(){

    }

}