package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by spmce on 10/30/2016.
 */
//@TeleOp(name = "Really Big Class TeleOp" , group = "TeleOp")
public class reallybigclass extends OpMode {

    DcMotor fl,fr,bl,br;
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
    }


    @Override
    public void loop() {

        //VelocityVortexBacon.check(color);
        VelocityVortexOmniWheelDrive d = new VelocityVortexOmniWheelDrive();

        double lx;
        double ly;
        double rx;
        double f;
        double angle;
        double ref;
        //telementry.addData
        Gamepad pad = gamepad1;
        lx = pad.left_stick_x;
        ly = -pad.left_stick_y;
        rx = pad.right_stick_x;
        f = pythag(lx, ly);

        angle = Math.acos(lx / f);
        //if (ly < 0)
            //
            // 63angle += Math.PI;
        //angle = 0.75*Math.PI - angle;

        double[] F = new double[4];
        telemetry.addData("angle",angle);
        telemetry.addData("degree",angle*180/Math.PI);

        if (f == 0) {
            F[0] = 0;
            F[1] = 0;
            F[2] = F[0];
            F[3] = F[1];
            telemetry.addData("Nothing", "");
        } else if (angle < Math.PI / 4) {
            ref = Math.PI / 4 - angle;
            if (ly >= 0) {
                F[0] = f * Math.cos(ref);
                F[1] = -f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("right-up", "");
            } else {
                F[0] = f * Math.sin(ref);
                F[1] = -f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("right-down", "");
            }
        } else if (angle < Math.PI / 4 + 0.00000000000002) {
            if (ly >= 0) {
                F[0] = f;
                F[1] = 0;
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("NE", "");
            } else {
                F[0] = 0;
                F[1] = -f;
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("SE", "");
            }
        } else if (angle < 3 * Math.PI / 4) {
            ref = 3 * Math.PI / 4 - angle;
            if (ly >= 0) {
                F[0] = f * Math.sin(ref);
                F[1] = f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("Forwards", "");
            } else {
                F[0] = -f * Math.cos(ref);
                F[1] = -f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("Backwards", "");
            }
        } else if (angle == 3 * Math.PI / 4) {
            if (ly >= 0) {
                F[0] = 0;
                F[1] = f;
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("NW", "");
            } else {
                F[0] = -f;
                F[1] = 0;
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("SW", "");
            }
        } else if (angle == Math.PI) {
            double num = Math.sqrt(2)/2;
            F[0] = -num;
            F[1] = num;
            F[2] = F[0];
            F[3] = F[1];
            telemetry.addData("left", "");
        } else {
            //ref = angle - 3 * Math.PI / 4;
            ref = 5 * Math.PI / 4 - angle;
            if (ly >= 0) {
                F[0] = f * Math.cos(ref);
                F[1] = -f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("left-up", "");
            } else {
                F[0] = f * Math.sin(ref);
                F[1] = -f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("left-down", "");
            }
        }
        F = turn(F,rx);
        double[] D = F;

        telemetry.addData("front Left : ", D[0]);
        telemetry.addData("front right: ", D[1]);
        telemetry.addData("back  right: ", D[2]);
        telemetry.addData("back  Left : ", D[3]);
        for (int i = 0; i < 4; i++) {
            if (D[i] > 1)
                D[i] = 1;
            else if (D[i] < -1)
                D[i] = -1;
            else if (isNaN(D[i]))
                D[i] = 0;
        }
        fl.setPower(D[0]);
        fr.setPower(D[1]);
        br.setPower(D[2]);
        bl.setPower(D[3]);
    }
    public double pythag(double a, double b) {
        double a2;
        double b2;
        double c2;
        double c;
        a2 = Math.pow(a, 2);
        b2 = Math.pow(b,2);
        c2 = a2+b2;
        c = Math.sqrt(c2);
        return c;
    }
    public double[] turn (double[] F, double rx) {
        F[0] = F[0] + rx;
        F[1] = F[1] - rx;
        F[2] = F[2] - rx;
        F[3] = F[3] + rx;
        for (int i = 0; i < 4; i++) {
            int k = i + 2;
            if (k >= 4)
                k -= 4;
            if (F[i] > 1) {
                double temp = F[i] - 1;
                F[i] = 1;
                F[k] -= temp;
            }
            if (F[i] < -1) {
                double temp = F[i] + 1;
                F[i] = -1;
                F[k] -= temp;
            }
        }
        return F;
    }
    boolean isNaN(double x){return x != x;}
}
