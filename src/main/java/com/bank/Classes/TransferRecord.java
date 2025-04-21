package com.bank.Classes;

public class TransferRecord {
    private Transaction deposit;
    private Transaction withdraw;

    TransferRecord(Transaction withdraw, Transaction deposit) {
        this.deposit = deposit;
        this.withdraw = withdraw;
    }

    public Transaction getDepositTransaction() {
        return deposit;
    }

    public Transaction getWithdrawTransaction() {
        return withdraw;
    }



}
