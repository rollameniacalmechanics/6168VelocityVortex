package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
/**
 * Created by spmce on 10/9/2016.
 */
public class VelocityVortexOmniWheelDrive extends OpMode{
    public double[] calculate(Gamepad pad) {
        double lx;
        double ly;
        double rx;
        double f;
        double angle;
        double ref;
        //telementry.addData
        lx =  pad.left_stick_x;
        ly = -pad.left_stick_y;
        rx =  pad.right_stick_x;
        f = pythag(lx, ly);

        angle = Math.acos(lx/f);
        if (ly < 0)
            angle += Math.PI;
        //angle = 0.75*Math.PI - angle;

        double[] F = new double[4];

        if (f == 0) {
            F[0] = 0;
            F[1] = 0;
            F[2] = F[0];
            F[3] = F[1];
            telemetry.addData("Nothing","");
        } else if (angle >= Math.PI/4) {
            ref = Math.PI/4 - angle;
            if (ly >= 0) {
                F[0] = f * Math.cos(ref);
                F[1] =-f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("right-up","");
            } else {
                F[0] = f * Math.sin(ref);
                F[1] =-f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("right-down","");
            }
        } else if (angle >= 3*Math.PI/4) {
            ref = 3*Math.PI/4 - angle;
            if (ly >= 0) {
                F[0] = f * Math.sin(ref);
                F[1] = f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("Forwards","");
            } else {
                F[0] =-f * Math.cos(ref);
                F[1] =-f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("Backwards","");
            }
        } else {
            ref = angle - 3*Math.PI/4;
            if (ly >= 0) {
                F[0] = f * Math.cos(ref);
                F[1] =-f * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("left-up","");
            } else {
                F[0] = f * Math.sin(ref);
                F[1] =-f * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
                telemetry.addData("left-down","");
            }
            telemetry.update();
        }
        /*if (angle >= Math.PI/4 && angle < 3*Math.PI/4) {
            ref = angle - Math.PI / 4;
            F[0] = f * Math.cos(ref);
            F[1] = f * Math.sin(ref);
            F[2] = F[0];
            F[3] = F[1];
        }
        if (angle >= 3*Math.PI/4 && angle < 5*Math.PI/4) {
            ref = angle - 3*Math.PI / 4;
            F[0] = -f * Math.sin(ref);
            F[1] =  f * Math.cos(ref);
            F[2] =  F[0];
            F[3] =  F[1];
        }
        if (angle >= 5*Math.PI/4 && angle < 7*Math.PI/4) {
            ref = angle - 5*Math.PI / 4;
            F[0] = -f * Math.cos(ref);
            F[1] = -f * Math.sin(ref);
            F[2] = F[0];
            F[3] = F[1];
        }
        if (angle >= 7*Math.PI/4 || angle < Math.PI/4) {
            if (angle >= 7*Math.PI/4) {
                ref = angle - 7 * Math.PI / 4;
            } else {
                ref = angle;
            }
            F[0] =  f * Math.sin(ref);
            F[1] = -f * Math.cos(ref);
            F[2] =  F[0];
            F[3] =  F[1];
        }*/
        /*if (ly >= 0) {
            F[0] = f * Math.cos(angle);
            F[1] = f * Math.sin(angle);
            F[2] = F[0];
            F[3] = F[1];
        } else {
            F[0] = -f * Math.cos(angle);
            F[1] = -f * Math.sin(angle);
            F[2] = F[0];
            F[3] = F[1];
        }*/
        F = turn(F,rx);

        return F;
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
        F[2] = F[2] + rx;
        F[3] = F[3] - rx;
        for (int i = 0; i < 4; i++)
        {
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

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
