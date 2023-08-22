package com.devtalk.consultation.consultationservice.global.vo;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
public class Money {

    public static final Money ZERO = Money.of(0);

    private BigDecimal amount;


    Money(Integer amount) {
        if(amount < 0) throw new IllegalArgumentException();
        this.amount = BigDecimal.valueOf(amount);
    }

    Money(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException();
        this.amount = amount;
    }

    public static Money of(int amount) {
        return new Money(amount);
    }

    public Money minus(Money money) {
        return new Money(amount.subtract(money.amount));
    }

    public Money plus(Money money) {
        return new Money(amount.add(money.amount));
    }

    public Money times(double percent) {
        return new Money(amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean equalsOrMore(Money o) {
        return amount.compareTo(o.amount) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount.toBigInteger(), money.amount.toBigInteger());
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
