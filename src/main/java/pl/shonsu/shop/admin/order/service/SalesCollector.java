package pl.shonsu.shop.admin.order.service;

import pl.shonsu.shop.admin.order.model.AdminOrder;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class SalesCollector implements Collector<AdminOrder, DaySale, DaySale> {
    @Override
    public Supplier<DaySale> supplier() {
        return DaySale::new;
    }

    @Override
    public BiConsumer<DaySale, AdminOrder> accumulator() {
        return (daySale, adminOrder) -> {
            daySale.setSale(daySale.getSale().add(adminOrder.getGrossValue()));
            daySale.setCount(daySale.getCount() + 1);
        };
    }

    @Override
    public BinaryOperator<DaySale> combiner() {
        return (daySale, daySale2) -> {
            daySale.setSale(daySale.getSale().add(daySale2.getSale()));
            daySale.setCount(daySale.getCount() + 1);
            return daySale;
        };
    }

    @Override
    public Function<DaySale, DaySale> finisher() {
        return daySale -> {
            return daySale;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
