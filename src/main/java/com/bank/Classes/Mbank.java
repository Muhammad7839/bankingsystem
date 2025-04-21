package com.bank.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Mbank {

    private List<Customer> customers;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private List<TransferRecord> transfers;

    private int currentCustomerID;

    public Mbank() {
        this.customers = new ArrayList<Customer>();
        this.accounts = new ArrayList<Account>();
        this.transactions = new ArrayList<Transaction>();

        this.currentCustomerID = -1;

        this.loadCustomersFromFile("customers.txt");
        this.loadAccountsFromFile("accounts.txt");
        this.loadTransactionsFromFile("transactions.txt");
        this.loadTransfersFromFile("transfers.txt");
    }

    public void setcurrentCustomerID(int currentCustomerID) {
        this.currentCustomerID = currentCustomerID;
    }

    public int getcurrentCustomerID() {
        return this.currentCustomerID;
    }

    public Customer getCustomer(int customerId) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == customerId)
                return customers.get(i);
        }
        return null;
    }

    public Account getAccountOfCurrentCustomer() {
        for (Account account : accounts) {
            if (account.getCustomerId() == currentCustomerID) {
                return account;
            }
        }
        return null;

    }

    public Customer getCurrentCustomer() {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == currentCustomerID) {
                return customer;
            }
        }
        return null;
    }

    private int getAccountIndex(int accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber() == accountNumber)
                return i;
        }
        return -1;
    }

    public void loadCustomersFromFile(String filePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + new File(filePath).getAbsolutePath());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                int cId;
                int cPIN;
                String cName;
                String cEmail;
                String cPhone;
                String cAddress;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(",");
                    cId = Integer.parseInt(parts[0].trim());
                    cPIN = Integer.parseInt(parts[1].trim());
                    cName = parts[2].trim();
                    cEmail = parts[3].trim();
                    cPhone = parts[4].trim();
                    cAddress = parts[5].trim();

                    Customer customer = new Customer(cId, cPIN, cName, cEmail, cPhone, cAddress);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAccountsFromFile(String filePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }

            int aNumber;
            int aType;
            String openDate;
            float balance;
            int cId;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    aNumber = Integer.parseInt(parts[0].trim());
                    aType = Integer.parseInt(parts[1].trim());
                    openDate = parts[2].trim();
                    balance = Float.parseFloat(parts[3].trim());
                    cId = Integer.parseInt(parts[4].trim());

                    Account account = new Account(aNumber, aType, openDate, balance, cId);
                    accounts.add(account);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTransactionsFromFile(String filePath) {
        transactions = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }

            int cNumber;
            int aNumber;
            char tType;
            String tDate;
            float tAmount;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(",");

                    cNumber = Integer.parseInt(parts[1].trim());
                    aNumber = Integer.parseInt(parts[2].trim());
                    tType = parts[3].trim().charAt(0);
                    tDate = parts[4].trim();
                    tAmount = Float.parseFloat(parts[5].trim());

                    Transaction transaction = new Transaction(cNumber, aNumber, tType, tDate, tAmount);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Transaction getTransaction(int transactionNumber) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionNumber() == transactionNumber)
                return transactions.get(i);
        }
        return null;
    }

    public void loadTransfersFromFile(String filePath) {
        transfers = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }

            int depositTransactionNumber;
            int withdrawTransactionNumber;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    withdrawTransactionNumber = Integer.parseInt(parts[0].trim());
                    depositTransactionNumber = Integer.parseInt(parts[1].trim());

                    TransferRecord transfer = new TransferRecord(getTransaction(depositTransactionNumber),
                            getTransaction(withdrawTransactionNumber));
                    transfers.add(transfer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTransactionToFile(Transaction T) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transactions.txt", true))) {

            String line = String.format("%d,%d,%d,%c,%s,%.2f",
                    T.getTransactionNumber(),
                    T.getCustomerNumber(),
                    T.getAccountNumber(),
                    T.getTransactionType(),
                    T.getTransactionDate(),
                    T.getTransactionAmount());

            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTransfersToFile(TransferRecord T) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/transfers.txt", true))) {

            String line = String.format("%d,%d",
                    T.getWithdrawTransaction().getTransactionNumber(),
                    T.getDepositTransaction().getTransactionNumber());

            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/accounts.txt"))) {

            for (Account account : accounts) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
                dateFormat.setLenient(false);
                String openDateStr = dateFormat.format(account.getOpenDate());

                String line = String.format("%d,%d,%s,%.2f,%d",
                        account.getAccountNumber(), account.getAccountType(), openDateStr,
                        account.getBalance(), account.getCustomerId());
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean loginHelper(int cNumber, int cPin) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == cNumber && customer.getCustomerPIN() == cPin) {
                currentCustomerID = cNumber;
                return true;
            }
        }
        return false;
    }

    private String isValidDeposit(int accountNumber) {
        int accountIndex;

        accountIndex = getAccountIndex(accountNumber);
        if (accountIndex == -1)
            return "Account Not Found!";

        return "VALID";
    }

    private String isValidWithraw(int accountNumber, float transactionAmount) {
        int accountIndex;
        Account currentAccount;

        accountIndex = getAccountIndex(accountNumber);
        if (accountIndex == -1)
            return "Account not found!";

        currentAccount = accounts.get(accountIndex);

        if (currentAccount.getBalance() < transactionAmount)
            return "Insufficient funds in account!";

        return "VALID";
    }

    private String isValidTransfer(int fromAccount, int toAccount, float transactionAmount) {

        if (fromAccount == toAccount)
            return "You cannot transfer to your own account!";

        String withdrawStatus = isValidWithraw(fromAccount, transactionAmount);
        if (withdrawStatus != "VALID")
            return withdrawStatus;

        String depostStatus = isValidDeposit(toAccount);
        if (depostStatus != "VALID")
            return depostStatus;

        return "VALID";
    }

    public String deposit(int accountNumber, float transactionAmount) {

        String depositStatus = isValidDeposit(accountNumber);
        if (depositStatus == "VALID") {
            Transaction T;
            int accountIndex;
            Account currentAccount;

            try {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);

                accountIndex = getAccountIndex(accountNumber);
                currentAccount = accounts.get(accountIndex);

                T = new Transaction(currentAccount.getCustomerId(), currentAccount.getAccountNumber(), 'D',
                        formattedDateTime,
                        transactionAmount);

            } catch (Exception e) {
                return e.toString();
            }
            transactions.add(T);
            currentAccount.addBalance(transactionAmount);
            accounts.set(accountIndex, currentAccount);

            saveTransactionToFile(T);
            saveAccountsToFile();
            return "SUCCESS";
        }
        return depositStatus;

    }

    public String withdraw(int accountNumber, float transactionAmount) {
        String withrawStatus = isValidWithraw(accountNumber, transactionAmount);
        if (withrawStatus == "VALID") {
            Transaction T;
            int accountIndex;
            Account currentAccount;

            try {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);

                accountIndex = getAccountIndex(accountNumber);
                currentAccount = accounts.get(accountIndex);

                T = new Transaction(currentAccount.getCustomerId(), currentAccount.getAccountNumber(), 'W',
                        formattedDateTime,
                        transactionAmount);

            } catch (Exception e) {
                return e.toString();
            }
            transactions.add(T);

            currentAccount.removeBalance(transactionAmount);
            accounts.set(accountIndex, currentAccount);

            saveTransactionToFile(T);
            saveAccountsToFile();
            return "SUCCESS";
        }
        return withrawStatus;

    }

    public String transfer(int fromAccount, int toAccount, float transactionAmount) {
        String transferStatus = isValidTransfer(fromAccount, toAccount, transactionAmount);
        if (transferStatus == "VALID") {
            withdraw(fromAccount, transactionAmount);
            deposit(toAccount, transactionAmount);

            TransferRecord T = new TransferRecord(transactions.get(transactions.size() - 2),
                    transactions.get(transactions.size() - 1));
            transfers.add(T);
            saveTransfersToFile(T);

            return "SUCCESS";
        }
        return transferStatus;
    }

    public TransferRecord getLastTransfer() {
        return transfers.get(transfers.size() - 1);
    }

    public void logout() {
        this.currentCustomerID = -1;
    }

}