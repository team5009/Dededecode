package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.misc.constants.Alliance
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.atan
import kotlin.math.sqrt
import kotlin.math.tan

class Limelight(private val hwMap: HardwareMap, private val alliance: Selector.alliance) {
    val ll = hwMap.get(Limelight3A::class.java, "LL")

    var fr: FiducialResult? = null

    //vars and vals
    var y = 0.0
    var x = 0.0

    val goalId = if (alliance.name == "RED"){
        24
    }else{
        20
    }

    fun llstart(){
        ll.start()
    }
    fun stop(){
        ll.stop()
    }
    fun detectO(): Int{
        val result = ll.latestResult
        if(result.isValid) {
            // Access fiducial results
            val fiducialResults: List<FiducialResult> = result.getFiducialResults()
            for (i in fiducialResults) {
                if (i.fiducialId > 20 && i.fiducialId < 24) {
                    return i.fiducialId
                }
            }
        }
        return -1
    }
    fun detectG(): Boolean{
        val result = ll.latestResult
        if(result.isValid) {
            // Access fiducial results
            val fiducialResults: List<FiducialResult> = result.getFiducialResults()
            for (i in fiducialResults) {
                if (i.fiducialId == goalId) {
                    fr = i
                    x = fr!!.targetXDegrees
                    y = fr!!.targetYDegrees
                    return true
                }
            }
        }
        fr = null
        return false
    }
    fun distance(): Double{
        if(fr != null) {
            return (29.5 - (315.134/25.4)) / tan(80.0 + ty())
        }
        return -1.0
    }
    fun tx():Double{
        if(fr != null){
            return x
        }
        return -1.0
    }
    fun ty(): Double{
        if(fr != null){
            return y
        }
        return -1.0
    }
    fun angle(): Double{
        if(fr != null){
            return x
        }
        return 361.0
    }
}