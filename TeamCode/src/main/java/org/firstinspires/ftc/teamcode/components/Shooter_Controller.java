package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Shooter_Controller {
    double last_update = Double.NaN;

    double targetPosition = 0.0;
    double last_error = 0.0;
    double kP;
    double kD;
    double last_velocity = 0.0;
    double convert = (4.0 * Math.PI * 1.6) / (28.0);

    public Shooter_Controller(
            double kP,
            double kD
    ) {
        this.kP = kP;
        this.kD = kD;

    }

    public void power_mod(DcMotorEx shooter) {
        double cur_v = shooter.getVelocity() * convert;
        double output = update(cur_v);

        last_velocity = cur_v;
        shooter.setPower(shooter.getPower() + output);
    }

    public void setTarget(double target) {
        targetPosition = target;
    }

    public double getPositionError(double measuredPosition) {
        return targetPosition - measuredPosition;
    }

    public double update(double measuredPosition) {
        double error = getPositionError(measuredPosition);

        if (last_error == Double.NaN) {
            last_error = error;
            return 0.0;
        } else {
            double error_diff = last_error - error;

            last_error = error;
            double base_power = 0.0001 * measuredPosition + 0.0003 * error_diff;
            double kF;
            if (base_power <= 0.1) {
                kF = 0.0;
            } else {
                kF = base_power - Math.copySign(1, base_power) * 0.02 ;
            }
            return kP * error + kD * error_diff + kF;
        }
    }

    public void reset() {
        last_error = 0.0;
        last_update = 0.0;
    }

}
