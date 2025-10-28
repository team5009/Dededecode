package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class Selector(private val instance : LinearOpMode){

    var a_pressed = false
    var b_pressed = false
    var d_pad_pressed = false

    var delay = 0L

    val which_alliance = alliance.entries.lastIndex + 1
    var alliance_index = 0
    var alliance_name = alliance.RED

    var selector = 0
    val path_length = paths.entries.lastIndex + 1
    var path_index = 0
    var path_name = paths.FAR

    fun select() {
        if(instance.gamepad1.a && !a_pressed){
            if(selectors.entries[selector] == selectors.PATH_NAME) {
                path_name = paths.entries[path_index]
            }
            if(selectors.entries[selector] == selectors.ALLIANCE){
                alliance_name = alliance.entries[alliance_index]
            }
            a_pressed = true
            selector += 1
        } else if(!instance.gamepad1.a && a_pressed){
            a_pressed = false
        }
        if(instance.gamepad1.b && !b_pressed){
            b_pressed = true
            selector -= 1
        } else if(!instance.gamepad1.b && b_pressed){
            b_pressed = false
        }
    }

    fun scroll() {
        if (selectors.entries[selector] == selectors.ALLIANCE){
            if(instance.gamepad1.dpad_down && !d_pad_pressed){
                d_pad_pressed = true
                alliance_index = (alliance_index + 1) % which_alliance
            } else if(instance.gamepad1.dpad_up && !d_pad_pressed){
                d_pad_pressed = true
                alliance_index = (alliance_index - 1) % which_alliance
            } else if(!instance.gamepad1.dpad_up && !instance.gamepad1.dpad_down && d_pad_pressed) {
                d_pad_pressed = false
            }
        }else if(selectors.entries[selector] == selectors.PATH_NAME){
            if(instance.gamepad1.dpad_down && !d_pad_pressed){
                d_pad_pressed = true
                path_index = (path_index + 1) % path_length
            } else if(instance.gamepad1.dpad_up && !d_pad_pressed){
                d_pad_pressed = true
                path_index = (path_index - 1) % path_length
            } else if(!instance.gamepad1.dpad_up && !instance.gamepad1.dpad_down && d_pad_pressed) {
                d_pad_pressed = false
            }
        }else if(selectors.entries[selector] == selectors.DELAY) {
            if(instance.gamepad1.dpad_down && !d_pad_pressed){
                d_pad_pressed = true
                delay += 500
            } else if(instance.gamepad1.dpad_up && !d_pad_pressed && delay > 0){
                d_pad_pressed = true
                delay -= 500
            } else if(!instance.gamepad1.dpad_up && !instance.gamepad1.dpad_down && d_pad_pressed) {
                d_pad_pressed = false
            }
        }
    }
    enum class alliance {
        RED,
        BLUE
    }
    enum class paths {
        FAR,
        CLOSE
    }
    enum class selectors {
        ALLIANCE,
        PATH_NAME,
        DELAY,
        READY,
        DONE
    }
}