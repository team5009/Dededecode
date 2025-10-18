package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.events
import org.firstinspires.ftc.teamcode.components.testing

class Twose_Red(private val instance: LinearOpMode) {
    fun run(time: ElapsedTime, s: Selector, t: testing){
        val eventListener = events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, Selector.alliance.RED, true)
        bot.path.start(
            Point(8.0, 83.0,).setDeg(0.0),
            s.alliance_name == Selector.alliance.RED
        )
        bot.motors.setPowerRatio(0.8)
        bot.path.segment(
            Point(65.0, 83.0),
            Point(73.0, 83.0).setTolerance(5.0),
            Point(70.0, 83.0).setDeg(45.0),
        )
//        eventListener.states.set(events.AutoStates.READY_SHOOT)
//        while(instance.opModeIsActive() && eventListener.states.get() != events.AutoStates.READY_SHOOT){
//            bot.path.wait(100.0)
//        }
        bot.motors.setPowerRatio(0.3)
        bot.path.segment(
            Point(81.0, 83.0).setDeg(90.0)
        )
        .segment(
            Point(78.0, 93.0).setTolerance(5.0),
            Point(78.0, 90.0)
        )
    }
}