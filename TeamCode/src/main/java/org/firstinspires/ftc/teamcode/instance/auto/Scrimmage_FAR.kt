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
        s.motors = bot.motors
        //start pose
        bot.path.start(
            Point(8.0, 86.0,"start").setDeg(0.0),
            s.alliance_name == Selector.alliance.RED
        )
        //Set 1
        bot.path.segment(
            Point(81.0, 93.0,"shoot").setTolerance(5.0),
            Point(78.0, 91.0).setDeg(40.0),
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //Set 2 middle spike mark
        if(s.gate_open == Selector.open_gate.YES){
            //gate
            bot.path.segment(
                Point(42.0, 104.0, "intake").setDeg(60.0)
            )
            eventListener.states.set(Events.AutoStates.INTAKE_READY)
            bot.path.segment(
                Point(70.0, 125.0).setTolerance(8.0).setDeg(80.0),
                Point(64.0, 130.5).setDeg(95.0).setTolerance(5.0),
                Point(61.0, 118.5).setDeg(95.0).setTolerance(5.0),
                Point(64.0, 130.5).setDeg(95.0).setTolerance(5.0),
                Point(61.0, 128.5).setDeg(95.0)
            )
            instance.sleep(500)
        }else{
            bot.path.segment(
                Point(49.0, 98.0, "intake").setDeg(90.0)
            )
            eventListener.states.set(Events.AutoStates.INTAKE_READY)
            bot.motors.setPowerRatio(0.8)
            bot.path.segment(
                Point(59.0, 124.0).setTolerance(8.0).setDeg(90.0),
                Point(59.0, 130.5).setDeg(90.0)
            )
        }
//        Testing.shooting.set(true)
//        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(81.0, 85.0, "shoot").setDeg(42.0).setTolerance(5.0),
            Point(78.0, 89.0).setDeg(42.0),
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        eventListener.targ.set(550.0)
        //Set 3 far spike mark
        bot.path.segment(
            Point(24.0, 100.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(42.0, 127.0).setDeg(90.0).setTolerance(8.0),
            Point(43.0, 129.5).setDeg(90.0).setTolerance(7.0),
            Point(43.0, 112.0).setDeg(90.0)
        )
        bot.motors.setPowerRatio(1.0)
//        bot.path.segment(
//            Point(105.0, 91.0, "shoot").setDeg(6.0).setTolerance(5.0),
//            Point(102.0, 88.0).setDeg(46.0)
//        )
        bot.path.segment(
            Point(119.0, 83.0, "shoot").setTolerance(5.0).setDeg(80.0),
            Point(121.0, 78.0).setDeg(80.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        bot.path.segment(
            Point(119.0, 78.0).setDeg(90.0)
        )
        bot.path.end()
    }
}