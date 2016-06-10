// Written by Brandon Yee. April 16, 2016. Collaboration with Cole Nagata, Jason Tay, and Mihir Kolli.

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class SwerveDrive2 extends OpMode {
    DcMotor m1; // front right
    DcMotor m2; // front left
    DcMotor m3; // rear left
    DcMotor m4; // rear right

    DcMotor s1; // front right
    DcMotor s2; // front left
    DcMotor s3; // rear left
    DcMotor s4; // rear right

	double turningSpeed = 0.5; // This determines how fast the s motors are turning.
	int gearTicks = 1440; // The amount of ticks by the encoder that equals a full 360 degree rotation by the motor.
	// int gearRatio = 1; // What is gear ratio??

    // These functions are used for calculating speed and direction. They are part of the math equations.
    public double ms (double equation) {
        return Math.sqrt(equation);
    }
    public double mp (double varToIncrease) {
        return Math.pow(varToIncrease, 2);
    }
    public double getSpeed (double q, double m) {
        return ((m > 1) ? q/m : q);
    }
    public int getDirection (double q1, double q2) {
    	// ((q1 == 0 && q2 == 0) ? 0 : Math.atan2(q1, q2) * 180 / Math.PI)
	    // (((q1 == 0 && q2 == 0) ? 0 : Math.atan2(q1, q2) / Math.PI) + 0.5)
	    double equationSolution = ((q1 == 0 && q2 == 0) ? 0 : Math.atan2(q1, q2) * (gearTicks / 360) / Math.PI);
        return (Integer.parseInt("" + equationSolution));
    }

    public SwerveDrive2() {} // Constructor

    @Override
    public void init () {
        // These motors are in charge of speed.
        m1 = hardwareMap.dcMotor.get("m1");
        m2 = hardwareMap.dcMotor.get("m2");
        m3 = hardwareMap.dcMotor.get("m3");
        m4 = hardwareMap.dcMotor.get("m4");

        // These motors are in charge of direction. They use encoders.
        s1 = hardwareMap.dcMotor.get("s1");
        s2 = hardwareMap.dcMotor.get("s2");
        s3 = hardwareMap.dcMotor.get("s3");
        s4 = hardwareMap.dcMotor.get("s4");

        // Setup the encoders.
        s1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        s2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        s3.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        s4.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void loop () {
//        // Get the speed and strafe from left stick, and the direction from the right stick.
//        float f = -gamepad1.left_stick_y;
//        float p = gamepad1.left_stick_x;
//        float w = gamepad1.right_stick_x;
//
//        // What does this do? I added it to the top, since it auto-converts floats to doubles for me.
//        f = (float)scaleInput(f);
//        p = (float)scaleInput(p);
//        w = (float)scaleInput(w);
//
//        // Wheelbase and trackwidth. Needed to know how many degrees to turn.
//        double l = 8.5; // Wheelbase.
//        double t = 10; // Trackwidth.
//        double r = ms(mp(l) + mp(t));
//
//        // Used in the math algorithm for swerve drive.
//        double a = p - ((l/r) * w);
//        double b = p + ((l/r) * w);
//        double c = f - ((t/r) * w);
//        double d = f + ((t/r) * w);
//        double e = ms(mp(b) + mp(c));
//        double g = ms(mp(b) + mp(d));
//        double j = ms(mp(a) + mp(d));
//        double k = ms(mp(a) + mp(c));
//
//        // This gets the largest number from e, g, j, or k.
//        double[] mQuick = {e, g, j, k};
//        Arrays.sort(mQuick);
//        double m = mQuick[3];
//
//        // Front right.
//        double w1s = getSpeed(e, m);
//        int w1d = getDirection(b, c);
//
//	    // Speed.
//        m1.setPower(w1s);
//	    // Direction.
//	    // Direction.
//	    s1.setPower(turningSpeed);
//	    s1.setTargetPosition(w1d);
//	    s1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//
//	    // Front left.
//	    double w2s = getSpeed(g, m);
//	    int w2d = getDirection(b, d);
//
//	    // Speed.
//	    m2.setPower(w2s);
//	    // Direction.
//	    s2.setPower(turningSpeed);
//	    s2.setTargetPosition(w2d);
//	    s2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//
//	    // Rear left.
//	    double w3s = getSpeed(j, m);
//	    int w3d = getDirection(a, d);
//
//	    // Speed.
//	    m3.setPower(w3s);
//	    // Direction.
//	    s3.setPower(turningSpeed);
//	    s3.setTargetPosition(w3d);
//	    s3.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//
//	    // Rear right.
//	    double w4s = getSpeed(k, m);
//	    int w4d = getDirection(a, c);
//
//	    // Speed.
//	    m4.setPower(w4s);
//	    // Direction.
//	    s4.setPower(turningSpeed);
//	    s4.setTargetPosition(w4d);
//	    s4.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
//
    }

    // Don't touch.
    double scaleInput(double dVal){
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        int index = (int) (dVal * 16.0); if (index < 0) {index = -index;}
        if (index > 16) {index = 16;}
        double dScale = 0.0; if (dVal < 0) {dScale = -scaleArray[index];} else {dScale = scaleArray[index];}
        return dScale;
    }
}
