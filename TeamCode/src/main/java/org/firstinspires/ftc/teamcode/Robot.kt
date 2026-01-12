package org.firstinspires.ftc.teamcode

import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.hardware.HyperionMotor
import ca.helios5009.hyperion.hardware.Otos
import ca.helios5009.hyperion.misc.events.EventListener
import ca.helios5009.hyperion.pathing.PathBuilder
import ca.helios5009.hyperion.pathing.Point
import com.acmerobotics.dashboard.config.Config
import com.bylazar.battery.PanelsBattery
import com.bylazar.configurables.annotations.Configurable
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.configuration.EthernetOverUsbConfiguration
import org.firstinspires.ftc.teamcode.components.Selector
//import org.firstinspires.ftc.teamcode.components.Sorter
//import org.firstinspires.ftc.teamcode.components.Turret

class Robot(instance: LinearOpMode, events:EventListener, val alliance: Selector.alliance, is_auto:Boolean = false) {
    val motors = Motors(instance.hardwareMap, "FL", "FR", "BL", "BR")
    val otos = Otos(instance.hardwareMap, "OTOS", /*OtosConstant.offset*/)
    val path = PathBuilder(instance, events, motors, otos, true)
//    val sorter = Sorter(instance)
//    val shooter = Turret(instance, alliance, this)
    init{
        if(is_auto){
            path.setDriveConstants(
                DriveConstants.GainSpeed,
                0.0,
                DriveConstants.kD,
                DriveConstants.Tolerance,
                Double.POSITIVE_INFINITY
            )
            path.setStrafeConstants(
                DriveConstants.GainSpeed,
                0.0,
                DriveConstants.kD,
                StrafeConstants.Tolerance,
                Double.POSITIVE_INFINITY
            )
            path.setRotateConstants(
                RotateConstants.GainSpeed,
                0.0,
                RotateConstants.kD,
                0.0,
                RotateConstants.Tolerance,
                Double.POSITIVE_INFINITY
            )
            path.setDistanceTolerance(2.0)
        }
    }
    fun profile_far_shooter(){
        path.segment(Point(0.0, 0.0))
    }
//    fun autoStart(){
//        sorter.setPattern(shooter.ll.detectO())
//    }
//    fun gameEnd(){
//        shooter.ll.stop()
//    }
}
fun readVoltage(): Double {
    val provider = PanelsBattery.provider
    val voltage = provider.batteryVoltage
    return voltage
}
@Configurable
object DriveConstants {
    @JvmField var GainSpeed = 0.05
    @JvmField var AccelerationLimit = 0.7
    @JvmField var kD = 0.006
    @JvmField var Tolerance = 3.0
    @JvmField var Deadband = 0.25
}
@Configurable
object StrafeConstants {
    @JvmField var GainSpeed = 0.086
    @JvmField var AccelerationLimit = 0.8
    @JvmField var kD = 0.0
    @JvmField var Tolerance = 6.0
    @JvmField var Deadband = 0.25
}
@Configurable
object RotateConstants {
    @JvmField var GainSpeed = 1.8
    @JvmField var AccelerationLimit = 0.5
    @JvmField var kD = 0.0
    @JvmField var Tolerance = Math.PI/24
    @JvmField var Deadband = Math.PI/48
}
@Configurable
object TurretPID {
    @JvmField var kP = 0.0
    @JvmField var kI = 0.0
    @JvmField var kD = 0.0
    @JvmField var kF = 0.0
}
@Configurable
object OtosConstant {
    @JvmField var offset = Point(0.0, 0.0)
}

//Hyperion constants
/*@Config
object DriveConstants {
    @JvmField var GainSpeed = 0.05
    @JvmField var AccelerationLimit = 0.7
    @JvmField var kD = 0.006
    @JvmField var Tolerance = 3.0
    @JvmField var Deadband = 0.25
}
@Config
object StrafeConstants {
    @JvmField var GainSpeed = 0.086
    @JvmField var AccelerationLimit = 0.8
    @JvmField var kD = 0.0
    @JvmField var Tolerance = 6.0
    @JvmField var Deadband = 0.25
}
@Config
object RotateConstants {
    @JvmField var GainSpeed = 1.8
    @JvmField var AccelerationLimit = 0.5
    @JvmField var kD = 0.0
    @JvmField var Tolerance = Math.PI/12
    @JvmField var Deadband = Math.PI/24
}
@Config
object OtosConstant {
    @JvmField var offset = Point(0.625, 0.0)
}*/
