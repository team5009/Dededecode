package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.hardware.HardwareMap

class Sorter(private val hwMap: HardwareMap) {
    var pattern = "PPG"

    fun setPattern(OId: Int){
        pattern = if(OId == 21){
            "GPP"
        }else if(OId == 22){
            "PGP"
        }else{
            "PPG"
        }
    }
}