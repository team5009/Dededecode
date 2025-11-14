package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.components.Testing
import kotlin.math.abs

class TeleOp_GamePads (private val instance: LinearOpMode) {

    private val gamepad1 = instance.gamepad1
    private val gamepad2 = instance.gamepad2

    val intake = HyperionMotor(instance.hardwareMap, "CH")
    val shooter = HyperionMotor(instance.hardwareMap, "Shooter")

    val t = Testing(instance)

    //misc var
    var snap = 0L
    var spinup = 0L
    var targ = 0.0

    //Dbounce
    var f_pressed = false
    var f_stop = true
    var is_pushed = false

    fun game_pad_1() {
        //Joshua

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
        }else if(!(gamepad1.circle && gamepad1.triangle && gamepad1.square) && is_pushed){
            t.push_l(0.6)
            t.push_r(0.5)
            is_pushed = false
        }

        //Feeder
        if(gamepad1.cross){
            if(abs(targ - t.lastVelocity) < 15.0) {
                t.lift(0.0)
            }
        } else {
            t.lift(1.0)
        }

        //intake
        if (gamepad1.right_bumper){
            intake.power = -0.6
        }else if(gamepad1.left_bumper){
            intake.power = 1.0
        }else{
            intake.power = 0.0
        }

        //flywheel
        if(gamepad1.dpad_down && !f_pressed && f_stop){
            //start 590
            f_pressed = true
            targ = 590.0
            f_stop = false
//            t.push_r(0.55)
//            t.push_l(0.55)
        }else if(gamepad1.dpad_left && !f_pressed && f_stop){
            //start 630
            f_pressed = true
            targ = 630.0
            f_stop = false
//            t.push_r(0.55)
//            t.push_l(0.55)
        }else if((gamepad1.dpad_down || gamepad1.dpad_left) && !f_pressed && !f_stop){
            //stop shooter
            shooter.power = 0.0
            f_pressed = true
            f_stop = true
        }else if(!gamepad1.dpad_down && f_pressed){
            f_pressed = false
        }
        //shooter running
        if(!f_stop) {
            t.power_mod(targ)
        }
    }

    fun game_pad_2() {

    }
}