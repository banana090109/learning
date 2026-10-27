package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FindTopExpensesTest {

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = new ArrayList<>();
        // 12 витрат: від -100 до -1200
        for (int i = 1; i <= 12; i++) {
            transactions.add(new Transaction("01-01-2024", -i * 100.0, "Витрата " + i));
        }
        // доходи, які не мають потрапити у топ витрат
        transactions.add(new Transaction("02-01-2024", 5000.0, "Зарплата"));
        transactions.add(new Transaction("03-01-2024", 8000.0, "Фриланс"));

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        List<Transaction> topExpenses = analyzer.findTopExpenses();

        Assertions.assertEquals(10, topExpenses.size(), "Має бути рівно 10 найбільших витрат");

        // Сортування за зростанням суми: найбільша витрата (найвід'ємніша) перша
        Assertions.assertEquals(-1200.0, topExpenses.get(0).getAmount(), 0.0001, "Перша витрата має бути найбільшою");
        Assertions.assertEquals(-300.0, topExpenses.get(9).getAmount(), 0.0001, "Десята витрата має бути -300");

        for (Transaction transaction : topExpenses) {
            Assertions.assertTrue(transaction.getAmount() < 0, "У топі не має бути доходів");
        }

        // Перевірка порядку: суми не зростають від початку до кінця
        for (int i = 1; i < topExpenses.size(); i++) {
            Assertions.assertTrue(
                    topExpenses.get(i).getAmount() >= topExpenses.get(i - 1).getAmount(),
                    "Порядок сортування порушено");
        }
    }
}
