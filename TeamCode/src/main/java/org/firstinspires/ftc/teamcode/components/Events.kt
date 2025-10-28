package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs

class Events(private val instance:LinearOpMode, private val s : Selector, private val t: Testing) {
    val listener = EventListener()
    val states = AtomicReference(AutoStates.START)
    //val color = My_Color_Sensor(instance)
    val targ = 570.0
    val time_out = ElapsedTime()
    init {
        val alliance = if(s.alliance_name == Selector.alliance.RED){
            "RED"
        }else{
            "BLUE"
        }
        listener.addListener("start"){
            while(instance.opModeIsActive()){
               t.power_mod(570.0)
            }
            "stop"
        }
        listener.addListener("shoot"){
            //t.shooter.power = 0.8
            while(instance.opModeIsActive() && states.get() != Events.AutoStates.READY_SHOOT){
                delay(100)
            }
            shoot()
            t.push_l(0.2)
            delay(800)
            t.push_l(0.6)
            shoot()
            t.push_r(0.9)
            t.push_l(0.2)
            delay(600)
            t.push_r(0.5)
            t.push_l(0.6)
            shoot()
            states.set(AutoStates.FINISH_SHOOT)
            t.shooter.power = 0.0
            "stopShooting"
        }
        listener.addListener("intake"){
            while (instance.opModeIsActive() && states.get() != AutoStates.INTAKE_READY){
                delay(100)
            }
            t.intake.power = -1.0
            delay(7000)
            states.set(AutoStates.INTAKED)
            t.intake.power = 0.0
            "stopped"
        }
    }
    suspend fun shoot(){
        val time_out = ElapsedTime()
        time_out.reset()
        while(instance.opModeIsActive() && time_out.seconds() < 4.0){
            if( abs(t.lastVelocity) < 7.0){
                break
            }
            delay(10)
        }
        t.lift(0.0)
        delay(600)
        t.lift(1.0)
    }
    enum class AutoStates {
        START,
        READY_SHOOT,
        FINISH_SHOOT,
        INTAKE_READY,
        INTAKED
    }
}