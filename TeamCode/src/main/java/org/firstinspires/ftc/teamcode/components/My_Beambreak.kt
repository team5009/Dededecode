package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.Servo

class My_Beambreak(private val instance: LinearOpMode) {

//Init (Note: DigitalChannel must be inited in a JavaClass)
    val breakbeam = instance.hardwareMap.get(DigitalChannel::class.java, "bb1");
    val flapper = instance.hardwareMap.get(Servo::class.java, "Feeder")

    fun beambreak(){
        breakbeam.setMode(DigitalChannel.Mode.INPUT);
    }
    fun flapper(position: Double){
        flapper.setPosition(1.0)
    }


    val was_broken = false
    val was_lifting = false

}