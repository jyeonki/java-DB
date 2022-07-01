package com.jdbc.basic.pay.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.pay.domain.Pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

// Oracle DBMS에 사원 급여정보를 CRUD하는 클래스
public class PayOracleRepo implements PayRepository {

    @Override
    public boolean save(Pay pay) {

        String sql = "INSERT INTO pay VALUES (TO_CHAR(sysdate,'yymmdd')|| + LPAD(seq_pay.nextval, 2, '0'), ?,?,?,?,?)";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pay.getEmpName());
            pstmt.setString(2, pay.getEmpRank());
            pstmt.setInt(3, pay.getBasePay());
            pstmt.setDouble(4, pay.getTaxRate());
            pstmt.setInt(5, pay.getNetSalary());

            int result = pstmt.executeUpdate();

            if (result != 0) { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(String empNum) {

        String sql = "DELETE FROM pay WHERE emp_num = ?";

        try (Connection conn = Connect.makeConnection()) {

            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, empNum);

            int result = pstmt.executeUpdate();

            if (result != 0) { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modify(Pay pay) {

        String sql = "UPDATE pay SET emp_rank = ?, base_pay = ?, tax_rate = ?, net_salary = ? WHERE emp_num = ?";

        try (Connection conn = Connect.makeConnection()) {
            // 트랜잭션 처리
            conn.setAutoCommit(false); // 자동커밋 설정 끄기!!

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pay.getEmpRank());
            pstmt.setInt(2, pay.getBasePay());
            pstmt.setDouble(3, pay.getTaxRate());
            pstmt.setInt(4, pay.getNetSalary());
            pstmt.setString(5, pay.getEmpNum());

            int result = pstmt.executeUpdate();

            if (result != 0) { // 결과가 0이 아니면 commit
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Pay> findAll() {

        Map<String, Pay> payMap = new HashMap<>();

        String sql = "SELECT * FROM pay ORDER BY emp_num";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Pay pay = new Pay(
                        rs.getString("emp_num")
                        , rs.getString("emp_name")
                        , rs.getString("emp_rank")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                        , rs.getInt("net_salary")
                );
                payMap.put(pay.getEmpNum(), pay);
            }
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pay findOne(String empNum) {

        String sql = "SELECT * FROM pay WHERE emp_num = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, empNum);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Pay pay  = new Pay(
                        rs.getString("emp_num")
                        , rs.getString("emp_name")
                        , rs.getString("emp_rank")
                        , rs.getInt("base_pay")
                        , rs.getDouble("tax_rate")
                        , rs.getInt("net_salary")
                );

                return pay;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public double getEmpPayAverage() {

        String sql = "SELECT AVG(net_salary) AS avg_emp_pay FROM pay";

//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT AVG(net_salary) AS avg_emp_pay\n")
//                .append("FROM pay");

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_emp_pay");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
