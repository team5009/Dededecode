package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

public class Java_Beambreak {
    public DigitalChannel create_sensor(String name, HardwareMap hardwareMap, boolean is_input) {

        DigitalChannel sensor = hardwareMap.get(DigitalChannel.class, name);
        if(is_input){
            sensor.setMode(DigitalChannel.Mode.INPUT);
        }else {
            sensor.setMode(DigitalChannel.Mode.OUTPUT);
        }

        return sensor;
    }
}
