package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.VelocityVortex.OmniWheelDrive;

/**
 * Created by spmce on 10/9/2016.
 */

//@TeleOp(name = "Shane's TeleOp new" , group = "TeleOp")

public class VelocityVortex extends OpMode {

    //ColorSensor color;
    DcMotor fl,fr,bl,br;
    DcMotor sweeper;
    @Override
    public void init() {
        //color = hardwareMap.colorSensor.get("color");
        fl = hardwareMap.dcMotor.get("fl");
        //fl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr = hardwareMap.dcMotor.get("fr");
        fr.setDirection(DcMotorSimple.Direction.REVERSE);

        bl = hardwareMap.dcMotor.get("bl");
        //bl.setDirection(DcMotorSimple.Direction.REVERSE);

        br = hardwareMap.dcMotor.get("br");
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        sweeper = hardwareMap.dcMotor.get("swpr");
    }


    @Override
    public void loop() {
        //VelocityVortexBacon.check(color);
        //VelocityVortexOmniWheelDrive d = new VelocityVortexOmniWheelDrive();
        OmniWheelDrive d = new OmniWheelDrive();
        double[] D;
        D = d.drive(gamepad1);
        telemetry.addData("front Left : ",D[0]);
        telemetry.addData("front right: ",D[1]);
        telemetry.addData("back  right: ",D[2]);
        telemetry.addData("back  Left : ",D[3]);
        fl.setPower(D[0]);
        fr.setPower(D[1]);
        br.setPower(D[2]);
        bl.setPower(D[3]);
        double sweeperPower = -gamepad1.left_trigger + gamepad1.right_trigger;
        sweeper.setPower(sweeperPower);
        //telemetry.addData("",color.blue());
        //color.blue();








        // Stores values of joystick input
        // Strafe is movement along the x-axis
        // Drive is movement along the y-axis
        // Rotate is movement with respect to an axis at the center of the robot
        /*float strafe;
        float drive;
        float rotate;

        float frontLeftPow;
        float backLeftPow;
        float frontRightPow;
        float backRightPow;

        // Copy joystick values into something more friendly
        strafe = gamepad1.left_stick_x;
        drive = gamepad1.left_stick_y;
        rotate = gamepad1.right_stick_x;

        // Scale these values for smooth acceleration
        frontLeftPow = (float)(drive + strafe + rotate);
        backLeftPow = (float)(drive - strafe + rotate);
        frontRightPow = (float)(drive - strafe - rotate);
        backRightPow = (float)(drive + strafe - rotate);

        // Set the motor power (the actual driving part)
        fl.setPower(frontLeftPow);
        bl.setPower(backLeftPow);
        fr.setPower(frontRightPow);
        br.setPower(backRightPow);*/
    }
}
