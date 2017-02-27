package org.firstinspires.ftc.teamcode.VelocityVortex;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by spmce on 12/16/2016.
 */
//@Autonomous (name = "Simple Auto", group = "Autonomous")
public class VVSimpleAuto extends VVAutoMeth {

    private int state = 0;
    //private AutoLaunch launch = new AutoLaunch();
    private boolean isFinished;
    int num = 0;

    public void init() {
        super.init();
    }

    public void start() { super.start(); }

    public void autoLoop(boolean ifBlue, boolean ifRamp) {
        switch (state) {
            case 0:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 1:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 2:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 3:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 4:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 5:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 6:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 7:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 8:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 9:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 10:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 11:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 12:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 13:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 14:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 15:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state = 20;
                break;
            case 16:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 17:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 18:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 19:
                try {
                    Thread.sleep(1000); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 20:
                driveForward(0.5);
                if (mFL.getCurrentPosition() > 1280) {
                    state++;
                }
                break;
            case 21:
                zeroDrive();
                state++;
                break;
            case 22:
                isFinished = vvShooter();
                if (isFinished) {
                    state++;
                }
                break;
            case 23:
                if (ifRamp) {
                    state = 27;
                } else {
                    state++;
                }
                break;
            case 24:
                driveForward(.5);
                if (mFL.getCurrentPosition() > 5580) {
                    state++;
                }
                break;
            case 25:
                drivePow(Math.PI,1,ifBlue);
                try {
                    Thread.sleep(800); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 26:
                zeroDrive();
                break;
            case 27:
                drivePow(3*Math.PI/16,1,ifBlue);
                try {
                    Thread.sleep(3150); // 1.2 seconds
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                state++;
                break;
            case 28:
                zeroDrive();
                break;
            default:
                zeroDrive();
                telemetry.addData("default", "default state");
                break;
        }
        telemetry.addData("state",state);
        robotTele();
    }
    public void stop() {
        robotTele();
        zeroDrive();
    }
    private void robotTele() {
        telemetry.addData("fl encoder", mFL.getCurrentPosition());
        telemetry.addData("fr encoder", mFR.getCurrentPosition());
        telemetry.addData("bl encoder", mBL.getCurrentPosition());
        telemetry.addData("br encoder", mBR.getCurrentPosition());
    }
}
