package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

public class TransactionCSVReaderTest {

    @Test
    public void testReadTransactionsFromCsv() {
        URL url = TransactionCSVReaderTest.class.getResource("/pr_2.csv");
        Assertions.assertNotNull(url, "Файл pr_2.csv не знайдено у ресурсах");

        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions(url.toExternalForm());

        Assertions.assertEquals(14, transactions.size(), "Кількість транзакцій у CSV неправильна");

        Transaction first = transactions.get(0);
        Assertions.assertEquals("05-12-2023", first.getDate(), "Дата першої транзакції неправильна");
        Assertions.assertEquals(-450.0, first.getAmount(), 0.0001, "Сума першої транзакції неправильна");
        Assertions.assertEquals("Сільпо", first.getDescription(), "Опис першої транзакції неправильний");

        for (Transaction transaction : transactions) {
            Assertions.assertNotNull(transaction.getDate());
            Assertions.assertNotNull(transaction.getDescription());
        }
    }
}
