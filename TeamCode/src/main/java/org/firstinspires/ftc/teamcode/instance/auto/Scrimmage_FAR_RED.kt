package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Events
import org.firstinspires.ftc.teamcode.components.Testing
import kotlin.math.PI
import kotlin.math.abs

class Scrimmage_FAR_RED(private val instance: LinearOpMode) {
    fun run(time: ElapsedTime, s: Selector, t: Testing){
        t.shooter.power = 0.5
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        s.motors = bot.motors
        //start pose
        bot.path.start(
            Point(8.0, 86.0,"start").setDeg(-36.0),
            s.alliance_name == Selector.alliance.RED
        )
        //Set 1
        bot.path.wait(1500.0)
        bot.path.segment(
            Point(8.0, 86.0,"shoot").setDeg(-36.0),
        )
        while(abs(t.shot_controller.getPositionError(t.velocity())) < 50.0) {
            bot.path.wait(100.0)
        }
        bot.path.wait(750.0)
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }

        //Set 2 middle spike mark
        t.shot_controller.setTarget(757.0)
        bot.path.segment(
            Point(41.0, 100.0, "intake").setDeg(-90.0),
            Point(46.0, 134.0, "intake_ready").setDeg(-90.0).setTolerance(7.0),
            Point(40.0, 130.5, "speed_60").setDeg(-90.0).setTolerance(11.0),
        )
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(24.0, 90.0, "shoot").setTolerance(6.0).setDeg(-40.0),
            Point(13.0, 92.0).setDeg(-40.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while (instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT) {
            bot.path.wait(100.0)
        }
        //Set 3 far spike mark
        t.shot_controller.setTarget(758.0)
        bot.path.segment(
            Point(18.0, 103.0, "intake").setDeg(-45.0),
            Point(27.0, 133.0, "intake_ready").setDeg(-90.0).setTolerance(12.0)
        )

        bot.path.wait(500.0)
            .segment(
                Point(19.0, 132.0).setDeg(-90.0).setTolerance(14.0),
                Point(17.0, 88.0, "shoot").setDeg(-40.0)
            )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while (instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT) {
            bot.path.wait(100.0)
        }
        //Out of shoot zone
        bot.path.segment(
            Point(36.0, 125.0).setTolerance(8.0).setDeg(-90.0),
            Point(36.0, 132.0).setDeg(-90.0).setTolerance(8.0)
        )
        bot.path.end()
    }
}