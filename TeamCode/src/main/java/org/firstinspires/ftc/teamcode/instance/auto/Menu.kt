package org.firstinspires.ftc.teamcode.instance.auto

import com.bylazar.telemetry.JoinedTelemetry
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
//import org.firstinspires.ftc.teamcode.components.My_Color_Sensor
import org.firstinspires.ftc.teamcode.components.Selector
import org.firstinspires.ftc.teamcode.components.Testing

@Autonomous(name = "Menu", group = "Autos")
class Menu : LinearOpMode() {
    override fun runOpMode() {
        telemetry = JoinedTelemetry(PanelsTelemetry.ftcTelemetry, telemetry)
        val s = Selector(this)
        val t = Testing(this)
        //val color = My_Color_Sensor(this)
        val timer = ElapsedTime()
        while (opModeInInit() && Selector.selectors.entries[s.selector] != Selector.selectors.DONE) {
            s.select()
            s.scroll()
            telemetry.addData("Which alliance: ", Selector.alliance.entries[s.alliance_index])
            telemetry.addData("Alliance: ", s.alliance_name)
            telemetry.addLine()
            telemetry.addData("Path: ", Selector.paths.entries[s.path_index])
            telemetry.addData("Selected Path: ", s.path_name)
            telemetry.addLine()
            telemetry.addData("Will the gate open? ", Selector.open_gate.entries[s.gate_index])
            telemetry.addData("Is gate open?", s.gate_open)
            telemetry.addLine()
            telemetry.addData("Delay(ms): ", s.delay)
            telemetry.addLine()
            telemetry.addData("Selector: ", Selector.selectors.entries[s.selector])
            telemetry.update()
        }
        t.init_auto()
        waitForStart()
        timer.reset()
        sleep(s.delay)

        if (s.path_name == Selector.paths.FAR_RED) {
            Scrimmage_FAR_RED(this).run(timer, s, t)
        } else if (s.path_name == Selector.paths.FAR_BLUE) {
            FAR_BLUE(this).run(timer, s, t)
        } else if(s.path_name == Selector.paths.CLOSE_RED){
            RED_CLOSE(this).run(timer, s, t)
        } else if(s.path_name == Selector.paths.CLOSE_BLUE){
            Scrimmage_CLOSE(this).run(timer, s, t)
        }
    }
}
