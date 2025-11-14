package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Events
import org.firstinspires.ftc.teamcode.components.Testing

class Scrimmage_FAR(private val instance: LinearOpMode) {
    fun run(time: ElapsedTime, s: Selector, t: Testing){
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        //start pose
        bot.path.start(
            Point(8.0, 83.0,"start").setDeg(0.0),
            s.alliance_name == Selector.alliance.RED
        )
        Testing.shooting.set(true)
        bot.path.segment(
            Point(65.0, 83.0,"shoot"),
            Point(85.0, 82.0).setTolerance(5.0),
            Point(82.0, 82.0).setDeg(45.0),
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        bot.path.segment(
            Point(70.0, 109.0, "intake").setDeg(60.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(80.0, 125.0).setTolerance(8.0).setDeg(60.0),
            Point(78.0, 119.5).setDeg(60.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(87.0, 82.0, "shoot").setTolerance(5.0),
            Point(84.0, 82.0).setDeg(45.0),
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        bot.path.segment(
            Point(24.0, 100.0, "intake").setDeg(60.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(29.0, 128.0).setTolerance(8.0).setDeg(60.0),
            Point(31.0, 112.5).setDeg(60.0)
        )
        .segment(
            Point(32.0, 127.0).setDeg(75.0).setTolerance(3.0)
        )
    }
}