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

package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {
  // representation invariant (RI)：The set variable should reference a valid FiniteSet object.
  // The isComplement variable should be a boolean value indicating whether the set is a complement or not.

  // abstraction function (AF)：The SimpleSet object represents a set of points.
  // If isComplement is false, the set is represented by the set variable.
  // If isComplement is true, the set is the complement of the set variable.

  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  private FiniteSet set;
  private boolean isComplement;
  public SimpleSet(float[] vals) {
    set = FiniteSet.of(vals);
    isComplement = false;
  }

  public SimpleSet(FiniteSet set, boolean isComplement) {
    this.set = set;
    this.isComplement = isComplement;
  }

  // HINT: feel free to create other constructors!

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;

    return set.equals(other.set) && isComplement == other.isComplement;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // we want to check if this is a infinite set, and it returns
    // infinity if this represents a infinite set
    if (isComplement){
      return Float.POSITIVE_INFINITY;
    }
    //otherwise, return the number of elements in this finite set
    return set.size();
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    // There will be four cases for toString: When it is an empty set,
    // when it contains {@literal N >= 0} points, when it contains all
    // but {@literal N > 0} points, and when it contains every point.

    // Inv: sb = (points[0]+", ") + (+points[1]+", ") + ... + (points[i-1])

    List<Float> points = set.getPoints();

    // When it is not a Complement, and the size equals zero, it is an empty set.
    if (!isComplement && points.size() == 0) {
      return "{}";
      // When it is not a Complement, and the size does not equals to zero, it is an finite set.
    }else if(!isComplement && points.size() != 0) {
      StringBuilder sb = new StringBuilder("{");
      for (int i = 0; i < points.size(); i++) {
        sb.append(points.get(i));
        if (i < points.size() - 1) {
          sb.append(", ");
        }
      }
      sb.append("}");
      return sb.toString();
      // When it is a Complement, and the size does not equals to zero, it is complement of a finite set.
    }else if(isComplement && points.size() != 0) {
      StringBuilder sb = new StringBuilder("R \\ {");
      for (int i = 0; i < points.size(); i++) {
        sb.append(points.get(i));
        if (i < points.size() - 1) {
          sb.append(", ");
        }
      }
      sb.append("}");
      return sb.toString();
      // When it is a Complement, and the size equals to zero, it contains every point.
    }else{
      return "R";
    }
    }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // The boolean value of the complement is always the opposite with the boolean value of set, so we just negate the
    // boolean value in the return statement.
    // If set is infinite, isComplement would be true, the complement of it would be
    // finite set, so isComplement is false.
    // If set is finite, isComplement would be false, so complement of it would be
    // infinite set, so isComplement is true.
    return new SimpleSet(set, !isComplement);
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    boolean thisIsComplement = isComplement;
    boolean otherIsComplement = other.isComplement;

    // Each of the two sets can be either finite or finite complement,
    // so there are four possible combinations.

    // first case: finite set union finite set:
    // since both of them are finite set, we can just call the union method from FiniteSet;
    // and we set the isComplement parameter to be false because the unionS is already the result.
    if(!thisIsComplement && !otherIsComplement ){
      FiniteSet thisFinS = set;
      FiniteSet otherFinS = other.set;
      FiniteSet unionS = thisFinS.union(otherFinS);
      return new SimpleSet(unionS, false);
    }

    // second case: finite set union complement:
    // The union of a finite set A and a complement of another set B is everything other than B difference A,
    // so after setting unionS to be B difference A, we set isComplement to be true for the returning SimpleSet object
    // which represents the complement of unionS.
    else if(!thisIsComplement && otherIsComplement){
      FiniteSet thisFinS = set;
      FiniteSet otherComS = other.complement().set;
      FiniteSet unionS = otherComS.difference(thisFinS);
      return new SimpleSet(unionS, true);
    }

    // third case: complement union finite set:
    // The union of the complement of set A and a finite set B is everything other than A difference B,
    // so after setting unionS to be A difference B, we set isComplement to be true for the returning SimpleSet object
    // which represents the complement of unionS.
    else if(thisIsComplement && !otherIsComplement){
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherFinS = other.set;
      FiniteSet unionS = thisComS.difference(otherFinS);
      return new SimpleSet(unionS, true);
    }

    // fourth case: complement union complement:
    // The union of the complement of set A and the complement of set B is everything other than the intersection of A
    // and B, so after setting unionS to be A intersection B, we set isComplement to be true for the returning SimpleSet object
    // which represents the complement of unionS.
    else{
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherComS = other.set;
      FiniteSet unionS = thisComS.intersection(otherComS);
      return new SimpleSet(unionS, true);
    }

  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersected with other
   */
  public SimpleSet intersection(SimpleSet other) {
    boolean thisIsComplement = isComplement;
    boolean otherIsComplement = other.isComplement;

    // first case: finite set intersection finite set:
    // since both of them are finite set, we can just call the intersection method from FiniteSet;
    // and we set the isComplement parameter to be false because the interS is already the result.
    if(!thisIsComplement && !otherIsComplement ){
      FiniteSet thisFinS = set;
      FiniteSet otherFinS = other.set;
      FiniteSet interS = thisFinS.intersection(otherFinS);
      return new SimpleSet(interS, false);
    }

    // second case: finite set intersection complement:
    // The intersection of a finite set A and a complement of another set B is A difference B,
    // so after setting interS to be A difference B, we set isComplement to be false for the returning SimpleSet object
    // which represents NOT the complement of interS.
    else if(!thisIsComplement && otherIsComplement){
      FiniteSet thisFinS = set;
      FiniteSet otherComS = other.complement().set;
      FiniteSet interS = thisFinS.difference(otherComS);
      return new SimpleSet(interS, false);
    }

    // third case: complement intersection finite set:
    // The intersection of the complement of set A and a finite set B is B difference A,
    // so after setting interS to be B difference A, we set isComplement to be false for the returning SimpleSet object
    // which represents NOT the complement of interS.
    else if(thisIsComplement && !otherIsComplement){
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherFinS = other.set;
      FiniteSet interS = otherFinS.difference(thisComS);
      return new SimpleSet(interS, false);
    }

    // fourth case: complement intersection complement:
    // The intersection of the complement of set A and the complement of set B is everything else other than A union B,
    // so after setting interS to be A union B, we set isComplement to be true for the returning SimpleSet object
    // which represents the complement of interS.
    else{
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherComS = other.set;
      FiniteSet interS = thisComS.union(otherComS);
      return new SimpleSet(interS, true);
    }

  }
  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    boolean thisIsComplement = isComplement;
    boolean otherIsComplement = other.isComplement;

    // first case: finite set difference finite set:
    // since both of them are finite set, we can just call the difference method from FiniteSet;
    // and we set the isComplement parameter to be false because the diffS is already the result.
    if(!thisIsComplement && !otherIsComplement ){
      FiniteSet thisFinS = set;
      FiniteSet otherFinS = other.set;
      FiniteSet diffS = thisFinS.difference(otherFinS);
      return new SimpleSet(diffS, false);
    }

    // second case: finite set difference complement:
    // The difference of a finite set A and a complement of another set B is A intersection B,
    // so after setting diffS to be A intersection B, we set isComplement to be false for the returning SimpleSet object
    // which represents NOT the complement of diffS.
    else if(!thisIsComplement && otherIsComplement){
      FiniteSet thisFinS = set;
      FiniteSet otherComS = other.complement().set;
      FiniteSet diffS = thisFinS.intersection(otherComS);
      return new SimpleSet(diffS, false);
    }

    // third case: complement difference finite set:
    // The difference of the complement of set A and a finite set B is everything else other than the union of A and B,
    // so after setting diffS to be A union B, we set isComplement to be true for the returning SimpleSet object
    // which represents the complement of diffS.
    else if(thisIsComplement && !otherIsComplement){
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherFinS = other.set;
      FiniteSet diffS = thisComS.union(otherFinS);
      return new SimpleSet(diffS, true);
    }

    // fourth case: complement difference complement:
    // The difference of the complement of set A and the complement of set B is B difference A,
    // so after setting diffS to be B difference A, we set isComplement to be false for the returning SimpleSet object
    // which NOT represents the complement of diffS.
    else{
      FiniteSet thisComS = this.complement().set;
      FiniteSet otherComS = other.set;
      FiniteSet diffS = otherComS.difference(thisComS);
      return new SimpleSet(diffS, false);
    }

  }

}
