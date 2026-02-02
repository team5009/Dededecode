package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Events
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Testing

class RED_CLOSE(private val instance: LinearOpMode) {
    fun run(timer: ElapsedTime, s: Selector, t: Testing){
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        s.motors = bot.motors
        bot.path.start(
            Point(132.0, 132.0, "start").setDeg(-90.0),
            s.alliance_name == Selector.alliance.RED
        )

        //set 1
        bot.path.wait(500.0)
        Testing.shooting.set(true)
        bot.path.segment(
            Point(131.0, 101.0, "shoot_close_r").setTolerance(5.0).setDeg(-90.0),
            Point(131.0, 104.0).setDeg(-90.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }

        //set 2 close spike mark
        bot.path.segment(
            Point(88.0, 108.0, "intake").setDeg(-90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(74.0, 140.0).setTolerance(11.0).setDeg(-90.0),
            Point(74.0, 140.5).setDeg(-90.0)
        )
        t.shot_controller.setTarget(595.0)
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(121.5, 102.0, "shoot_close_r").setTolerance(5.0).setDeg(-75.0),
            Point(124.0, 105.0).setDeg(-75.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //set 3 middle spike mark
        bot.path.segment(
            Point(63.0, 116.0, "intake").setDeg(-90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.motors.setPowerRatio(0.6)
        bot.path.segment(
            Point(51.0, 145.0).setTolerance(10.0).setDeg(-90.0),
            Point(51.0, 145.5).setDeg(-90.0)
        )
        Testing.shooting.set(true)
        bot.motors.setPowerRatio(1.0)
        bot.path.segment(
            Point(119.0, 102.0, "shoot_close_r").setTolerance(5.0).setDeg(-75.0),
            Point(124.5, 104.0).setDeg(-+75.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
//        //set 4 far spike mark
//        bot.path.segment(
//            Point(17.0, 93.0, "intake").setDeg(105.0)
//        )
//        eventListener.states.set(Events.AutoStates.INTAKE_READY)
//        bot.motors.setPowerRatio(0.6)
//        bot.path.segment(
//            Point(11.0, 110.0).setTolerance(8.0).setDeg(105.0),
//            Point(11.0, 104.5).setDeg(80.0)
//        )
        bot.path.end()
    }
}