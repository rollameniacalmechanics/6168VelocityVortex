package org.firstinspires.ftc.teamcode.Shane;<<<<<<< HEAD
package org.firstinspires.ftc.teamcode.TestCode;
=======
package org.firstinspires.ftc.teamcode.Shane;
>>>>>>> origin/master

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
<<<<<<< HEAD
 * Created by team on 7/19/2017.
 */

@TeleOp(name = "Jill",group = "TeleOp" )
public class MyTestCodeTeleOp extends MyTestCodeTelemetry {

=======
 * Created by lsatt on 7/19/2017.
 */
@TeleOp(name = "Shane's TeleOp", group = "TeleOp")
public class MyTestCodeTeleOp extends MyTestCodeTelemetry {

    private int padCofig = 0;
>>>>>>> origin/master

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
        padControls();
<<<<<<< HEAD
        setMotorPower();
        super.myTelemetry();

    }   //end loop

    private void padControls(){

        rightPower = gamepad1.right_stick_y; // gamepad right stick for right robot motor
        leftPower = gamepad1.left_stick_y; // gamepad left stick for left robot motor

        if (gamepad1.dpad_up){              //  lift motor controlled by d-pad on controller
            liftPower = 0.75;
        }
        else if (gamepad1.dpad_down){
            liftPower = -0.75;
        }
        else {
            liftPower = 0;
        }

        //  arm motor controlled by A/B buttons on controller

        // Gamepad h/w works like this:
        // bumper on is true, bumper off is 0 -- my comment says A/B buttons, but code below
        // uses triggers -- OOPS!!
        // gamepad1.left_trigger is a float, so trigger needs supply value for armPower
        /* this will not work --
        if (boolean!!){
            armPower = 1;
        }
        else if (gamepad1.right_trigger){
            armPower = -1;
        }
        else {
            armPower = 0;
        }
        */

        // switched armPower setting to the triggers  -- JCF 7/19/17
        armPower = gamepad1.right_trigger;
        armPower = armPower - gamepad1.right_trigger;




    }

    private void setMotorPower() {

        rightMotor.setPower(rightPower);
        leftMotor.setPower(leftPower);
        liftMotor.setPower(liftPower);
        liftMotor.setPower(armPower);
    }

=======
        setMotorPow();
        configTele();
        super.myTelemetry();
    }

    private void padControls() {
        if (gamepad1.a) {
            padCofig = 0;
        }
        if (gamepad1.b) {

            padCofig = 1;
        }
        double drivePower[] = new double[2];
        if (padCofig == 0) {
            TankDrive tank = new TankDrive();
            drivePower = tank.drive(gamepad1);
        }
        if (padCofig == 1) {
            TurnDrive turn = new TurnDrive();
            drivePower = turn.drive(gamepad1);
        }
        rightPower = drivePower[0];
        leftPower = drivePower[1];
        liftPower = gamepad1.right_trigger - gamepad1.left_trigger;
        if (gamepad1.left_bumper) {
            armPower = 1;
        } else if (gamepad1.right_bumper) {
            armPower = -1;
        } else {
            armPower = 0;
        }
    }

    private void setMotorPow() {
        rightMotor.setPower(rightPower);
        leftMotor.setPower(leftPower);
        liftMotor.setPower(liftPower);
        armMotor.setPower(armPower);
    }

    private void configTele() {
        telemetry.addData("gamepad Configuration", padCofig);
    }
>>>>>>> origin/master
}
