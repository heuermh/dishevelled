/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.dishevelled.variation.interval;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import com.google.common.collect.BoundType;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Ordering;

/**
 * Interval, copied from Range.java and modified to use integers as the domain.
 *
 * @author Kevin Bourrillion
 * @author Gregory Kick
 */
public final class Interval implements Predicate<Integer>, Serializable {

  static Interval create(final Cut<Integer> lowerBound, final Cut<Integer> upperBound) {
    return new Interval(lowerBound, upperBound);
  }

  public static Interval open(final int lower, final int upper) {
    return create(Cut.aboveValue(lower), Cut.belowValue(upper));
  }

  public static Interval closed(final int lower, final int upper) {
    return create(Cut.belowValue(lower), Cut.aboveValue(upper));
  }

  public static Interval closedOpen(final int lower, final int upper) {
    return create(Cut.belowValue(lower), Cut.belowValue(upper));
  }

  public static Interval openClosed(final int lower, final int upper) {
    return create(Cut.aboveValue(lower), Cut.aboveValue(upper));
  }

  public static Interval interval(final int lower, final BoundType lowerType, final int upper, final BoundType upperType) {
    checkNotNull(lowerType);
    checkNotNull(upperType);

    Cut<Integer> lowerBound = (lowerType == BoundType.OPEN)
        ? Cut.aboveValue(lower)
        : Cut.belowValue(lower);
    Cut<Integer> upperBound = (upperType == BoundType.OPEN)
        ? Cut.belowValue(upper)
        : Cut.aboveValue(upper);
    return create(lowerBound, upperBound);
  }

  public static Interval lessThan(final int endpoint) {
    return create(Cut.<Integer>belowAll(), Cut.belowValue(endpoint));
  }

  public static Interval atMost(final int endpoint) {
    return create(Cut.<Integer>belowAll(), Cut.aboveValue(endpoint));
  }

  public static Interval upTo(final int endpoint, final BoundType boundType) {
    switch (boundType) {
      case OPEN:
        return lessThan(endpoint);
      case CLOSED:
        return atMost(endpoint);
      default:
        throw new AssertionError();
    }
  }

  public static Interval greaterThan(final int endpoint) {
    return create(Cut.aboveValue(endpoint), Cut.<Integer>aboveAll());
  }

  public static Interval atLeast(final int endpoint) {
    return create(Cut.belowValue(endpoint), Cut.<Integer>aboveAll());
  }

  public static Interval downTo(final int endpoint, final BoundType boundType) {
    switch (boundType) {
      case OPEN:
        return greaterThan(endpoint);
      case CLOSED:
        return atLeast(endpoint);
      default:
        throw new AssertionError();
    }
  }

  private static final Interval ALL = new Interval(Cut.<Integer>belowAll(), Cut.<Integer>aboveAll());

  public static Interval all() {
    return ALL;
  }

  public static Interval singleton(final int value) {
    return closed(value, value);
  }

  public static Ordering<Interval> ordering() {
    return ORDERING;
  }

  public static Ordering<Interval> reverseOrdering() {
    return REVERSE_ORDERING;
  }

  public static Ordering<Interval> orderingByLowerEndpoint() {
    return BY_LOWER_ENDPOINT;
  }

  public static Ordering<Interval> reverseOrderingByLowerEndpoint() {
    return REVERSE_BY_LOWER_ENDPOINT;
  }

  public static Ordering<Interval> orderingByUpperEndpoint() {
    return BY_UPPER_ENDPOINT;
  }

  public static Ordering<Interval> reverseOrderingByUpperEndpoint() {
    return REVERSE_BY_UPPER_ENDPOINT;
  }

  final Cut<Integer> lowerBound;
  final Cut<Integer> upperBound;

  private Interval(final Cut<Integer> lowerBound, final Cut<Integer> upperBound) {
    if (lowerBound.compareTo(upperBound) > 0 || lowerBound == Cut.<Integer>aboveAll()
        || upperBound == Cut.<Integer>belowAll()) {
      throw new IllegalArgumentException("Invalid interval: " + toString(lowerBound, upperBound));
    }
    this.lowerBound = checkNotNull(lowerBound);
    this.upperBound = checkNotNull(upperBound);
  }

  public boolean hasLowerBound() {
    return lowerBound != Cut.<Integer>belowAll();
  }

  public int lowerEndpoint() {
    return lowerBound.endpoint();
  }

  public BoundType lowerBoundType() {
    return lowerBound.typeAsLowerBound();
  }

  public boolean hasUpperBound() {
    return upperBound != Cut.<Integer>aboveAll();
  }

  public int upperEndpoint() {
    return upperBound.endpoint();
  }

  public BoundType upperBoundType() {
    return upperBound.typeAsUpperBound();
  }

  public boolean isEmpty() {
    return lowerBound.equals(upperBound);
  }

  public boolean contains(final int value) {
    checkNotNull(value);
    return lowerBound.isLessThan(value) && !upperBound.isLessThan(value);
  }

