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
        bot.path.start(
            Point(8.0, 83.0,"start").setDeg(0.0),
            s.alliance_name == Selector.alliance.RED
        )
        Testing.shooting.set(true)
        bot.path.segment(
            Point(65.0, 83.0,"fly"),
            Point(83.0, 82.0).setTolerance(5.0),
            Point(80.0, 82.0).setDeg(45.0),
        )
        eventListener.states.set(Events.AutoStates.READY_SHOOT)
        while(instance.opModeIsActive() && eventListener.states.get() != Events.AutoStates.FINISH_SHOOT){
            bot.path.wait(100.0)
        }
        Testing.shooting.set(false)
        bot.path.segment(
            Point(81.0, 83.0, "intake").setDeg(90.0)
        )
        eventListener.states.set(Events.AutoStates.INTAKE_READY)
        bot.path.segment(
            Point(78.0, 93.0).setTolerance(5.0),
            Point(78.0, 90.0)
        )
        bot.path.wait(3000.0, "f_stop")
        .segment(
            Point(68.0,83.0).setTolerance(5.0),
            Point(65.0, 80.0).setDeg(0.0)
        )
    }
}