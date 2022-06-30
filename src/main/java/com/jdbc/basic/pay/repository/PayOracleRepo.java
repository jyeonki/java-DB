package com.jdbc.basic.pay.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.pay.domain.Pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

// Oracle DBMS에 사원 급여정보를 CRUD하는 클래스
public class PayOracleRepo implements PayRepository {

    @Override
    public boolean save(Pay pay) {

        String sql = "INSERT INTO pay VALUES (to_char(sysdate,'yymmdd')|| + SEQ_PAY.nextval, ?,?,?,?,?)";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pay.getEmpName());
            pstmt.setString(2, pay.getEmpRank());
            pstmt.setInt(3, pay.getBasePay());
            pstmt.setDouble(4, pay.getTaxRate());
            pstmt.setInt(5, pay.getNetSalary());

            int result = pstmt.executeUpdate();

            if (result != 0) {
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
    public boolean remove(int empNum) {
        return false;
    }

    @Override
    public boolean modify(Pay pay) {
        return false;
    }

    @Override
    public Map<Integer, Pay> findAll() {
        return null;
    }

    @Override
    public Pay findOne(int empNum) {
        return null;
    }

    @Override
    public double getEmpSalaryAverage() {
        return 0;
    }
}
