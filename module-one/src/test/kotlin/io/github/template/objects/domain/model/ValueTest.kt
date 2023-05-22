package io.github.template.objects.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

internal class ValueTest : FreeSpec() {

    companion object {
        private const val CURRENCY = "UAH"
    }

    init {
        "The Value type" - {

            "empty test" {
                val value = Value(amount = Amount(BigDecimal.ZERO), currency = Currency(CURRENCY))
                value.amount.get shouldBe BigDecimal.ZERO
                value.currency.get shouldBe CURRENCY
            }
        }
    }
}
