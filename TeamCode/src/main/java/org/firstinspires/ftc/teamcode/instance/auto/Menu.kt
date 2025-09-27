package org.firstinspires.ftc.teamcode.instance.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.components.My_Color_Sensor
import org.firstinspires.ftc.teamcode.components.Selector

@Autonomous(name = "Menu", group = "Autos")
class Menu : LinearOpMode() {
    override fun runOpMode() {
        //val dashboard = FtcDashboard.getInstance()
        //telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)
        val s = Selector(this)
        val color = My_Color_Sensor(this)
        val timer = ElapsedTime()
        while(opModeInInit() && Selector.selectors.entries[s.selector] != Selector.selectors.DONE) {
            s.select()
            s.scroll()
            telemetry.addData("Which alliance: ", Selector.alliance.entries[s.alliance_index])
            telemetry.addData("Alliance: ", s.alliance_name)
            telemetry.addLine()
            telemetry.addData("Path: ", Selector.paths.entries[s.path_index])
            telemetry.addData("Selected Path: ", s.path_name)
            telemetry.addLine()
            telemetry.addData("Delay(ms): ", s.delay)
            telemetry.addLine()
            telemetry.addData("Selector: ", Selector.selectors.entries[s.selector])
            telemetry.update()
        }

        waitForStart()
        timer.reset()
        sleep(s.delay)

        if(s.path_name == Selector.paths.RED_FAR) {
            /*path name*/ //(this, arm).run(timer, s)
        } else if(s.path_name == Selector.paths.RED_CLOSE) {
            /*path name*/ //(this, arm).run(timer, s)
        }else if(s.path_name == Selector.paths.BLUE_FAR){
            /*path name*/ //(this, arm).run(timer, s)
        }else if(s.path_name == Selector.paths.BLUE_CLOSE){
            /*path name*/ //(this, arm).run(timer, s)
        }
    }

}
