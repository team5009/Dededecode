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

class Scrimmage_FAR(private val instance: LinearOpMode) {
    fun run(time: ElapsedTime, s: Selector, t: Testing){
        t.shooter.power = 0.5
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        s.motors = bot.motors
        //start pose
        bot.path.start(
            Point(8.0, 86.0,"start").setDeg(36.0),
            s.alliance_name == Selector.alliance.RED
        )
        //Set 1
        bot.path.segment(
            Point(15.0, 90.0,"shoot").setDeg(36.0),
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
        t.shot_controller.setTarget(810.0)
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
                Point(35.0, 100.0, "intake").setDeg(50.0),
                Point(39.0, 137.0, "intake_ready").setDeg(90.0).setTolerance(10.0),
                Point(40.0, 130.5).setDeg(90.0).setTolerance(13.0),
                Point(35.0, 85.0, "shoot").setTolerance(6.0).setDeg(50.0),
                Point(18.0, 86.0).setDeg(40.0)
            )

        }
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //Set 3 far spike mark
        t.shot_controller.setTarget(790.0)
        bot.path.segment(
            Point(15.0, 95.0, "intake").setDeg(50.0),
            Point(20.0, 139.0,"intake_ready").setDeg(90.0).setTolerance(12.0),
            Point(21.0, 142.0).setDeg(90.0).setTolerance(14.0),
            Point(18.0, 84.0, "shoot").setDeg(40.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        //Set 4 close spike mark
        Testing.shooting.set(true)
        t.shot_controller.setTarget(630.0)
        t.hood(0.05)
        bot.path.segment(
            Point(70.0, 111.0, "intake").setTolerance(8.0).setDeg(90.0),
            Point(62.0, 136.0, "intake_ready").setDeg(90.0).setTolerance(8.0),
            Point(67.0, 136.0, "shoot").setDeg(75.0).setTolerance(7.0),
            Point(99.0, 100.0, "final_start").setDeg(70.0)
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        bot.path.end()
    }
}