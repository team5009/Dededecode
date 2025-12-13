package org.firstinspires.ftc.teamcode.instance.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Shooter_Controller;

@TeleOp(name="Shoots")

public class ShooterTest extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();
    DcMotorEx shooter = null;
    Servo hood = null;
    double last_time = 0.0;
    double delta_time = 0.0;
    double last_velocity = 0.0;
    int last_ticks = 0;
    double convert = (4.0 * Math.PI * 1.6) / (28.0);
    double spin_time = 0.0;
    boolean was_tolerance = false;
    double targ = 0.0;
    DigitalChannel breakbeam = null;
    Servo flapper = null;

    @Override
    public void runOpMode() {
        boolean debounce = false;

        shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        hood = hardwareMap.get(Servo.class, "Hood");
        hood.setPosition(0.5);

        breakbeam = hardwareMap.get(DigitalChannel.class, "bb1");
        breakbeam.setMode(DigitalChannel.Mode.INPUT);

        flapper = hardwareMap.get(Servo.class, "Feeder");
        flapper.setPosition(1.0);

        powermod.setTarget(targ);

        boolean was_broken = false;
        boolean was_lifting = false;
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        timer.reset();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            power_mod();
            //shooter.setPower(0.2);
            if(breakbeam.getState()) {
                telemetry.addLine("Breakbeam: NOT BROKEN");
            } else {
                telemetry.addLine("Breakbeam: BROKEN");
            }

            if(gamepad1.x){
                if(!was_lifting) {
                    flapper.setPosition(0.0);
                    was_lifting = true;
                /*} else if (!was_broken && !breakbeam.getState()) {
                    was_broken = true;
                } else if(breakbeam.getState() && was_broken) {
                    flapper.setPosition(1.0);*/
                }
            } else {
                flapper.setPosition(1.0);
                was_lifting = false;
                was_broken = false;
            }

            if(gamepad1.dpad_down && !debounce){
                targ -= 10;
                powermod.setTarget(targ);
                debounce = true;
            } else if (gamepad1.dpad_up && !debounce) {
                targ += 30;
                powermod.setTarget(targ);
                debounce = true;
            } else if (gamepad1.dpad_left && !debounce) {
                hood.setPosition(hood.getPosition() + 0.1);
                debounce = true;
            } else if (gamepad1.dpad_right && !debounce) {
                hood.setPosition(hood.getPosition() - 0.1);
                debounce = true;
            } else if (!gamepad1.dpad_up || !gamepad1.dpad_down
                    || !gamepad1.dpad_left || !gamepad1.dpad_right && debounce) {
                debounce = false;
            }

            telemetry.addData("Power: ", shooter.getPower());
            //telemetry.addData("ticks: ", shooter.getCurrentPosition());
            telemetry.addData("Target_v: ", targ);
            telemetry.addData("Velocity: ", last_velocity);
            telemetry.addData("hood: ", hood.getPosition());
            telemetry.addData("spin time", spin_time);
            telemetry.addData("LoopTime (ns)", delta_time);
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }

    public void power_mod_2(double target) {
        double gain = 0.0025;
        double tolerance = 7.0;
        double current_v = shooter.getVelocity() * convert;

        double delta_v = current_v - last_velocity;
        double exp_v = current_v + delta_v;
        double exp_e = target - exp_v;
        double new_power = shooter.getPower() + gain * (exp_e / target);
        shooter.setPower(new_power);
        if((Math.abs(target - current_v) < tolerance) && !was_tolerance) {
            spin_time = timer.milliseconds();
            was_tolerance = true;
        } else if (was_tolerance && Math.abs(target - current_v) > tolerance) {
            spin_time = 0.0;
            was_tolerance = false;
            timer.reset();
        }

        last_velocity = current_v;
    }

    public Shooter_Controller powermod = new Shooter_Controller(0.00002, -0.004);
    public void power_mod() {
        double cur_v = shooter.getVelocity() * convert;
        double output = powermod.update(cur_v);

        last_velocity = cur_v;
        //double adjust = output / convert;
        shooter.setPower(shooter.getPower() + output);
    }

    public double velocity() {
        int ticks = shooter.getCurrentPosition();
        double delta_ticks = ticks - last_ticks;
        last_ticks = ticks;
        delta_time = timer.nanoseconds() - last_time;
        last_time = timer.nanoseconds();

        return delta_ticks / delta_time;
    }
}

