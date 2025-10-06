package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.components.My_Color_Sensor

@TeleOp(name = "Main")
class MainTeleOp: LinearOpMode() {
	override fun runOpMode() {
		val motors = Motors(hardwareMap, "FL", "FR", "BL", "BR")
		val intake = HyperionMotor(hardwareMap, "CH")
		val shooter = HyperionMotor(hardwareMap, "Shooter")
		motors.setPowerRatio(1.0)
		val color = My_Color_Sensor(this)

		val timer = ElapsedTime()

		var drive = 0.0

		//dbounce
		var x_pressed = false

		while (opModeInInit()) {

		}
		waitForStart()
		timer.reset()

		while (opModeIsActive()) {
			drive = -gamepad1.left_stick_y.toDouble()
			val strafe = gamepad1.left_stick_x.toDouble()
			val rotate = gamepad1.right_stick_x.toDouble()
			motors.gamepadMove(drive, strafe, rotate)
			if (gamepad1.right_bumper){
				intake.power = 0.95
			}else if(gamepad1.left_bumper){
				intake.power = -0.95
			}else{
				intake.power = 0.0
			}
			if(gamepad1.circle && !x_pressed){
				shooter.power = 1.0
				x_pressed = true
			}else if(gamepad1.circle && !x_pressed){
				shooter.power = 0.0
				x_pressed = true
			} else {
				x_pressed = false
			}
			telemetry.addData("color", color.sensor())
			telemetry.update()
		}

	}
}

