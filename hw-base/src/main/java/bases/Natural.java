/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package bases;

import java.util.Arrays;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Represents an immutable, non-negative integer value along with a base in
 * which to print its digits, which we can think of as a pair (base, value).
 * For example, (2, 5) represents the integer 5 (in decimal), but it will show
 * its digits as 101 (in binary) when printed.
 * <p>
 * We require that the base is at least 2 and at most 36 for simplicity.
 */
public class Natural {

    /**
     * Returns the digits of value from lowest to highest when written in the
     * given base.
     *
     * @param base the base used to represent numbers with digits
     * @param value the value we want to get digits from
     * @spec.requires {@literal 2 <= base <= 36 and 0 < value}
     * @return the digits of value when written in the given base, with the
     *      lowest value digits appearing first in the array
     */
    public static int[] getDigits(int base, int value) {
        assert 2 <= base && base <= 36;
        assert value > 0;

        // Find the largest exponent needed to express this value in this base.
        // This is the integer k such that base^k <= value < base^{k+1}. The
        // following expression solves base^k <= value for k:
        int maxExp = (int) Math.ceil(Math.log(value) / Math.log(base));

        // Create an array with enough room for all of these digits. (Having extra
        // zero digits wouldn't be a problem, although that won't happen here.)
        int[] digits = new int[maxExp + 1];

        int i = 0;
        int r = value;

        // Inv: 0 <= D[0] < b, 0 <= D[1] < b, ..., 0 <= D[i-1] < b, and
        //      D[i] = D[i+1] = ... = D[n-1] = 0 and
        //      value = D[0] + D[1] b + ... + D[i-1] b^{i-1} + b^i * r,
        //      where D = digits, n = digits.length, and b = base.
        while (r != 0) {
            digits[i] = r % base;
            r = r / base;
            i = i + 1;
        }

        // Post: 0 <= D[0] < b, 0 <= D[1] < b, ..., 0 <= D[n-1] < b and
        //       value = D[0] + D[1] b + D[2] b^2 + ... + D[n-1] b^{n-1}
        return digits;
    }

    /**
     * Returns the index of the first non-zero digit, starting from the right.
     *
     * @param digits the non-null list of digits we want to get the leading digit from
     * @spec.requires digits != null and digits.length &gt; 0
     * @return the index of the first non-zero digit, starting from the right, or
     *     0 if none are non-zero
     */
    public static int leadingDigit(int[] digits) {

        int i = digits.length - 1;
        // Inv: D[i+1] = ... = D[n-1] = 0 where D = digits
        while (i!=0 && digits[i] == 0){
                i--;
        }
        // At this point in the code, we know that loop exits at i and D[i].
        // This implies the post-condition below, since when the loop exits at i, it's either
        // because the nonzero digit (D[i] != 0) is found and being returned, which means every element
        // checked before from D[i+1] to D[n-1] are all zero, or because it reaches the end of the array
        // where i = 0 and all of the elements checked before, from D[i+1] to D[n-1], are zero.

        // Post: D[i+1], ..., D[n-1] are all zero and (D[i] != 0 or i = 0)
        return i;
    }

    // Shorthand: b = this.base, D = this.digits, and n = this.digits.length
    //
    //   RI: 2 <= b <= 36 and D != null and n >= 1 and
    //       if n > 1, then D[n-1] != 0 (no leading zeros) and
    //       for i = 0 .. n-1, we have 0 <= D[i] < b
    //
    //   AF(this) = (b, D[0] + D[1] b + D[2] b^2 + ... + D[n-1] b^{n-1})

    /** The base of this. */
    private final int base;

    /** The digits of this in terms of base. */
    private final int[] digits;

    /**
     * Creates the value (base, 0).
     *
     * @param base the base used to represent numbers with digits
     * @spec.requires {@literal 2 <= base <= 36}
     */
    public Natural(int base) {
        this(base, new int[] { 0 });
    }

    /**
     * Creates the value (base, value)
     *
     * @param base the base used to represent numbers with digits
     * @param value the integer value to be represented by this natural number
     * @spec.requires {@literal 2 <= base <= 36 and 0 <= value}
     */
    public Natural(int base, int value) {
        // The method getDigits requires value > 0, so we need to handle the case
        // where value = 0 here. (If value < 0, getDigits will throw an exception.)
        this(base, (value == 0) ? new int[] { 0 } : getDigits(base, value));
    }

