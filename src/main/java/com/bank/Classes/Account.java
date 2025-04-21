package com.bank.Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Account {

    private int accountNumber;
    private int accountType;
    private Date openDate;
    private float balance;
    private int customerId;

    public static final int CHECKING = 1;
    public static final int SAVINGS = 2;

    public Account(int accountNumber, int accountType, String openDate, float balance, int customerId) {
        setAccountType(accountType);
        setAccountNumber(accountNumber);
        setOpenDate(openDate);
        setCustomerId(customerId);
        this.balance = balance;
    }

    public void displayAccountData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + (accountType == 1 ? "Checking" : "Savings"));
        System.out.println("Open Date: " + dateFormat.format(openDate));
        System.out.println("Balance: " + balance);
        System.out.println("Customer ID: " + customerId);
        System.out.println();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        if (isValidAccountNumber(accountNumber)) {
            this.accountNumber = accountNumber;
        } else {
            throw new IllegalArgumentException(
                    "Account number must be a 7-digit number with the last 2 digits matching the account type.");
        }
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        if (isValidAccountType(accountType)) {
            this.accountType = accountType;
        } else {
            throw new IllegalArgumentException("Invalid account type. Use 1 for Checking or 2 for Savings.");
        }
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
            dateFormat.setLenient(false);
            this.openDate = dateFormat.parse(openDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use MM-dd-yy.");
        }
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void addBalance(float balance) {
        this.balance += balance;
    }

    public void removeBalance(float balance) {
        this.balance -= balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        if (isValidCustomerId(customerId)) {
            this.customerId = customerId;
        } else {
            throw new IllegalArgumentException("Customer ID must be a 5-digit integer.");
        }
    }

    private boolean isValidAccountNumber(int accountNumber) {
        String accountNumberString = String.valueOf(accountNumber);

        if (accountNumberString.length() != 7) {
            return false;
        }

        String lastTwoDigits = accountNumberString.substring(5);
        return lastTwoDigits.equals("0" + String.valueOf(accountType));
    }

    private boolean isValidAccountType(int accountType) {
        return accountType == CHECKING || accountType == SAVINGS;
    }

    private boolean isValidCustomerId(int customerId) {
        return String.valueOf(customerId).length() == 5;
    }
}
