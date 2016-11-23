package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by spmce on 11/3/2016.
 */
@TeleOp (name = "Velocity Vortex TeleOp" , group = "TeleOp")
public class VelocityVortexTeleOp extends VelocityVortexHardware {

    VelocityVortexTelemetry tele = new VelocityVortexTelemetry();
    //@Override
    public void init() {
        super.init();
        //tele.initTele();
    }
    /*public void init_loop() {
        //super.init_loop();
        //tele.initLoopTele();
    }*/
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
        //MySweeper sweeper = new MySweeper();
        //double left = gamepad1.left_trigger;
        //double right = gamepad1.right_trigger;
        //sweeperPower = sweeper.sweep(left,right);
        sweeperPower = -gamepad1.left_trigger + gamepad1.right_trigger;
        mSweeper.setPower(sweeperPower);
        if (gamepad1.a)
            sLeftBeacon.setPosition(.96);
        else
            sLeftBeacon.setPosition(initLeftBeacon);
        if (gamepad1.b)

            sRightBeacon.setPosition(0);
        else
            sRightBeacon.setPosition(initRightBeacon);
        //tele.loopTele();
        //telemetry.addData("Touch",touch.isPressed());
        //telemetry.addData("Color 1 blue",color1.blue());
        //telemetry.addData("Color 1 red",color1.red());
        shanesTelemetry tele = new shanesTelemetry();
        tele.motorTele();
        tele.servoTele();
        tele.sensorTele();
    }
}