    /**
     * Creates an integer with the given digits in the given base. The digits
     * should be provided with the smaller valued positions at the front of the
     * array. For example, 321 base 10 would be passed in as [1, 2, 3].
     *
     * @param base the base used to represent numbers with digits
     * @param digits the non-null list of digits in the given base
     * @spec.requires {@literal 2 <= base <= 36 and digits.length >= 1 and
     *      all digits are between 0 and base - 1}
     */
    public Natural(int base, int[] digits) {
        assert 2 <= base && base <= 36;
        assert digits.length >= 1;
        for (int i = 0; i < digits.length; i++) {
            assert 0 <= digits[i] && digits[i] < base;
        }

        // While the RI does not allow leading zeros, we did not include that
        // restriction above, so we must remove them here.
        this.digits = Arrays.copyOf(digits, leadingDigit(digits)+1);
        this.base = base;

        assert this.digits.length == 1 || this.digits[this.digits.length-1] != 0;
    }

    /**
     * Returns the base of this.
     *
     * @return the base used to represent this integer.
     */
    public int getBase() {
        return base;
    }

    /**
     * Returns the value of this.
     *
     * @return the value of this integer
     */
    public int getValue() {
        // NOTE: this is essentially just evalPoly1 from HW2

        int i = this.digits.length - 1;
        int j = 0;
        int val = this.digits[i];

        // Inv: val = D[i] b^0 + D[i+1] b^1 + ... + D[n-1] b^j and i+j = n - 1,
        //      where D = this.digits, n = this.digits.length, and b = this.base
        while (j != this.digits.length - 1) {
            j = j + 1;
            i = i - 1;
            val = val * this.base + this.digits[i];
        }

        // Post: val = D[0] + D[1] b + D[2] b^2 + ... + D[n-1] b^{n-1}
        return val;
    }

    /**
     * Returns this, converted to be in terms of the given base.
     *
     * @param base the base used to represent numbers with digits
     * @spec.requires {@literal 2 <= base <= 36}
     * @return (base, this.value), i.e., the same value but in the given base
     */
    public Natural toBase(int base) {
        assert 2 <= base && base <= 36;

        // Hints: 1. Compare this invariant to the one from getValue above
        //        2. Take advantage of the existing methods, plus and times.

        Natural r = new Natural(base);
        Natural b = new Natural(base, this.base);

        int i = this.digits.length - 1;
        int j = 0;
        r = new Natural(base, this.digits[i]);

        // Inv: r = (base, D[i] b^0 + D[i+1] b^1 + ... + D[n-1] b^j) and i+j = n-1,
        //      where D = this.digits, n = this.digits.length, and b = this.base.
        while (j != this.digits.length - 1) {

            j = j + 1;
            i = i - 1;
            r = r.times(b).plus(new Natural(base, this.digits[i]));

        }
        // The post-condition holds as we already converted this value to the intended base and
        // assigned this value to r after the while loop.

        // Post: r = (base, this.value)
        return r;
    }

    /**
     * Returns a string representation of this Natural.
     *
     * @return The string of digits corresponding to this value in this base.
     */
    public String toString() {

        // Precondition: 2 <= b <= 36 and D != null and n >= 1 and
        //       if n > 1, then D[n-1] != 0 (no leading zeros) and
        //       for i = 0 .. n-1, we have 0 <= D[i] < b

        StringBuilder buf = new StringBuilder();
        // Inv: buf.length <= n.length where D = digits and n = digits.length
        // buf = ch(D[n-1]), ch(D[n-2]), ..., ch(D[0])

        for (int i = digits.length - 1; i>=0; i--){
            char d = BaseDigits.digitToChar(digits[i], base);
            buf.append(d);
        }

        // At this point in the code, we know that the loop exits and finished converting to string.
        // The post-condition holds, since all the digits are converted to char and are appended to buf from right to left,
        // from n-1 to 0.

        // Post: buf = ch(D[n-1]), ch(D[n-2]), ..., ch(D[0])
        return buf.toString();
    }

