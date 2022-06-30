package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Pay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayOracleRepoTest {

    PayRepository repository = new PayOracleRepo();

    @Test
    @DisplayName("사원별 급여 정보를 DB에 삽입해야 한다.")
    void insertTest() {

        Pay park = new Pay();
        park.setEmpName("박대박");
        park.setEmpRank("대리");
        park.calc(park.getEmpRank());
        park.calcNetSalary();

        boolean result = repository.save(park);
        assertTrue(result);
    }

}