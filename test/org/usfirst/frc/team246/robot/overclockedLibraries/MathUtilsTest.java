package org.usfirst.frc.team246.robot.overclockedLibraries;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

	@Test
	public void testSignedPow() {
		// Bases:
		double posInt = 2;
		double negInt = -3;
		double zeroInt = 0;
		double posFloat = 6.534;
		double negFloat = -3.65;

		// Exponents:
		int posOdd = 3;
		int posEven = 6;
		int negOdd = -5;
		int negEven = -4;
		// zeroInt is already declared

		// Solutions:
		double posIntXposOdd = 8;
		double posIntXposEven = 64;
		double posIntXnegOdd = 0.03125;
		double posIntXnegEven = 0.0625;
		double posIntXzeroInt = 1;

		double negIntXposOdd = -27;
		double negIntXposEven = -729;
		double negIntXnegOdd = -0.00412;
		double negIntXnegEven = -0.01235;
		double negIntXzeroInt = -1;

		double zeroIntXposOdd = 0;
		double zeroIntXposEven = 0;
		double zeroIntXnegOdd = 0;
		double zeroIntXnegEven = 0;
		double zeroIntXzeroInt = 1;

		double posFloatXposOdd = 278.957;
		double posFloatXposEven = 77817.053;
		double posFloatXnegOdd = 0.000084;
		double posFloatXnegEven = 0.000549;
		double posFloatXzeroInt = 1;

		double negFloatXposOdd = -48.627;
		double negFloatXposEven = -2364.597;
		double negFloatXnegOdd = -0.00154;
		double negFloatXnegEven = -0.00563;
		double negFloatXzeroInt = -1;

		Assert.assertEquals(MathUtils.signedPow(posInt, posOdd), posIntXposOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posInt, posEven), posIntXposEven, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posInt, negOdd), posIntXnegOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posInt, negEven), posIntXnegEven, 0.01);
		Assert.assertEquals(MathUtils.signedPow(posInt, 0), posIntXzeroInt, 0.01);

		Assert.assertEquals(MathUtils.signedPow(negInt, posOdd), negIntXposOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negInt, posEven), negIntXposEven, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negInt, negOdd), negIntXnegOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negInt, negEven), negIntXnegEven, 0.01);
		Assert.assertEquals(MathUtils.signedPow(negInt, 0), negIntXzeroInt, 0.01);

		Assert.assertEquals(MathUtils.signedPow(zeroInt, posOdd), zeroIntXposOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(zeroInt, posEven), zeroIntXposEven, 0.001);
		Assert.assertEquals(MathUtils.signedPow(zeroInt, negOdd), zeroIntXnegOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(zeroInt, negEven), zeroIntXnegEven, 0.01);
		Assert.assertEquals(MathUtils.signedPow(zeroInt, 0), zeroIntXzeroInt, 0.01);

		Assert.assertEquals(MathUtils.signedPow(posFloat, posOdd), posFloatXposOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posFloat, posEven), posFloatXposEven, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posFloat, negOdd), posFloatXnegOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(posFloat, negEven), posFloatXnegEven, 0.01);
		Assert.assertEquals(MathUtils.signedPow(posFloat, 0), posFloatXzeroInt, 0.01);

		Assert.assertEquals(MathUtils.signedPow(negFloat, posOdd), negFloatXposOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negFloat, posEven), negFloatXposEven, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negFloat, negOdd), negFloatXnegOdd, 0.001);
		Assert.assertEquals(MathUtils.signedPow(negFloat, negEven), negFloatXnegEven, 0.01);
		Assert.assertEquals(MathUtils.signedPow(negFloat, 0), negFloatXzeroInt, 0.01);

	}

}