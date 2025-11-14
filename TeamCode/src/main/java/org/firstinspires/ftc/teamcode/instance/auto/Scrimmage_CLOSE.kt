package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Events
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Testing

class Scrimmage_CLOSE(private val instance: LinearOpMode) {
    fun run(timer: ElapsedTime, s: Selector, t: Testing){
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        bot.path.start(
            Point(112.0, 100.0, "start").setDeg(90.0),
            s.alliance_name == Selector.alliance.RED
        )
        //set 1
        Testing.shooting.set(true)
        bot.path.segment(
            Point(112.0, 75.0, "shoot").setTolerance(5.0).setDeg(90.0),
            Point(112.0, 72.0).setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //set 2
        bot.path.segment(
            Point(61.0, 93.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(58.0, 113.0).setTolerance(8.0).setDeg(90.0),
            Point(58.0, 110.5).setDeg(60.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(105.0, 76.0, "shoot").setTolerance(5.0).setDeg(90.0),
            Point(108.0, 71.0).setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //set 3
        bot.path.segment(
            Point(36.0, 93.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(33.0, 121.0).setTolerance(8.0).setDeg(90.0),
            Point(33.0, 115.5).setDeg(60.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(104.0, 74.0, "shoot").setTolerance(5.0).setDeg(90.0),
            Point(107.0, 70.0).setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        if(timer.seconds() < 26) {
            //set 4
            bot.path.segment(
                Point(29.0, 93.0, "intake").setDeg(105.0)
            )
            eventListener.states.set(Events.AutoStates.INTAKE_READY)
            bot.motors.setPowerRatio(0.6)
            bot.path.segment(
                Point(25.0, 120.0).setTolerance(8.0).setDeg(105.0),
                Point(25.0, 114.5).setDeg(80.0)
            )
        }
    }
}