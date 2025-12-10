package org.firstinspires.ftc.teamcode.components

import android.graphics.Color
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.NormalizedRGBA
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class My_Color_Sensor (private val instance: LinearOpMode) {

    var sensors:ArrayList<NormalizedColorSensor> = arrayListOf()
    init {
        val sensors = arrayListOf(
            Java_Color_Sensor().get("CSR", instance.hardwareMap),
            Java_Color_Sensor().get("CSM", instance.hardwareMap),
            Java_Color_Sensor().get("CSL", instance.hardwareMap)
        )
    }
    fun sensor() : ArrayList<String>{
        val hsvValues = FloatArray(3)
        var i = 0
        val c: ArrayList<String> = arrayListOf()
        for(s in sensors) {
            Color.colorToHSV(s.normalizedColors.toColor(), hsvValues)

            if(hsvValues[0] > 90.0 && hsvValues[0] < 150.0){
                c.add("GREEN")
            }else if(hsvValues[0] > 250.0 && hsvValues[0] < 300.0){
                c.add("PURPLE")
            }else {
                c.add("NOTHING")
            }

        }
        return c
    }
//    fun dist(): Double{
//       for(s in sensors) {
//           return (s as DistanceSensor).getDistance(DistanceUnit.CM)
//       }
//    }
}