package org.example;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TransactionAnalyzer {
    private List<Transaction> transactions;

    // Метод для розрахунку загального балансу
    public double calculateTotalBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public int countTransactionsByMonth(String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public List<Transaction> findTopExpenses() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0) // Вибірка лише витрат (від'ємні значення)
                .sorted(Comparator.comparing(Transaction::getAmount)) // Сортування за сумою
                .limit(10) // Обмеження результату першими 10 записами
                .collect(Collectors.toList()); // Збір результату в список
    }


    // Найбільша витрата за період (найбільша сума списання, тобто найвід'ємніша сума)
    public Transaction findLargestExpenseByPeriod(String startDate, String endDate) {
        return findExpensesByPeriod(startDate, endDate).stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    // Найменша витрата за період (найменша сума списання, тобто найближча до нуля)
    public Transaction findSmallestExpenseByPeriod(String startDate, String endDate) {
        return findExpensesByPeriod(startDate, endDate).stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    // Витрати (від'ємні суми), дата яких потрапляє у період [startDate; endDate]
    private List<Transaction> findExpensesByPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate from = LocalDate.parse(startDate, formatter);
        LocalDate to = LocalDate.parse(endDate, formatter);

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = LocalDate.parse(t.getDate(), formatter);
                    return !date.isBefore(from) && !date.isAfter(to);
                })
                .collect(Collectors.toList());
    }

    // Сумарні витрати по категоріях (опис виконує роль категорії), додатні значення
    public Map<String, Double> sumExpensesByCategory() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                result.merge(transaction.getDescription(), -transaction.getAmount(), Double::sum);
            }
        }
        return result;
    }

    // Сумарні витрати по місяцях, ключ у форматі MM-yyyy, у хронологічному порядку
    public Map<String, Double> sumExpensesByMonth() {
        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
        Map<String, Double> result = new TreeMap<>(
                Comparator.comparing(key -> YearMonth.parse(key, monthFormatter)));

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                String monthKey = LocalDate.parse(transaction.getDate(), parseFormatter)
                        .format(monthFormatter);
                result.merge(monthKey, -transaction.getAmount(), Double::sum);
            }
        }
        return result;
    }

        // Тут будуть інші методи для аналізу транзакцій
}

