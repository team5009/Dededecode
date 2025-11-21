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
        s.motors = bot.motors
        bot.path.start(
            Point(112.0, 100.0, "start").setDeg(90.0),
            s.alliance_name == Selector.alliance.RED
        )
        //set 1
        Testing.shooting.set(true)
        bot.path.segment(
            Point(113.0, 80.0, "shoot").setTolerance(5.0).setDeg(80.0),
            Point(113.0, 73.0).setDeg(80.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            instance.sleep(100)
        }
        //set 2 close spike mark
        bot.path.segment(
            Point(61.0, 91.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(67.0, 113.0).setTolerance(8.0).setDeg(90.0),
            Point(67.0, 110.5).setDeg(60.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(107.5, 76.0, "shoot").setTolerance(5.0).setDeg(80.0),
            Point(111.0, 71.0).setDeg(80.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            instance.sleep(100)
        }
//        if(s.gate_open == Selector.open_gate.YES){
//            bot.path.segment(
//                Point(46.0, 91.0).setTolerance(4.0).setDeg(90.0),
//                Point(46.0, 110.0)
//            )
//        }
        //set 3 middle spike mark
        bot.path.segment(
            Point(39.0, 89.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(39.0, 121.0).setTolerance(8.0).setDeg(90.0),
            Point(39.0, 119.5).setDeg(60.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(105.0, 74.0, "shoot").setTolerance(5.0).setDeg(80.0),
            Point(108.5, 70.0).setDeg(80.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            instance.sleep(100)
        }
        //set 4 far spike mark
        bot.path.segment(
            Point(17.0, 93.0, "intake").setDeg(105.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(17.0, 120.0).setTolerance(8.0).setDeg(105.0),
            Point(17.0, 114.5).setDeg(80.0)
        )
        bot.path.end()
    }
}