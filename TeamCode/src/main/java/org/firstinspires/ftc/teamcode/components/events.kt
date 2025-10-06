package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.delay

class events(instance:LinearOpMode, private val s : Selector, t: testing) {
    val listener = EventListener()
    val color = My_Color_Sensor(instance)
    val time_out = ElapsedTime()
    init {
        val alliance = if(s.alliance_name == Selector.alliance.RED){
            "RED"
        }else{
            "BLUE"
        }
        listener.addListener("start"){
            t.shooter.power = 1.0
            delay(4000)

            t.shooter.power = 0.0
            "stop"
        }
        listener.addListener("intake"){
            t.intake.power = -1.0
            delay(10000)
            t.intake.power = 0.0
            "stopped"
        }
    }
}