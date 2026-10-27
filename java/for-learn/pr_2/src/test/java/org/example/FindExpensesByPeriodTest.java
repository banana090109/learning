package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class FindExpensesByPeriodTest {

    private TransactionAnalyzer createAnalyzer() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("12-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("20-12-2023", -750.0, "Подарунки"),
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("10-12-2023", 8000.0, "Зарплата") // дохід, ігнорується
        );
        return new TransactionAnalyzer(transactions);
    }

    @Test
    public void testLargestExpenseByPeriod() {
        TransactionAnalyzer analyzer = createAnalyzer();

        Transaction largest = analyzer.findLargestExpenseByPeriod("01-12-2023", "31-12-2023");

        Assertions.assertNotNull(largest);
        Assertions.assertEquals(-3200.0, largest.getAmount(), 0.0001);
        Assertions.assertEquals("Оренда квартири", largest.getDescription());
    }

    @Test
    public void testSmallestExpenseByPeriod() {
        TransactionAnalyzer analyzer = createAnalyzer();

        Transaction smallest = analyzer.findSmallestExpenseByPeriod("01-12-2023", "31-12-2023");

        Assertions.assertNotNull(smallest);
        Assertions.assertEquals(-450.0, smallest.getAmount(), 0.0001);
        Assertions.assertEquals("Сільпо", smallest.getDescription());
    }

    @Test
    public void testExpensesOutsidePeriodAreIgnored() {
        TransactionAnalyzer analyzer = createAnalyzer();

        // Січневі витрати не потрапляють у грудневий період
        Transaction largest = analyzer.findLargestExpenseByPeriod("01-12-2023", "31-12-2023");
        Assertions.assertEquals("Оренда квартири", largest.getDescription());

        Transaction largestJan = analyzer.findLargestExpenseByPeriod("01-01-2024", "31-01-2024");
        Assertions.assertEquals(-500.0, largestJan.getAmount(), 0.0001);
    }

    @Test
    public void testNoExpensesInPeriodReturnsNull() {
        TransactionAnalyzer analyzer = createAnalyzer();

        Assertions.assertNull(analyzer.findLargestExpenseByPeriod("01-06-2024", "30-06-2024"));
        Assertions.assertNull(analyzer.findSmallestExpenseByPeriod("01-06-2024", "30-06-2024"));
    }
}
