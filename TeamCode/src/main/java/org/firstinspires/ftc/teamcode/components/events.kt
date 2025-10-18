package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicReference

class events(instance:LinearOpMode, private val s : Selector, t: testing) {
    val listener = EventListener()
    val states = AtomicReference(AutoStates.START)
    val color = My_Color_Sensor(instance)
    val time_out = ElapsedTime()
    init {
        val alliance = if(s.alliance_name == Selector.alliance.RED){
            "RED"
        }else{
            "BLUE"
        }
        listener.addListener("start"){
            t.shooter.power = 0.8
            delay(2000)
            while(instance.opModeIsActive() && states.get() != events.AutoStates.READY_SHOOT){
                delay(100)
            }
            t.lift(0.0)
            delay(600)
            t.lift(1.0)
            delay(500)
            t.push_l(0.5)
            delay(600)
            t.push_l(0.1)
            delay(500)
            t.lift(0.0)


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
    enum class AutoStates {
        START,
        READY_SHOOT,
        FINISH_SHOOT,
    }
}