    /**
     * Produces the sum of this and other.
     *
     * @param other the natural number we are adding to this
     * @spec.requires other.base == this.base
     * @return (this.base, this.value + other.value)
     */
    public Natural plus(Natural other) {
        assert other != null;
        assert other.base == this.base;

        // We can simplify our code below if we know which number has more digits.
        // If the other one has more, we will let it calculate plus instead. (This
        // is fair since addition is commutative, i.e., x + y = y + x.)
        if (other.digits.length > this.digits.length) {
            return other.plus(this);  // will produce the required value
        }

        // We now have: other.digits.length <= this.digits.length

        // The sum may need one more digit than we have, so we will allocate space
        // for one extra digit and set it initially to zero. It is fine if this
        // zero is not replaced as leading zeros are allowed by the constructor.
        int[] newDigits = new int[this.digits.length + 1];

        // The next two loops add the corresponding digits of this and other into
        // the array newDigits. The first loop handles the digits that exist in
        // both this and other, and the second loop handles the digits that exist
        // only in this (i.e., when other is shorter). After the loops, newDigits

        // will represent the correct value, but it it will not yet satisfy the RI.


        int i = 0;

        // This Inv states that for each index k from 0 to i-1, the value of D[k] is equal to the sum of the
        // corresponding elements from arrays A and B.
        // Inv: D[0] = A[0]+B[0], D[1] = A[1]+B[1], ..., D[i-1] = A[i-1]+B[i-1],
        //      where D = new_digits, A = this.digits, and B = other.digits
        while (i != other.digits.length) {

            newDigits[i] = this.digits[i] + other.digits[i];
            i++;
        }

        // The reason why we have D[0] < 2b-1, D[1] < 2b-1, ..., D[n-1] < 2b-1
        // is that from the RI, we know that for i = 0 .. n-1, we have 0 <= D[i] < b,
        // since now that we have an Inv: D[0] = A[0]+B[0], D[1] = A[1]+B[1], ..., D[i-1] = A[i-1]+B[i-1],
        // we know that the maximum length A[i]+B[i] can reach would be shorter than 2b-1.

        // The reason why the invariant of the loop below holds initially is that we are doing element-wise
        // addition, and when the other.digits is running out of elements, we can make A[k] and B[k] as the same length,
        // where everything after the last element of other.digit is 0 for B[k],
        // so the element-wise addition still works.

        // This Inv states that for each index k from 0 to i-1, the value of D[k] is equal to the sum of the
        // corresponding elements from arrays A and B. The values of array B are determined
        // based on the conditions provided: if index k < length of other.digits,
        // then B[k] = other.digits[k]. Otherwise, if index k >= the length of other.digits, B[k] = 0.
        // Inv: D[0] = A[0]+B[0], D[1] = A[1]+B[1], ..., D[i-1] = A[i-1]+B[i-1],
        //      where B[k] = other.digits[k] if k < other.digits.length and
        //            B[k] = 0               otherwise
        while (i != this.digits.length) {

            if (i < other.digits.length) {
                newDigits[i] = this.digits[i] + other.digits[i];
            } else {
                newDigits[i] = this.digits[i] + 0;
            }
            i++;

        }

        // Have: D[0] = A[0]+B[0], D[1] = A[1]+B[1], ..., D[n-1] = A[n-1]+B[n-1],
        //       where n = this.digits.length and B is as defined above
        checkZipSum(other.digits, this.digits, newDigits);

        // They cause the code to always return null, which we only want to do
        // while you are working on the two loops above. Once those work, remove
        // these lines and start on the third and final loop below.

        // The reason why we have this.value + other.value = D[0] + D[1] b + ... + D[n-1] b^{n-1}
        // is that The expression this.value + other.value represents the decimal sum of two numbers.
        // The digit representation of a number in base b can be expressed as D[n-1] * b^(n-1) + D[n-2] * b^(n-2) + ... + D[1] * b + D[0],
        // where D[n-1], D[n-2], ..., D[1], D[0] are the digits of the number.


        // The next loop changes the values in newDigits so that it satisfies the
        // part of the RI that says each digit is between 0 and b-1. It does this
        // *without* changing the value that the digits represent, so they will
        // still represent the value this.value + other.value.

        i = 0;

        // Inv: this.value + other.value = D[0] + D[1] b + ... + D[n] b^n and
        //      D[0] < b, D[1] < b, ..., D[i-1] < b and
        //      D[i] < 2b and D[i+1] < 2b-1, D[i+2] < 2b-1, ..., D[n-1] < 2b-1
        while (i != newDigits.length) {

            // NOTE: Do not use div or mod. Simple arithmetic should be enough.

            // Hint: Subtracting b from D[i] while adding 1 to D[i+1] does not change
            //       the value that these digits represent since
            //
            //   (D[i] - b) b^i + (D[i+1] + 1) b^{i+1}
            //   = D[i] b^i - b^{i+1} + D[i+1] b^{i+1} + b^{i+1}
            //   = D[i] b^i + D[i+1] b^{i+1]

            if (newDigits[i] > this.base-1){
                newDigits[i] = newDigits[i] - this.base;
                newDigits[i + 1] += 1;
            }

            i = i + 1;  // NOTE: do not change this line
        }


        // post-condition: this.value + other.value = D[0] + D[1] b + ... + D[n] b^n and
        //                0 < D[0] < b-1, 0 < D[1] < b-1, ..., 0 < D[i-1] < b-1 and
        //                0 < D[i] < b-1 and 0 < D[i+1] < b-1, 0 < D[i+2] < b-1, ..., 0 < D[n-1] < b-1

        // The post-condition holds because the loop just makes sure that each digit is between 0 and b-1
        // without changing the value that the digits represent, so they will still represent the
        // value this.value + other.value.

        // The precondition 2 <= base <= 36 and digits.length >= 1 and
        // because the while loop makes sure that all digits are between 0 and base - 1
        // These steps ensure that the digit newDigits[i] is adjusted within the range of 0 to base - 1.
        // By subtracting base from newDigits[i], any excess value that is beyond the valid range is
        // wrapped around back into the valid range. The addition of 1 to the next digit newDigits[i+1] ensures
        // that the value of the digit newDigits[i+1] is adjusted to account for the carry that occurred
        // when the digit newDigits[i] was adjusted.

        return new Natural(this.base, newDigits);


    }

