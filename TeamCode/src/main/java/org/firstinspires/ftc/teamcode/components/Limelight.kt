//package org.firstinspires.ftc.teamcode.components
//
//import ca.helios5009.hyperion.misc.constants.Alliance
//import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult
//import com.qualcomm.hardware.limelightvision.Limelight3A
//import com.qualcomm.robotcore.hardware.HardwareMap
//
//class Limelight(private val hwMap: HardwareMap, private val alliance: Selector.alliance) {
//    val ll = hwMap.get(Limelight3A::class.java, "LL")
//    val goalId = if (alliance.name == "RED"){
//        24
//    }else{
//        20
//    }
//    fun llstart(){
//        ll.start()
//    }
//    fun stop(){
//        ll.stop()
//    }
//    fun detectO(): Int{
//        val result = ll.latestResult
//        if(result.isValid) {
//            // Access fiducial results
//            val fiducialResults: List<FiducialResult> = result.getFiducialResults()
//            for (i in fiducialResults) {
//                if (i.fiducialId > 20 && i.fiducialId < 24) {
//                    return i.fiducialId
//                }
//            }
//        }
//        return -1
//    }
//
//    fun detectG(): Boolean{
//        val result = ll.latestResult
//        if(result.isValid) {
//            // Access fiducial results
//            val fiducialResults: List<FiducialResult> = result.getFiducialResults()
//            for (i in fiducialResults) {
//                if (i.fiducialId == goalId) {
//                    return true
//                }
//            }
//        }
//        return false
//    }
//
//
//
//}