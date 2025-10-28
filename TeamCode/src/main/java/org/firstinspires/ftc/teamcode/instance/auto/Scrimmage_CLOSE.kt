package org.firstinspires.ftc.teamcode.instance.auto

import ca.helios5009.hyperion.pathing.Point
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.components.Events
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Testing

class Scrimmage_CLOSE(private val instance: LinearOpMode) {
    fun run(time: ElapsedTime, s: Selector, t: Testing){
        val eventListener = Events(instance, s, t)
        val bot = Robot(instance, eventListener.listener, s.alliance_name, true)
        bot.path.start(
            Point(125.0, 125.0).setDeg(45.0),
            s.alliance_name == Selector.alliance.RED
        )
        .segment(
            Point(87.0, 87.0).setTolerance(5.0),
            Point(84.0, 84.0)
        )
        .segment(
            Point(84.0, 84.0).setDeg(90.0)
        )
        bot.path.wait(3000.0, "")
        bot.path.segment(
            Point(84.0, 123.0).setTolerance(5.0),
            Point(84.0, 120.0)
        )
    }
}