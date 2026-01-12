package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.tan

class Limelight(private val hwMap: HardwareMap/*, private val t: Turret*/, private val alliance: Selector.alliance, private val is_auto: Boolean = false) {
    val ll = hwMap.get(Limelight3A::class.java, "LL")

    var fr: FiducialResult? = null

    //vars and vals
    var y = 0.0
    var x = 0.0

    init{
        ll.stop()
    }

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
    fun update_turret(t: Turret){
        t.go_to(0.0)
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
    fun G_info(): Point{
        val result = ll.latestResult
        if(result.isValid) {
            // Access fiducial results
            val fiducialResults: List<FiducialResult> = result.getFiducialResults()
            for (i in fiducialResults) {
                if (i.fiducialId == goalId) {
                    fr = i
                    x = fr!!.targetXDegrees
                    y = fr!!.targetYDegrees
                    //update_turret()
                    return Point(
                        result.botpose.position.x,
                        result.botpose.position.y
                    ).setDeg(result.botpose.orientation.yaw)
                }
            }
        }
        fr = null
        return Point(-1.0, -1.0).setDeg(361.0)
    }
    fun distance(): Double{
        if(fr != null && !ty().isNaN()) {
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
        return Double.NaN
    }
    fun angle(): Double{
        if(fr != null){
            return x
        }
        return 361.0
    }
    fun pose(){
        val result = ll.latestResult
        if(result.isValid){
            result.botpose
        }
    }
}