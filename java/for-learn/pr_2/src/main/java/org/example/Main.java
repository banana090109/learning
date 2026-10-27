package org.example;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String filePath = Main.class.getResource("/pr_2.csv").toExternalForm();
        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions(filePath);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        TransactionReportGenerator reportGenerator = new TransactionReportGenerator();
        double totalBalance = analyzer.calculateTotalBalance();
//        System.out.println("Загальний баланс: " + totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = analyzer.countTransactionsByMonth(monthYear);
//        System.out.println("Кількість транзакцій за " + monthYear + ": " + transactionsCount);

        reportGenerator.printBalanceReport(totalBalance);
        reportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = analyzer.findTopExpenses();
        reportGenerator.printTopExpensesReport(topExpenses);

        reportGenerator.printExpensesByCategoryReport(analyzer.sumExpensesByCategory());
        reportGenerator.printExpensesByMonthReport(analyzer.sumExpensesByMonth());

    }
}


