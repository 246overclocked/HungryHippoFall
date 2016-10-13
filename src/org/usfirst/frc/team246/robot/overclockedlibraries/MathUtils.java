package org.usfirst.frc.team246.robot.overclockedLibraries;

public class MathUtils {

	/**
	 * Take the power of a double, preserving negativity.
	 *
	 * @param base
	 *            the double base
	 * @param exponent
	 *            the integer exponent
	 * @return The signed power of a double, where a negative power of zero
	 *         returns zero and the zero'th power of zero returns one
	 */
	public static double signedPow(double base, int exponent) {
		if (exponent > 0) {
			return (Math.abs(Math.pow(base, (exponent - 1))) * base);
		} else if (exponent < 0) {
			if (base == 0) {
				return 0;
			} else {
				return (Math.pow(base, -1) * Math.abs(Math.pow(base, exponent + 1)));
			}
		} else {
			if (base >= 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}

}
