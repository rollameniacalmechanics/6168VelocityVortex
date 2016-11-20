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
            sLeftBeacon.setPosition(.97);
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
        motorTele();
        servoTele();
        sensorTele();
    }
    void motorTele() {
        telemetry.addData("fl",leftDrivePower);
        telemetry.addData("fr",rightDrivePower);
        telemetry.addData("bl",backLeftPower);
        telemetry.addData("br",backRightPower);
        telemetry.addData("sweeper",sweeperPower);
    }
    void servoTele() {
        telemetry.addData("rightBeacon",rightBeaconPosition);
        telemetry.addData("leftBeacon",leftBeaconPosition);
    }
    void sensorTele() {
        telemetry.addData("touch", touch.isPressed());
        telemetry.addData("touch double", touch.getValue());
        telemetry.addData("light1", light1.getLightDetected());
        telemetry.addData("light2", light2.getLightDetected());
        telemetry.addData("color1 red", color1.red());
        telemetry.addData("color1 blue",color1.blue());
        telemetry.addData("color2 red",color2.red());
        telemetry.addData("color2 blue",color2.blue());
        telemetry.addData("gyro heading",gyro.getHeading());
        //telemetry.addData("gyro rotate", gyro.getRotationFraction());
        //telemetry.addData("gyro x",gyro.rawX());
        //telemetry.addData("gyro y",gyro.rawY());
        //telemetry.addData("gyro z",gyro.rawZ());
        telemetry.addData("range",range.getDistance(DistanceUnit.INCH));
        telemetry.addData("","");
    }
}
