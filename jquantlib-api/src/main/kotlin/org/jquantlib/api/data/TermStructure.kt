/*
 Copyright (C) 2019 David Arkell

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.api.data

import java.time.LocalDate

sealed class TermStructure {
  abstract val dayCounter: DayCounter
  abstract val calendarId: String
}

sealed class YieldTermStructure: TermStructure() {
  abstract val referenceDate: LocalDate
  abstract val settlementDays: Int
}

data class FlatForward(
    override val referenceDate: LocalDate,
    override val dayCounter: DayCounter,
    override val settlementDays: Int = 0,
    override val calendarId: String = "",
    val forward: Quote,
    val compounding: Compounding = Compounding.Continuous,
    val frequency: Frequency = Frequency.Annual
): YieldTermStructure()

sealed class VolatilityTermStructure : TermStructure()

sealed class BlackVolTermStructure : VolatilityTermStructure() {
  abstract val referenceDate: LocalDate
  abstract val volatility: Quote
  abstract val bdc: BusinessDayConvention
}

sealed class BlackVolatilityTermStructure : BlackVolTermStructure()

data class BlackConstantVol(
    override val referenceDate: LocalDate,
    override val volatility: Quote,
    override val calendarId: String = "",
    override val dayCounter: DayCounter,
    override val bdc: BusinessDayConvention = BusinessDayConvention.Following
) : BlackVolatilityTermStructure()