    /**
     * Helper method to check that C is the element-wise sum of the entries in A
     * and B. We allow A to be shorter than B and B to be shorter than C. Missing
     * entries are treated as zero.
     *
     * @param A the first array to use in the sum check
     * @param B the second array to use in the sum check
     * @param C the array that we want to check is a element-wise sum of A and B
     * @spec.requires {@literal A.length <= B.length <= C.length}
     */
    private static void checkZipSum(int[] A, int[] B, int[] C) {
        assert A.length <= B.length;
        assert B.length <= C.length;

        for (int j = 0; j < B.length; j++) {
            assert C[j] == B[j] + (j < A.length ? A[j] : 0);
        }
    }

    /**
     * Produces a number whose digits, in this base, are the result of taking the
     * digits of this number and shifting them to the left m positions, writing
     * zeros in the now empty positions.
     *
     * @param m the number of positions we want the digits to shift left
     * @return (this.base, this.value * this.base^m)
     */
    public Natural leftShift(int m) {
        assert m >= 0;

        int[] digits = new int[this.digits.length + m];
        System.arraycopy(this.digits, 0, digits, m, this.digits.length);

        // Now: digits = [0, ..., 0, D[0], D[1], ..., D[n-1]], with m 0s at front,
        //      where D = this.digits and n = this.digits.length

        // The value of these digits are
        //    0 + 0 b + ... + 0 b^{m-1} + D[0] b^m + ... + D[n-1] b^{m+n-1}
        //     = D[0] b^m + ... + D[n-1] b^{m+n-1}
        //     = b^m (D[0] + D[1] b + ... + D[n-1] b^{n-1})
        //     = b^m * this.value
        return new Natural(this.base, digits);
    }

