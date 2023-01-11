package com.neeraj.preperation.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.SearchPanes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DataTableUtils {


    private static <S, D> D getValue(S source, Function<S, D> function, D defaultDestination) {
        return Optional.ofNullable(source).map(function).orElse(defaultDestination);
    }

    public static List<SearchPanes.Item> getSearchPanesItems(List<Object[]> groupByCount) {
        return groupByCount.parallelStream()
                .map(item -> new String[]{getValue(item[0], Object::toString, ""), getValue(item[1], Object::toString, "0")})
                .map(item -> new SearchPanes.Item(item[0], item[0], Long.parseLong(item[1]), Long.parseLong(item[1])))
                .toList();
    }

    /**
     * Creates a 'LIMIT .. OFFSET .. ORDER BY ..' clause for the given {@link DataTablesInput}.
     *
     * @return a {@link Pageable}, must not be {@literal null}.
     */
    public static Pageable getPageable(DataTablesInput input) {
        List<Sort.Order> orders = new ArrayList<>();
//        for (Order order : input.getOrder()) {
//            Column column = input.getColumns().get(order.getColumn());
//            if (column.getOrderable()) {
//                String sortColumn = column.getData();
//                Sort.Direction sortDirection = Sort.Direction.fromString(order.getDir());
//                orders.add(new Sort.Order(sortDirection, sortColumn));
//            }
//        }
        Sort sort = orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);

        if (input.getLength() == -1) {
            input.setStart(0);
            input.setLength(Integer.MAX_VALUE);
        }
        return PageRequest.of(input.getStart(), input.getLength(), sort);
    }
}
