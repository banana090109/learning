package org.example;

import java.util.List;
import java.util.Map;

public class TransactionReportGenerator {

    // Кожен символ * відповідає 1000 грн витрат
    private static final double VALUE_PER_STAR = 1000.0;

    public void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public void printExpensesByCategoryReport(Map<String, Double> expensesByCategory) {
        System.out.println();
        System.out.println("Сумарні витрати по категоріях (1 * = " + (int) VALUE_PER_STAR + " грн):");
        printExpenseBars(expensesByCategory);
    }

    public void printExpensesByMonthReport(Map<String, Double> expensesByMonth) {
        System.out.println();
        System.out.println("Сумарні витрати по місяцях (1 * = " + (int) VALUE_PER_STAR + " грн):");
        printExpenseBars(expensesByMonth);
    }

    private void printExpenseBars(Map<String, Double> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("Витрат немає");
            return;
        }

        int labelWidth = expenses.keySet().stream().mapToInt(String::length).max().orElse(0);

        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            double sum = entry.getValue();
            int stars = (int) Math.max(1, Math.round(sum / VALUE_PER_STAR));
            System.out.printf("%-" + labelWidth + "s %10.2f грн  %s%n",
                    entry.getKey(), sum, "*".repeat(stars));
        }

        double total = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        int totalStars = (int) Math.max(1, Math.round(total / VALUE_PER_STAR));
        System.out.printf("%-" + labelWidth + "s %10.2f грн  %s%n", "Разом", total, "*".repeat(totalStars));
    }
}
