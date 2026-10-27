package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SumExpensesTest {

    private TransactionAnalyzer createAnalyzer() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("05-12-2023", -450.0, "Сільпо"),
                new Transaction("20-12-2023", -750.0, "Сільпо"),
                new Transaction("12-12-2023", -3200.0, "Оренда квартири"),
                new Transaction("05-01-2024", -500.0, "Сільпо"),
                new Transaction("10-12-2023", 8000.0, "Зарплата") // дохід, ігнорується
        );
        return new TransactionAnalyzer(transactions);
    }

    @Test
    public void testSumExpensesByCategory() {
        Map<String, Double> result = createAnalyzer().sumExpensesByCategory();

        Assertions.assertEquals(3, result.size(), "Має бути 3 категорії витрат");
        Assertions.assertEquals(1700.0, result.get("Сільпо"), 0.0001);
        Assertions.assertEquals(3200.0, result.get("Оренда квартири"), 0.0001);
        Assertions.assertNull(result.get("Зарплата"), "Доходи не мають потрапляти у витрати");
    }

    @Test
    public void testSumExpensesByMonth() {
        Map<String, Double> result = createAnalyzer().sumExpensesByMonth();

        Assertions.assertEquals(2, result.size(), "Має бути 2 місяці витрат");
        Assertions.assertEquals(4400.0, result.get("12-2023"), 0.0001);
        Assertions.assertEquals(500.0, result.get("01-2024"), 0.0001);

        // Хронологічний порядок: 12-2023 перед 01-2024
        List<String> months = List.copyOf(result.keySet());
        Assertions.assertEquals(Arrays.asList("12-2023", "01-2024"), months);
    }
}
