package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.HardwareMap

class Sorter(private val instance: LinearOpMode) {
    val cs = My_Color_Sensor(instance)

    var pattern = "PPG"

    fun setPattern(ObeliskId: Int){
        pattern = if(ObeliskId == 21){
            "GPP"
        }else if(ObeliskId == 22){
            "PGP"
        }else{
            "PPG"
        }
    }
    fun order(){

    }
}