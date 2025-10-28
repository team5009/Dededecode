package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
//import org.firstinspires.ftc.teamcode.components.My_Color_Sensor
import org.firstinspires.ftc.teamcode.components.Testing
import kotlin.math.abs

@TeleOp(name = "Main")
class MainTeleOp: LinearOpMode() {
	override fun runOpMode() {
		val motors = Motors(hardwareMap, "FL", "FR", "BL", "BR")
		val intake = HyperionMotor(hardwareMap, "CH")
		val shooter = HyperionMotor(hardwareMap, "Shooter")
		val t = Testing(this)
		motors.setPowerRatio(1.0)

		//val color = My_Color_Sensor(this)

		val timer = ElapsedTime()

		var drive = 0.0
		var fly = 0.0
		var snap = 0L
		var spinup = 0L
		val targ = 570.0

		//dbounce
		var f_pressed = false
		var f_stop = true

		while (opModeInInit()) {

		}
		waitForStart()
		timer.reset()

		while (opModeIsActive()) {
			drive = -gamepad1.left_stick_y.toDouble()
			val strafe = gamepad1.left_stick_x.toDouble()
			val rotate = gamepad1.right_stick_x.toDouble()
			motors.gamepadMove(drive, strafe, rotate)
			//flywheel
			if(gamepad1.dpad_down && !f_pressed && f_stop){
				shooter.power = 0.5
				f_pressed = true
				f_stop = false
				spinup = -1L
				snap = System.currentTimeMillis()
			}else if(gamepad1.dpad_down && !f_pressed && !f_stop){
				shooter.power = 0.0
				f_pressed = true
				f_stop = true
			}else if(!gamepad1.dpad_down && f_pressed){
				f_pressed = false
			}
			if(t.rpm() >= 4000.0 && spinup < 0){
				spinup = System.currentTimeMillis() - snap
			}
			if(!f_stop) {
				t.power_mod(targ)
			}
			//intake
			if (gamepad1.right_bumper){
				intake.power = 0.95
			}else if(gamepad1.left_bumper){
				intake.power = -0.95
			}else{
				intake.power = 0.0
			}
			//feeder
			if(gamepad1.cross){
				if(abs(t.lastVelocity) < 7.0) {
					t.lift(0.0)
				}
			} else {
				t.lift(1.0)
			}
			if(gamepad1.circle){
			//feed the feeder right
				t.push_r(0.9)
			}else if(gamepad1.square){
			//feed the feeder left
				t.push_l(0.2)
			}else if(gamepad1.triangle){
				t.push_r(0.8)
				t.push_l(0.3)
			}else {
				t.push_l(0.6)
				t.push_r(0.5)
			}
			telemetry.addData("speed", t.velocity())
			telemetry.addLine()
			telemetry.addData("rpm", t.rpm())
			telemetry.addLine()
			telemetry.addData("timer", spinup)
			telemetry.addLine()
//			telemetry.addData("color", color.sensor())
			telemetry.update()
		}

	}
}

