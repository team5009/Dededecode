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
    var far_pressed = false
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
                if(abs(t.shot_controller.getPositionError(t.velocity())) < 20.0 && !was_lifting) {
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
        //flywheel
        if (gamepad1.cross && !f_pressed && f_stop) {
            t.shot_controller.setTarget(610.0)
            t.hood(0.2)
            f_pressed = true
            f_stop = false
        }else if(gamepad1.cross && !f_pressed && !f_stop && !far_pressed){
            t.shot_controller.setTarget(780.0)
            t.hood(1.0)
            f_pressed = true
            f_stop = false
            far_pressed = true
        }else if (gamepad1.cross && !f_pressed && !f_stop) {
            t.shooter.power = 0.0
            f_pressed = true
            f_stop = true
            far_pressed = false
        } else if (!gamepad1.cross && f_pressed) {
            f_pressed = false
        }
        if (!f_stop) {
            t.power_mod()
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
                if(abs(t.shot_controller.getPositionError(t.velocity())) < 20.0 && !was_lifting) {
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