package com.bank.Classes;

public class Customer {

    private int customerId;
    private int customerPIN;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;

    public Customer(int customerId, int customerPIN, String customerName,
            String customerEmail, String customerPhone, String customerAddress) {
        setCustomerId(customerId);
        setCustomerPIN(customerPIN);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
    }

    public void printCustomerData() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Customer PIN: " + customerPIN);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Customer Email: " + customerEmail);
        System.out.println("Customer Phone: " + customerPhone);
        System.out.println("Customer Address: " + customerAddress);
        System.out.println();
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

    public int getCustomerPIN() {
        return customerPIN;
    }

    public void setCustomerPIN(int customerPIN) {
        if (isValidCustomerPIN(customerPIN)) {
            this.customerPIN = customerPIN;
        } else {
            throw new IllegalArgumentException("Customer PIN must be a 4-digit integer.");
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    private boolean isValidCustomerId(int customerId) {
        return String.valueOf(customerId).length() == 5;
    }

    private boolean isValidCustomerPIN(int customerPIN) {
        return String.valueOf(customerPIN).length() == 4;
    }
}
