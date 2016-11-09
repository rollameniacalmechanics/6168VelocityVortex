package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by spmce on 11/3/2016.
 */
@TeleOp (name = "Velocity Vortex TeleOp" , group = "TeleOp")
public class VelocityVortexTeleOp extends VelocityVortexHardware {

    VelocityVortexTelemetry tele = new VelocityVortexTelemetry();
    //@Override
    public void init() {
        super.init();
        tele.initTele();
    }
    public void init_loop() {
        //super.init_loop();
        tele.initLoopTele();
    }
    //@Override
    public void loop() {
        super.loop();
        OmniWheelDrive omniWheels = new OmniWheelDrive();
        double[] wheels;
        wheels = omniWheels.drive(gamepad1);
        // Set Drive Train Power
        leftDrivePower  = wheels[0];
        rightDrivePower = wheels[1];
        backRightPower  = wheels[2];
        backLeftPower   = wheels[3];
        mFL.setPower(leftDrivePower);
        mFR.setPower(rightDrivePower);
        mBR.setPower(backRightPower);
        mBL.setPower(backLeftPower);
        tele.loopTele();
    }
}
