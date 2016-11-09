package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by spmce on 11/2/2016.
 */
public class OmniWheelDrive extends DriveTrain{

    private double angle; //cosine angle zero to PI (it is still zero to PI when y is positive)
    //private double ref;   //reference angle
    private double power; //power of robot form -1 to 1
    private boolean ifPositive; //determines if y is positive or negative used for calculating angles
    private double x;     //right stick x

    /**
     * OmniWheelDrive Constructor
     */
    public OmniWheelDrive() {
        angle = 0;
        //ref = 0;
        power = 0;
        ifPositive = true;
        x = 0;
    }

    /**
     * OmniWheelDrive Constructor
     * @param angle
     * @param power
     */
    OmniWheelDrive(double angle, double power) {
        this.angle = angle;
        this.power = power;
        //ref = 0;
        ifPositive = true;
        x = 0;
    }

    /**
     * @param angle
     * @param power
     * @param ifPositive
     */
    OmniWheelDrive(double angle, double power, boolean ifPositive) {
        this.angle = angle;
        this.power = power;
        //ref = 0;
        this.ifPositive = ifPositive;
        x = 0;
    }

    /**
     * @param angle
     * @param power
     * @param ifPositive
     * @param x
     */
    OmniWheelDrive(double angle, double power, boolean ifPositive, double x) {
        this.angle = angle;
        this.power = power;
        //ref = 0;
        this.ifPositive = ifPositive;
        this.x = x;
    }

    @Override
    public void run() {
        super.run();
    }

    /**
     * @param angle
     * @param power
     * @param ifPositive
     */
    public void drive(double angle, double power, boolean ifPositive) {
        this.ifPositive = ifPositive;
        this.angle = angle;
        this.power = power;
        runDrive();
    }

    /**
     * @param angle
     * @param power
     * @param ifPositive
     * @param x
     */
    public void drive(double angle, double power, boolean ifPositive, double x) {
        this.ifPositive = ifPositive;
        this.angle = angle;
        this.power = power;
        runDrive();
        this.x = x;
    }

    /**
     * @param pad
     * @return
     */
    public double[] drive(Gamepad pad) {
        double lx = pad.left_stick_x;
        double ly = -pad.left_stick_y;
        x = pad.right_stick_x;
        power = pythag(lx, ly);
        angle = Math.acos(lx / power);
        ifPositive = isIfPositive(ly);
        return runDrive();
    }

    /**
     * @return
     */
    public double[] runDrive() {
        double[] F = new double[4];
        double ref;
        if (power == 0) {
            F[0] = 0;   //front left
            F[1] = 0;   //front right
            F[2] = F[0];//back right
            F[3] = F[1];//back left
        } else if (angle < Math.PI / 4) {
            ref = Math.PI / 4 - angle;
            if (ifPositive) {
                F[0] = power * Math.cos(ref);
                F[1] =-power * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
            } else {
                F[0] = power * Math.sin(ref);
                F[1] =-power * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
            }
        } else if (angle < Math.PI / 4 + 0.00000000000002) { //add small number becuase of rounding is off
            if (ifPositive) {
                F[0] = power;
                F[1] = 0;
                F[2] = F[0];
                F[3] = F[1];
            } else {
                F[0] = 0;
                F[1] = -power;
                F[2] = F[0];
                F[3] = F[1];
            }
        } else if (angle < 3 * Math.PI / 4) {
            ref = 3 * Math.PI / 4 - angle;
            if (ifPositive) {
                F[0] = power * Math.sin(ref);
                F[1] = power * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
            } else {
                F[0] = -power * Math.cos(ref);
                F[1] = -power * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
            }
        } else if (angle == 3 * Math.PI / 4) {
            if (ifPositive) {
                F[0] = 0;
                F[1] = power;
                F[2] = F[0];
                F[3] = F[1];
            } else {
                F[0] =-power;
                F[1] = 0;
                F[2] = F[0];
                F[3] = F[1];
            }
        } else if (angle == Math.PI) {
            double num = Math.sqrt(2)/2;
            F[0] = -num;
            F[1] = num;
            F[2] = F[0];
            F[3] = F[1];
        } else {
            //ref = angle - 3 * Math.PI / 4;
            ref = 5 * Math.PI / 4 - angle;
            if (ifPositive) {
                F[0] = -power * Math.cos(ref);
                F[1] = power * Math.sin(ref);
                F[2] = F[0];
                F[3] = F[1];
            } else {
                F[0] = -power * Math.sin(ref);
                F[1] = power * Math.cos(ref);
                F[2] = F[0];
                F[3] = F[1];
            }
        }
        F = turn(F,x);
        for (int i = 0; i < 4; i++) {
            if (F[i] > 1)
                F[i] = 1;
            else if (F[i] < -1)
                F[i] = -1;
            else if (isNaN(F[i]))
                F[i] = 0;
        }
        return F;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public double pythag(double a, double b) {
        double a2;
        double b2;
        double c2;
        double c;
        a2 = Math.pow(a,2);
        b2 = Math.pow(b,2);
        c2 = a2+b2;
        c = Math.sqrt(c2);
        return c;
    }

    /**
     * @param F
     * @param rx
     * @return
     */
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

    /**
     * @param x
     * @return
     */
    boolean isNaN(double x){return x != x;}

    /**
     * @param y
     * @return
     */
    boolean isIfPositive(double y) {return y >= 0;}
}
