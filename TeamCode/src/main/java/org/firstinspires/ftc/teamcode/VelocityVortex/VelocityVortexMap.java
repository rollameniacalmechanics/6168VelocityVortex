package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by spmce on 11/6/2016.
 */
public class VelocityVortexMap extends VelocityVortexHardware{
    public void map() {
        // Hardware Map
        // ADD HARDWARE MAP HERE;
        // DcMotors - Map
        mFL = hardwareMap.dcMotor.get("mFL");
        mFR = hardwareMap.dcMotor.get("mFR");
        mFR.setDirection(DcMotorSimple.Direction.REVERSE);
        mBL = hardwareMap.dcMotor.get("mBL");
        mBR = hardwareMap.dcMotor.get("mBR");
        // Servos - Map

        // Sensors - Map

    }
}
