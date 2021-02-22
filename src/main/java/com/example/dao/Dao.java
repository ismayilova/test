package com.example.dao;

import com.example.model.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Dao {

    private final JdbcTemplate jdbcTemplate;

    public  Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String sqlInsert = "insert into ik_transactions(financialinstitution, fxsettlementdate, reconciliationfileid, transactioncurrency, reconciliationcurrency, settlementcategory, transactionamountcredit, reconciliationamntcredit, feeamountcredit, transactionamountdebit, reconciliationamntdebit, feeamountdebit, counttotal, netvalue) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private String sqlSelect = "select * from ik_transactions";

    public  void addTransactionInDb(JSONArray transactions) throws SQLException {

        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        List<JSONObject> trns =transactions;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
             ps = connection.prepareStatement(sqlInsert);

            for(JSONObject transaction:trns){
                System.out.println(transaction);
                List<JSONObject> entries =(JSONArray) transaction.get( (Object) "entries");
                for(JSONObject ent:entries){
                    ps.setString(1,transaction.get((Object)"Financial_Institution").toString());
                    ps.setString(2,transaction.get((Object)"FX_Settlement_Date").toString());
                    ps.setString(3,transaction.get((Object)"Reconciliation_File_ID").toString());
                    ps.setString(4,transaction.get((Object)"Transaction_Currency").toString());
                    ps.setString(5,transaction.get((Object)"Reconciliation_Currency").toString());
                    ps.setString(6,ent.get((Object)"Settlement_Category_").toString());
                    ps.setDouble(7,Double.parseDouble(ent.get((Object)"Transaction_Amount_Credit").toString()));
                    ps.setDouble(8,Double.parseDouble(ent.get((Object)"Reconciliation_Amnt_Credit").toString()));
                    ps.setDouble(9,Double.parseDouble(ent.get((Object)"Fee_Amount_Credit").toString()));
                    ps.setDouble(10,Double.parseDouble(ent.get((Object)"Transaction_Amount_Debit").toString()));
                    ps.setDouble(11,Double.parseDouble(ent.get((Object)"Reconciliation_Amnt_Debit").toString()));
                    ps.setDouble(12,Double.parseDouble(ent.get((Object)"Fee_Amount_Debit").toString()));
                    ps.setDouble(13,Double.parseDouble(ent.get((Object)"Count_Total").toString()));
                    ps.setDouble(14,Double.parseDouble(ent.get((Object)"Net_Value_").toString()));

                    ps.addBatch();
                }


            }
            ps.executeBatch();
            connection.commit();


            // jdbcTemplate.update(
        }catch (SQLException ex) {
            throw new SQLException(ex);
        }
        finally {

             close(rs,ps,connection);
        }

    }


    public List<Transaction> queryData() throws SQLException{
        System.out.println("getData from DB");

        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
       List<Transaction> res= new ArrayList<>();

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sqlSelect);

            rs =(ResultSet) ps.executeQuery();

             while(rs.next()){
                 Transaction transaction = new Transaction();

                 transaction.setFinancialInstitution(rs.getString("financialinstitution"));
                 transaction.setFxSettlementDate(rs.getString("fxsettlementdate"));
                 transaction.setReconciliationFileID(rs.getString("reconciliationfileid"));
                 transaction.setTransactionCurrency(rs.getString("transactioncurrency"));
                 transaction.setReconciliationCurrency(rs.getString("reconciliationcurrency"));

                 transaction.setSettlementCategory(rs.getString("settlementcategory"));
                 transaction.setTransactionAmountCredit(rs.getDouble("transactionamountcredit"));
                 transaction.setReconciliationAmntCredit(rs.getDouble("reconciliationamntcredit"));
                 transaction.setFeeAmountCredit(rs.getDouble("feeamountcredit"));

                 transaction.setTransactionAmountDebit(rs.getDouble("transactionamountdebit"));
                 transaction.setReconciliationAmntDebit(rs.getDouble("reconciliationamntdebit"));
                 transaction.setFeeAmountDebit(rs.getDouble("feeamountdebit"));


                 transaction.setCountTotal(rs.getDouble("counttotal"));
                 transaction.setNetValue(rs.getDouble("netvalue"));

                 res.add(transaction);

             }






            // jdbcTemplate.update(
        }catch (SQLException ex) {
            throw new SQLException(ex);
        }
        finally {

            close(rs,ps,connection);
        }

        return res;

    }


    private   void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