  public int center() {
    if (!hasLowerBound() || !hasUpperBound()) {
      throw new IllegalStateException("cannot calculate the center of an interval without bounds");
    }
    return lowerEndpoint() + (upperEndpoint() - lowerEndpoint()) / 2;
  }

  public boolean isLessThan(final int value) {
    if (!hasUpperBound()) {
      return false;
    }
    if (upperBoundType() == BoundType.OPEN && upperEndpoint() == value) {
      return true;
    }
    return upperEndpoint() < value;
  }

  public boolean isGreaterThan(final int value) {
    if (!hasLowerBound()) {
      return false;
    }
    if (lowerBoundType() == BoundType.OPEN && lowerEndpoint() == value) {
      return true;
    }
    return lowerEndpoint() > value;
  }

  @Override
  public boolean apply(final Integer input) {
    return contains(input);
  }

  public boolean encloses(final Interval other) {
    return lowerBound.compareTo(other.lowerBound) <= 0
        && upperBound.compareTo(other.upperBound) >= 0;
  }

  public boolean isConnected(final Interval other) {
    return lowerBound.compareTo(other.upperBound) <= 0
        && other.lowerBound.compareTo(upperBound) <= 0;
  }

  public boolean intersects(final Interval connectedInterval) {
      return isConnected(connectedInterval) && !intersection(connectedInterval).isEmpty();
  }

  public Interval intersection(final Interval connectedInterval) {
    int lowerCmp = lowerBound.compareTo(connectedInterval.lowerBound);
    int upperCmp = upperBound.compareTo(connectedInterval.upperBound);
    if (lowerCmp >= 0 && upperCmp <= 0) {
      return this;
    }
    else if (lowerCmp <= 0 && upperCmp >= 0) {
      return connectedInterval;
    }
    else {
      Cut<Integer> newLower = (lowerCmp >= 0) ? lowerBound : connectedInterval.lowerBound;
      Cut<Integer> newUpper = (upperCmp <= 0) ? upperBound : connectedInterval.upperBound;
      return create(newLower, newUpper);
    }
  }

  public Interval span(final Interval other) {
    int lowerCmp = lowerBound.compareTo(other.lowerBound);
    int upperCmp = upperBound.compareTo(other.upperBound);
    if (lowerCmp <= 0 && upperCmp >= 0) {
      return this;
    }
    else if (lowerCmp >= 0 && upperCmp <= 0) {
      return other;
    }
    else {
      Cut<Integer> newLower = (lowerCmp <= 0) ? lowerBound : other.lowerBound;
      Cut<Integer> newUpper = (upperCmp >= 0) ? upperBound : other.upperBound;
      return create(newLower, newUpper);
    }
  }

  public Interval canonical() {
    Cut<Integer> lower = lowerBound.canonical(DiscreteDomain.integers());
    Cut<Integer> upper = upperBound.canonical(DiscreteDomain.integers());
    return (lower == lowerBound && upper == upperBound) ? this : create(lower, upper);
  }

  @Override
  public boolean equals(@Nullable final Object object) {
    if (object instanceof Interval) {
      Interval other = (Interval) object;
      return lowerBound.equals(other.lowerBound)
          && upperBound.equals(other.upperBound);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return lowerBound.hashCode() * 31 + upperBound.hashCode();
  }

  @Override
  public String toString() {
    return toString(lowerBound, upperBound);
  }

  private static String toString(final Cut<Integer> lowerBound, final Cut<Integer> upperBound) {
    StringBuilder sb = new StringBuilder(16);
    lowerBound.describeAsLowerBound(sb);
    sb.append('\u2025');
    upperBound.describeAsUpperBound(sb);
    return sb.toString();
  }

  Object readResolve() {
    if (this.equals(ALL)) {
      return all();
    }
    else {
      return this;
    }
  }

  static final Ordering<Interval> ORDERING = new Ordering<Interval>() {
    @Override
    public int compare(final Interval left, final Interval right) {
      return ComparisonChain.start()
          .compare(left.lowerBound, right.lowerBound)
          .compare(left.upperBound, right.upperBound)
          .result();
    }
  };
  static final Ordering<Interval> REVERSE_ORDERING = ORDERING.reverse();

  static final Ordering<Interval> BY_LOWER_ENDPOINT = new Ordering<Interval>() {
    @Override
    public int compare(final Interval left, final Interval right) {
      return ComparisonChain.start()
        .compare(left.hasLowerBound(), right.hasLowerBound())
        .compare(left.lowerEndpoint(), right.lowerEndpoint())
        .result();
    }
  };
  static final Ordering<Interval> REVERSE_BY_LOWER_ENDPOINT = BY_LOWER_ENDPOINT.reverse();

  static final Ordering<Interval> BY_UPPER_ENDPOINT = new Ordering<Interval>() {
    @Override
    public int compare(final Interval left, final Interval right) {
      return ComparisonChain.start()
        .compare(left.hasUpperBound(), right.hasUpperBound())
        .compare(left.upperEndpoint(), right.upperEndpoint())
        .result();
    }
  };
  static final Ordering<Interval> REVERSE_BY_UPPER_ENDPOINT = BY_UPPER_ENDPOINT.reverse();

  private static final long serialVersionUID = 0;
}