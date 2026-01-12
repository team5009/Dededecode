package org.firstinspires.ftc.teamcode.components

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.I2cDevice
import org.firstinspires.ftc.teamcode.Robot

class Megatag2(instance: LinearOpMode, alliance: Selector.alliance) {

    val ll = Limelight(instance.hardwareMap, alliance)

    fun init_imu(){

    }
}