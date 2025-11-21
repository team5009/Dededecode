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
            instance.sleep(100)
        }
        //Set 2
        bot.path.segment(
            Point(42.0, 104.0, "intake").setDeg(60.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        if(s.gate_open == Selector.open_gate.YES){
            bot.path.segment(
                Point(70.0, 125.0).setTolerance(8.0).setDeg(80.0),
                Point(64.0, 130.5).setDeg(80.0).setTolerance(5.0),
                Point(61.0, 128.5).setDeg(80.0)
            )
            instance.sleep(1200)
        }else{
            bot.path.segment(
                Point(56.0, 125.0).setTolerance(8.0).setDeg(45.0),
                Point(54.0, 119.5).setDeg(45.0)
            )
        }
//        Testing.shooting.set(true)
//        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(81.0, 85.0, "shoot").setTolerance(5.0),
            Point(78.0, 89.0).setDeg(45.0),
        )
        eventListener.targ.set(560.0)
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            instance.sleep(100)
        }
        //Set 3
        bot.path.segment(
            Point(24.0, 100.0, "intake").setDeg(45.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)

        bot.path.segment(
            Point(29.0, 128.0).setTolerance(8.0).setDeg(60.0),
            Point(31.0, 112.5).setDeg(60.0),
            Point(108.0, 93.0, "shoot").setDeg(60.0).setTolerance(5.0),
            Point(105.0, 90.0).setDeg(60.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            instance.sleep(100)
        }
        bot.path.end()
    }
}