package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.TestCode.VelocityVortex;

/**
 * Created by spmce on 10/9/2016.
 */
public class VelocityVortexBacon extends VelocityVortex {

    //private static DropBoxManager telemetry;
    Servo r;
    Servo l;
    //ColorSensor color;
    TouchSensor touch;

    public void init() {
        super.init();
        map();
        preset();
    }
    public void loop() {
        super.loop();
    }

    public void map() {
        r = hardwareMap.servo.get("r");
        l = hardwareMap.servo.get("l");
        //color = hardwareMap.colorSensor.get("color");
        touch = hardwareMap.touchSensor.get("touch");
    }

    public void preset() {
        r.setPosition(0.8);
        l.setPosition(0.2);
    }

    /*public static void check(ColorSensor color) {
        telemetry.addData("Test", color.blue());
    }*/

    public void press(boolean right) {
        if (right)
            r.setPosition(1.0);
        else
            l.setPosition(0.0);
    }
}
