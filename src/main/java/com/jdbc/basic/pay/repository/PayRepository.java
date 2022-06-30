package com.jdbc.basic.pay.repository;

import com.jdbc.basic.pay.domain.Pay;

import java.util.Map;

public interface PayRepository {

    // 사원 급여 정보 저장
    boolean save(Pay pay);

    // 사원 급여 정보 삭제
    boolean remove(int empNum);

    // 사원 급여 정보 수정 (직급)
    boolean modify(Pay pay);

    // 전체 사원 급여 조회
    Map<Integer, Pay> findAll();

    // 개별 급여 조회
    Pay findOne(int empNum);

    // 전체 사원들의 월급 평균 조회
    double getEmpSalaryAverage();
}
