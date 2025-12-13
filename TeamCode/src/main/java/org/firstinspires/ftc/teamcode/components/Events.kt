package org.firstinspires.ftc.teamcode.components

import ca.helios5009.hyperion.misc.events.EventListener
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import com.sun.tools.javac.sym.CreateSymbols
import kotlinx.coroutines.delay
import org.firstinspires.ftc.teamcode.Robot
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs

class Events(private val instance:LinearOpMode, private val s : Selector, private val t: Testing) {
    val listener = EventListener()
    val states = AtomicReference(AutoStates.START)
    //val color = My_Color_Sensor(instance)
    var was_broken = false
    val targ = if(s.path_name == Selector.paths.FAR){
        t.shot_controller.setTarget(840.0)
        t.hood(1.0)
    }else {
        t.shot_controller.setTarget(660.0)
        t.hood(0.2)
    }
    val time_out = ElapsedTime()
    init {
        val alliance = if(s.alliance_name == Selector.alliance.RED){
            "RED"
        }else{
            "BLUE"
        }
        listener.addListener("start"){
            Testing.shooting.set(true)
            t.shooter.power = 0.8
            delay(300)
            while(instance.opModeIsActive()){
                if(Testing.shooting.get()){
                    t.power_mod()
                }else{
                    t.shooter.power = 0.5
                }
            }
            "stop"
        }
        listener.addListener("shoot"){
            t.push_r(0.55)
            while(instance.opModeIsActive() && states.get() != Events.AutoStates.READY_SHOOT){
                delay(100)
            }
            shoot()
            t.shot_controller.setTarget(t.shot_controller.targetPosition - 54.0)
            t.push_r(0.45)
            delay(100)
            t.push_r(0.8)
            delay(600)
            shoot()
            t.push_r(0.45)
            delay(50)
            t.push_r(0.8)
            t.push_l(0.3)
            delay(250)
            t.push_r(0.45)
            t.push_l(0.65)
            shoot()
            t.shot_controller.setTarget(t.shot_controller.targetPosition + 10.0)
            states.set(AutoStates.FINISH_SHOOT)
            Testing.shooting.set(false)
            "stopShooting"
        }
        listener.addListener("intake"){
            t.intake.power = 1.0
            while (instance.opModeIsActive() && states.get() != AutoStates.INTAKE_READY){
                delay(100)
            }
            s.motors.setPowerRatio(0.6)
            delay(800)
            states.set(AutoStates.INTAKED)
            s.motors.setPowerRatio(1.0)
            delay(400)
            Testing.shooting.set(true)
            delay(1100)
            t.intake.power = 0.0
            "stopped"
        }
        listener.addListener("intake_ready"){
            states.set(AutoStates.INTAKE_READY)
            "set"
        }
        listener.addListener("final_start"){
            delay(300)
            states.set(AutoStates.READY_SHOOT)
            ""
        }
        listener.addListener("gate"){
            while(instance.opModeIsActive() && states.get() != Events.AutoStates.GATE_READY){
                delay(100)
            }
            s.motors.setPowerRatio(1.8)
            s.motors.gamepadMove(1.0,0.0, 0.0)
            delay(1000)
            s.motors.setPowerRatio(1.0)
            s.motors.gamepadMove(0.0,0.0, 0.0)
            states.set(AutoStates.INTAKED)
            ""
        }
    }
    suspend fun shoot(){
        val time_out = ElapsedTime()
        time_out.reset()
        while(instance.opModeIsActive() && time_out.seconds() < 0.5){
            if(abs(t.shot_controller.targetPosition - t.lastVelocity) < 17.0){
                break
            }
            delay(10)
        }
        t.lift(0.0)
        time_out.reset()
        while(instance.opModeIsActive()){
            if(!was_broken && !t.breakbeam.state){
                time_out.reset()
                was_broken = true
            }else if(was_broken && t.breakbeam.state && time_out.seconds() > 0.2){
                was_broken = false
                break
            }
            if(time_out.seconds() > 0.6){
                break
            }
        }
        t.lift(1.0)
    }
    enum class AutoStates {
        START,
        READY_SHOOT,
        FINISH_SHOOT,
        GATE_READY,
        INTAKE_READY,
        INTAKED
    }

}