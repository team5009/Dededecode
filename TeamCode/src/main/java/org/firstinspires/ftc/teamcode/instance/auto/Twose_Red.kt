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
            Point(7.0, 62.0,"start").setDeg(0.0)
        ).segment(
            Point(83.0, 62.0),
            Point(79.0, 62.0).setDeg(-45.0)
        ).wait(
            1000.0
        ).segment(Point(79.0, 62.0,"intake").setDeg(-90.0)
        ).segment(
            Point(79.0, 18.0),
            Point(79.0, 14.0,"stopped")
        )
    }


}