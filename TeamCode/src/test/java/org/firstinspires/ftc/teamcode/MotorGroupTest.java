/*
* MIT License

Copyright (c) 2018 Nathan Varner

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.firstinspires.ftc.teamcode;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.junit.Before;
import org.junit.Test;

public class MotorGroupTest {

  private MotorGroup motorGroup;
  private MockDcMotor motor = new MockDcMotor();

  /**
   * Run a setup before each test.
   */
  @Before
  public void beforeEach() {
    motorGroup = new MotorGroup(1440, 399, motor);
  }

  /**
   * Ensure that when the encoders are reset, they go back to 0.
   */
  @Test
  public void resetEncoders_0() {
    motorGroup.goForDistance(500, 1); // Make the encoders not 0
    motorGroup.setPower(0);
    while (motorGroup.isBusy()) {
    }
    motorGroup.brake();
    motorGroup.resetEncoders();
    assertEquals("Failed to reset encoders properly", motor.getCurrentPosition(), 0);
  }

  /**
   * Ensure that the constructor doesn't break with bad arguments that should throw an error.
   */
  @Test
  public void MotorGroup_error() {
    Object[][] settings = {
        // xClicks, mmPerXClicks, motor
        {0, 0.0, motor},
        {1, 1.0, null}
    };

    for (Object[] setting : settings) {
      try {
        motorGroup = new MotorGroup((int) setting[0], (double) setting[1], (DcMotor) setting[2]);
        fail("Failed to throw IllegalArgumentException when one should be thrown.");
      } catch (IllegalArgumentException e) {
        // We're expecting this exception, this is a pass
      }
    }
  }

  /**
   * Ensure that the constructor constructs the right way.
   */
  @Test
  public void MotorGroup_construction() {
    Object[][][] settings = {
        // {xClicks, mmPerXClicks, motor}, {expectedXClicks, expectedMmPerXClicks}
        {
            {1, 1.0, motor}, {1, 1.0}
        },
        {
            {-1, -1.0, motor}, {1, 1.0}
            // These negative numbers should be made positive and thus be legal
        }
    };

    for (int i = 0; i < settings.length; i++) {
      Object[][] setting = settings[i];
      motorGroup = new MotorGroup((int) setting[0][0], (double) setting[0][1],
          (DcMotor) setting[0][2]);

      if (!(motorGroup.X_CLICKS == (int) setting[1][0]) || !(motorGroup.MM_PER_X_CLICKS
          == (double) setting[1][1])) {
        fail("Failed to get expected result from test, #" + i);
      }
    }
  }

  /**
   * Ensure that setPower actually sets the power.
   */
  @Test
  public void setPower_success() {
    motorGroup.setPower(0);

    float[] testCases = {
        1.0f,
        0.5f,
        0.0f,
        -0.678925f
    };

    for (int i = 0; i < testCases.length; i++) {
      motorGroup.setPower(testCases[i]);
      assertEquals("Failed by not setting motor power, test case #" + i, (float) motor.getPower(),
          testCases[i]);
    }
  }

  /**
   * Ensure that setPower actually trims the ranges
   */
  @Test
  public void setPower_clip() {
    motorGroup.setPower(0);

    float[][] testCases = {
        // Test input, expected actual power
        {100, 1},
        {57.3f, 1},
        {-2, -1},
        {-54.6f, -1}
    };

    for (int i = 0; i < testCases.length; i++) {
      motorGroup.setPower(testCases[i][0]);
      assertEquals("Failed by not setting or clipping motor power, test case #" + i,
          testCases[i][1], (float) motor.getPower());
    }
  }
}