    /**
     * Produces the product of this and (base, val).
     *
     * @param val the integer value we want to multiply this by.
     * @spec.requires {@literal 0 <= val < this.base}
     * @return (this.base, this.value * val)
     */
    public Natural times(int val) {
        assert 0 <= val && val < this.base;

        int[] digits = new int[this.digits.length+1];
        int i = 0;

        // Inv: D[0] = v * A[0]], D[1] = v * A[1]], ..., D[i-1] = v * A[i-1],
        //      where D = digits, A = this.digits, and v = val
        while (i != this.digits.length) {
            digits[i] = val * this.digits[i];
            i = i + 1;
        }

        // Have: D[0] + D[1] b + ... + D[n-1] b^{n-1}
        //        = v * A[0] + v * A[1] b + ... + v * A[n-1] b^{n-1}
        //        = v * (A[0] + A[1] b + ... + A[n-1] b^{n-1})
        //        = v * this.value

        i = 0;

        // Note: if v <= b-1 and w <= b-1, then vw <= (b-1)^2 = b^2 - 2b + 1.
        // Hence, we have D[k] <= (b-1)^2 for k = 0 .. n-1.

        // Inv: D[0] + D[1] b + ... + D[n-1] b^{n-1} + D[n] = v * this.value and
        //      D[0] < b, D[1] < b, ..., D[i-1] < b and
        //      D[i] <= (b-1)^2 + b-1 and D[i+1] <= (b-1)^2, ..., D[n-1] <= (b-1)^2
        while (i != this.digits.length) {
            // It follows from the Division Theorem that the following two lines do
            // not change D[0] + D[1] b + ... + D[n-1] b^{n-1} + D[n].
            //
            // Since D[i] <= (b-1)^2 + b-1 = b^2 - b, we have D[i] / b <= b - 1, so
            // the first line leaves us with D[i+1] <= (b-1)^2 + b-1.

            digits[i+1] += digits[i] / this.base;
            digits[i] = digits[i] % this.base;

            i = i + 1;
        }

        // We now have D[0] < b, D[1] < b, ..., D[n-1] < b. However, we also need
        // to argue that D[n] < b. It was zero before the loop and only updated in
        // the last iteration, where, as noted above, we added at most b-1 to it,
        // so we end up with D[n] <= 0 + b-1 < b.

        // Exiting the loop, we know that digits represents val * this.value and
        // that every digit is between 0 and b-1 (so we can create this object).
        return new Natural(this.base, digits);
    }

    /**
     * Produces the product of this and other.
     *
     * @param other the natural number we want to multiply this by
     * @spec.requires other.base == this.base
     * @return (this.base, this.value * other.value)
     */
    public Natural times(Natural other) {
        assert other != null;
        assert other.base == this.base;

        int i = this.digits.length;
        int j = -1;
        Natural r = new Natural(this.base);

        // Inv: r.value = other * (D[i] + D[i+1] b + ... + D[n-1] b^j) and i+j=n-1,
        //      where D = this.digits, n = this.digits.length, and b = this.base
        while (j != this.digits.length - 1) {
            j = j + 1;
            i = i - 1;
            // Now: r.value = other * (D[i+1] + D[i+2] b + ... + D[n-1] b^{j-1})
            r = r.leftShift(1);
            // r.value = other * (D[i+1] b + D[i+2] b^2 + ... + D[n-1] b^j)
            r = r.plus(other.times(this.digits[i]));
            // r.value = other*D[i] + other*(D[i+1] b + D[i+2] b^2 + ... + D[n-1] b^j)
            //         = other * (D[i] + D[i+1] b + D[i+2] b^2 + ... + D[n-1] b^j)
        }

        // Have: r.value = other * (D[0] + D[1] b + ... + D[n-1] b^{n-1})
        //               = other * value
        return r;
    }

    /**
     * Entry point to manually test base conversion.
     *
     * @param args Command line arguments provided to the program (ignored).
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in, UTF_8.name());

        System.out.println("Enter first number: ");
        Natural x10 = new Natural(10, console.nextInt());
        Natural x2 = x10.toBase(2);
        System.out.println("In binary, the number you entered was " + x2);
        System.out.println();

        System.out.println("Enter second number: ");
        Natural y10 = new Natural(10, console.nextInt());
        Natural y2 = y10.toBase(2);
        System.out.println("In binary, the number you entered was " + y2);
        System.out.println();

        Natural sum = x2.plus(y2).toBase(10);
        System.out.println("The sum of the two numbers is: " + sum);
    }
}
