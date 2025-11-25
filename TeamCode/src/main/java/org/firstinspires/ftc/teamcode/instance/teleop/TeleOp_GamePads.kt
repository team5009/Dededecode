package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.components.Testing
import kotlin.math.abs

class TeleOp_GamePads (private val instance: LinearOpMode) {

    private val gamepad1 = instance.gamepad1
    private val gamepad2 = instance.gamepad2


    val t = Testing(instance)

    //misc var
    var snap = 0L
    var spinup = 0L
    var targ = 590.0

    //Dbounce
    var f_pressed = false
    var f_stop = true
    var is_pushed = false

    var was_broken = false
    var was_lifting = false

    var is_solo = true

    fun game_pad_1() {
        //Joshua

        if(is_solo){
            //Transfer
            if(gamepad1.circle && !is_pushed){
                //feed the feeder right
                t.push_r(0.9)
                is_pushed = true
            }else if(gamepad1.square && !is_pushed){
                //feed the feeder left
                t.push_l(0.2)
                is_pushed = true
            }else if(gamepad1.triangle && !is_pushed){
                t.push_r(0.8)
                t.push_l(0.3)
                is_pushed = true
            }else if(!(gamepad1.circle || gamepad1.triangle || gamepad1.square) && is_pushed){
                t.push_l(0.6)
                t.push_r(0.5)
                is_pushed = false
            }

            //Feeder
            if(gamepad1.cross){
                if(abs(targ - t.lastVelocity) < 15.0 && !was_lifting) {
                    t.lift(0.0)
                    was_lifting = true
                }else if(was_lifting && !was_broken && !t.breakbeam.state){
                    was_broken = true
                }else if(was_lifting && was_broken && t.breakbeam.state){
                    t.lift(1.0)
                }
            } else {
                t.lift(1.0)
                was_lifting = false
                was_broken = false
            }

            //flywheel
            if (gamepad1.dpad_down && !f_pressed && f_stop) {
                t.shooter.power = 0.5
                f_pressed = true
                f_stop = false
                spinup = -1L
                snap = System.currentTimeMillis()
            } else if (gamepad1.dpad_down && !f_pressed && !f_stop) {
                t.shooter.power = 0.0
                f_pressed = true
                f_stop = true
            } else if (!gamepad1.dpad_down && f_pressed) {
                f_pressed = false
            }
            if (t.rpm() >= 4000.0 && spinup < 0) {
                spinup = System.currentTimeMillis() - snap
            }
            if (!f_stop) {
                t.power_mod(targ)
                instance.telemetry.addLine("shooting")
            }
        }

        //intake
        if (gamepad1.right_bumper){
            t.intake.power = -0.6
        }else if(gamepad1.left_bumper){
            t.intake.power = 1.0
        }else{
            t.intake.power = 0.0
        }

    }

    fun game_pad_2() {
        //Driver 2
        if(is_solo && (gamepad2.dpad_down || gamepad2.square || gamepad2.triangle || gamepad2.circle || gamepad2.cross)){
            is_solo = false
        }
        if(!is_solo){

            //flywheel
            if(gamepad2.dpad_down && !f_pressed && f_stop){
                t.shooter.power = 0.5
                f_pressed = true
                f_stop = false
                spinup = -1L
                snap = System.currentTimeMillis()
            }else if(gamepad2.dpad_down && !f_pressed && !f_stop){
                t.shooter.power = 0.0
                f_pressed = true
                f_stop = true
            }else if(!gamepad2.dpad_down && f_pressed){
                f_pressed = false
            }
            if(t.rpm() >= 4000.0 && spinup < 0){
                spinup = System.currentTimeMillis() - snap
            }
            if(!f_stop) {
                t.power_mod(targ)
                instance.telemetry.addLine("shooting")
            }

            //Transfer
            if(gamepad2.circle && !is_pushed){
                //feed the feeder right
                t.push_r(0.9)
                is_pushed = true
            }else if(gamepad2.square && !is_pushed){
                //feed the feeder left
                t.push_l(0.2)
                is_pushed = true
            }else if(gamepad2.triangle && !is_pushed){
                t.push_r(0.8)
                t.push_l(0.3)
                is_pushed = true
            }else if(!(gamepad2.circle || gamepad2.triangle || gamepad2.square) && is_pushed){
                t.push_l(0.7)
                t.push_r(0.4)
                is_pushed = false
            }

            //Feeder
            if(gamepad2.cross){
                if(abs(targ - t.lastVelocity) < 15.0 && !was_lifting) {
                    t.lift(0.0)
                    was_lifting = true
                }else if(was_lifting && !was_broken && !t.breakbeam.state){
                    was_broken = true
                }else if(was_lifting && was_broken && t.breakbeam.state){
                    t.lift(1.0)
                }
            } else {
                t.lift(1.0)
                was_lifting = false
                was_broken = false
            }
        }
    }
}