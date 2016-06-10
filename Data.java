/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by byee20 on 10/10/15.
 */
public class Data extends OpMode {
	DcMotor m1; // front right
	DcMotor m2; // front left
	DcMotor m3; // rear left
	DcMotor m4; // rear right

	Servo s1; // front right
	Servo s2; // front left
	Servo s5; // rear left
	Servo s4; // rear right

	double defaultServoPosition = 0.5;
	int whichPos = 1;

	// Constructor
	public Data() {}

	@Override
	public void init () {
		m1 = hardwareMap.dcMotor.get("m1");
		m2 = hardwareMap.dcMotor.get("m2");
		m3 = hardwareMap.dcMotor.get("m3");
		m4 = hardwareMap.dcMotor.get("m4");

		// motorLeft.setDirection(DcMotor.Direction.REVERSE);
		// motorRight.setDirection(DcMotor.Direction.REVERSE);

		s1 = hardwareMap.servo.get("s1");
		s2 = hardwareMap.servo.get("s2");
		s5 = hardwareMap.servo.get("s5");
		s4 = hardwareMap.servo.get("s4");

		// All wheels facing forward. (direction = 0)
		s1.setPosition(defaultServoPosition);
		s2.setPosition(defaultServoPosition);
		s5.setPosition(defaultServoPosition);
		s4.setPosition(defaultServoPosition);
	}

	@Override
	public void loop () {
		if (whichPos == 1) {
			// All wheels facing forward. (direction = 0, servo direction = 0.5)
			s1.setPosition(0.5);
			s2.setPosition(0.5);
			s5.setPosition(0.5);
			s4.setPosition(0.5);
		} else if (whichPos == 2) {
			// All wheels facing left. (direction = -90, servo direction = 0)
			s1.setPosition(0);
			s2.setPosition(0);
			s5.setPosition(0);
			s4.setPosition(0);
		} else {
			// All wheels facing right. (direction = 90, servo direction = 1)
			s1.setPosition(1);
			s2.setPosition(1);
			s5.setPosition(1);
			s4.setPosition(1);
		}

		if (gamepad1.a) {
			whichPos = 2;
		} else if (gamepad1.b) {
			whichPos = 3;
		} else if (gamepad1.x) {
			whichPos = 1;
		}

		/** Buttons on the right side of gamepad.
		  * Not used in Brandon.
		  * Used to control servos in K9TeleOp.
		*/
		telemetry.addData("A button", "" + gamepad1.a);
		telemetry.addData("B button", "" + gamepad1.b);
		telemetry.addData("X button", "" + gamepad1.x);
		telemetry.addData("Y button", "" + gamepad1.y);

		telemetry.addData("Left stick x", "" + gamepad1.left_stick_x);
		telemetry.addData("Left stick y", "" + gamepad1.left_stick_y);

		/** The right joystick is the only correct joystick.
		  * It returns a double, of 1, 0, -1, or 0.435458297 or -0.13535358245 or etc.
		*/
		telemetry.addData("Right stick x", "" + gamepad1.right_stick_x);
		telemetry.addData("Right stick y", "" + gamepad1.right_stick_y);

		/** These are the general buttons on the gamepad. Back button does not work. */
		// telemetry.addData("Start button", "" + gamepad1.start);
		// telemetry.addData("Back button", "" + gamepad1.back);
		// telemetry.addData("Logitech button", "" + gamepad1.guide);

		/** These are the bumpers and triggers on the front of the gamepad.
		 * Not used for anything in both K9TeleOp and Brandon.
		 * Will be used to control servos in Brandon, in the future.
		 */
		// telemetry.addData("Left bumper", "" + gamepad1.left_bumper);
		// telemetry.addData("Right bumper", "" + gamepad1.right_bumper);
		// telemetry.addData("Left trigger", "" + gamepad1.left_trigger);
		// telemetry.addData("Right trigger", "" + gamepad1.right_trigger);

		/** This is when you press the bottom sticks down. */
		// telemetry.addData("Left stick button", "" + gamepad1.left_stick_button);
		// telemetry.addData("Right stick button", "" + gamepad1.right_stick_button);

		/** The Dpad is the left joystick on the bottom of the gamepad.
		 * Theoretically, it should be the joystick on the top left of the gamepad,
		 * but it must be a glitch.
		 */
		// telemetry.addData("Dpad up", "" + gamepad1.dpad_up);
		// telemetry.addData("Dpad down", "" + gamepad1.dpad_down);
		// telemetry.addData("Dpad left", "" + gamepad1.dpad_left);
		// telemetry.addData("Dpad right", "" + gamepad1.dpad_right);

		/** The left joystick is the joystick on the top left of the gamepad.
		 * It returns a float/double of 1, 0, or -1. It must be part of the Dpad glitch.
		 */
	}
}
