package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.junit.Assert;
import org.junit.Test;

public class UnitConverterTest {

	@Test
	public void testPerSecondToPer10ms() {
		double positiveInt = 1;
		double negativeInt = -2;
		double zeroInt = 0;
		double positiveFloat = 9.732;
		double negativeFloat = -3.65;
		
		double positiveIntSolution = 100;
		double negativeIntSolution = -200;
		double zeroIntSolution = 0;
		double positiveFloatSolution = 973.2;
		double negativeFloatSolution = -365;
		
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(positiveInt), positiveIntSolution,0.0001);
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per10msToPerSecond(negativeFloat), negativeFloatSolution, 0.0001);
		
	}

	@Test
	public void testPer10msTOPerSecond() {
		double positiveInt = 1;
		double negativeInt = -2;
		double zeroInt = 0;
		double positiveFloat = 9.732;
		double negativeFloat = -3.65;

		double positiveIntSolution = .01;
		double negativeIntSolution = -.02;
		double zeroIntSolution = 0;
		double positiveFloatSolution = .09732;
		double negativeFloatSolution = -.0365;
		
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(positiveInt), positiveIntSolution,0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer10ms(negativeFloat), negativeFloatSolution, 0.0001);
	}

	@Test
	public void testPerSecondToPer100ms() {
		double positiveInt = 1;
		double negativeInt = -2;
		double zeroInt = 0;
		double positiveFloat = 9.732;
		double negativeFloat = -3.65;
		
		double positiveIntSolution = 10;
		double negativeIntSolution = -20;
		double zeroIntSolution = 0;
		double positiveFloatSolution = 97.32;
		double negativeFloatSolution = -36.5;
		
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(positiveInt), positiveIntSolution,0.0001);
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.Per100msToPerSecond(negativeFloat), negativeFloatSolution, 0.0001);
		
	}

	@Test
	public void testPer100msTOPerSecond() {
		double positiveInt = 1;
		double negativeInt = -2;
		double zeroInt = 0;
		double positiveFloat = 9.732;
		double negativeFloat = -3.65;

		double positiveIntSolution = .1;
		double negativeIntSolution = -.2;
		double zeroIntSolution = 0;
		double positiveFloatSolution = .9732;
		double negativeFloatSolution = -.365;
		
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(positiveInt), positiveIntSolution,0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPer100ms(negativeFloat), negativeFloatSolution, 0.0001);
	}

	@Test
	public void testPerSecondToPerMinute() {
		double positiveInt = 60;
		double negativeInt = -3;
		double zeroInt = 0;
		double positiveFloat = 15.6;
		double negativeFloat = -3.887;

		double positiveIntSolution = 3600;
		double negativeIntSolution = -180;
		double zeroIntSolution = 0;
		double positiveFloatSolution = 936;
		double negativeFloatSolution = -233.22;

		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(positiveInt), positiveIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerSecondToPerMinute(negativeFloat), negativeFloatSolution, 0.0001);
	}

	@Test
	public void testPerMinuteToPerSecond() {
		double positiveInt = 60;
		double negativeInt = -3;
		double zeroInt = 0;
		double positiveFloat = 15.6;
		double negativeFloat = -3.887;

		double positiveIntSolution = 1;
		double negativeIntSolution = -0.05;
		double zeroIntSolution = 0;
		double positiveFloatSolution = 0.26;
		double negativeFloatSolution = -0.06478333333;

		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(positiveInt), positiveIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(negativeInt), negativeIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(zeroInt), zeroIntSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(positiveFloat), positiveFloatSolution, 0.0001);
		Assert.assertEquals(UnitConverter.PerMinuteToPerSecond(negativeFloat), negativeFloatSolution, 0.0001);
	}

}
