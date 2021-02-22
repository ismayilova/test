package com.example.model;

public class Transaction {
    
    
    private String financialInstitution;
    private String fxSettlementDate;
    private String reconciliationFileID;
    private String transactionCurrency;
    private String reconciliationCurrency;
    
    private String settlementCategory;
    
    private Double transactionAmountCredit;
    private Double reconciliationAmntCredit;
    private Double feeAmountCredit;
    private Double transactionAmountDebit;
    private Double reconciliationAmntDebit;
    private Double feeAmountDebit;
    private Double countTotal;
    private Double netValue;

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public String getFxSettlementDate() {
        return fxSettlementDate;
    }

    public String getReconciliationFileID() {
        return reconciliationFileID;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public String getReconciliationCurrency() {
        return reconciliationCurrency;
    }

    public String getSettlementCategory() {
        return settlementCategory;
    }

    public Double getTransactionAmountCredit() {
        return transactionAmountCredit;
    }

    public Double getReconciliationAmntCredit() {
        return reconciliationAmntCredit;
    }

    public Double getFeeAmountCredit() {
        return feeAmountCredit;
    }

    public Double getTransactionAmountDebit() {
        return transactionAmountDebit;
    }

    public Double getReconciliationAmntDebit() {
        return reconciliationAmntDebit;
    }

    public Double getFeeAmountDebit() {
        return feeAmountDebit;
    }

    public Double getCountTotal() {
        return countTotal;
    }

    public Double getNetValue() {
        return netValue;
    }


    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public void setFxSettlementDate(String fxSettlementDate) {
        this.fxSettlementDate = fxSettlementDate;
    }

    public void setReconciliationFileID(String reconciliationFileID) {
        this.reconciliationFileID = reconciliationFileID;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public void setReconciliationCurrency(String reconciliationCurrency) {
        this.reconciliationCurrency = reconciliationCurrency;
    }

    public void setSettlementCategory(String settlementCategory) {
        this.settlementCategory = settlementCategory;
    }

    public void setTransactionAmountCredit(Double transactionAmountCredit) {
        this.transactionAmountCredit = transactionAmountCredit;
    }

    public void setReconciliationAmntCredit(Double reconciliationAmntCredit) {
        this.reconciliationAmntCredit = reconciliationAmntCredit;
    }

    public void setFeeAmountCredit(Double feeAmountCredit) {
        this.feeAmountCredit = feeAmountCredit;
    }

    public void setTransactionAmountDebit(Double transactionAmountDebit) {
        this.transactionAmountDebit = transactionAmountDebit;
    }

    public void setReconciliationAmntDebit(Double reconciliationAmntDebit) {
        this.reconciliationAmntDebit = reconciliationAmntDebit;
    }

    public void setFeeAmountDebit(Double feeAmountDebit) {
        this.feeAmountDebit = feeAmountDebit;
    }

    public void setCountTotal(Double countTotal) {
        this.countTotal = countTotal;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }
}
