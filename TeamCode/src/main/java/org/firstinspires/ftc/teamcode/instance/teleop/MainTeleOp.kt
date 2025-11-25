package org.firstinspires.ftc.teamcode.instances.teleop

import ca.helios5009.hyperion.core.Motors
import ca.helios5009.hyperion.hardware.HyperionMotor
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.components.Limelight
import org.firstinspires.ftc.teamcode.components.Selector
//import org.firstinspires.ftc.teamcode.components.My_Color_Sensor
import org.firstinspires.ftc.teamcode.components.Testing
import kotlin.math.abs

@TeleOp(name = "Main")
class MainTeleOp: LinearOpMode() {
	override fun runOpMode() {
		val motors = Motors(hardwareMap, "FL", "FR", "BL", "BR")
		val intake = HyperionMotor(hardwareMap, "CH")
		val s = Selector(this)
		val ll = Limelight(this.hardwareMap, s.alliance_name)
		val shooter = HyperionMotor(hardwareMap, "Shooter")
		val t = Testing(this)
		motors.setPowerRatio(1.0)

		//val color = My_Color_Sensor(this)

		val timer = ElapsedTime()

		var drive = 0.0

		val controls = TeleOp_GamePads(this)

		ll.llstart()


		while (opModeInInit()) {
		}
		waitForStart()
		timer.reset()

		CoroutineScope(Dispatchers.Default).launch {
			while(opModeIsActive()) {
				controls.game_pad_1()
			}
		}
		CoroutineScope(Dispatchers.Default).launch {
			while(opModeIsActive()) {
				controls.game_pad_2()
			}
		}

		while (opModeIsActive()) {
			drive = -gamepad1.left_stick_y.toDouble()
			val strafe = gamepad1.left_stick_x.toDouble()
			val rotate = gamepad1.right_stick_x.toDouble()
			motors.gamepadMove(drive, strafe, rotate)
			telemetry.addData("speed", t.velocity())
			telemetry.addLine()
			telemetry.addData("rpm", t.rpm())
			telemetry.addLine()
			telemetry.addData("timer", controls.spinup)
			telemetry.addLine()
			telemetry.addData("Goal ID: ", ll.detectG())
			telemetry.addData("Fiducial results: ", ll.detectO())
			telemetry.update()
		}

	}
}

