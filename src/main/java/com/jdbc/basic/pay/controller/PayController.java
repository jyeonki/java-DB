package com.jdbc.basic.pay.controller;

import com.jdbc.basic.pay.domain.Pay;
import com.jdbc.basic.pay.repository.PayOracleRepo;
import com.jdbc.basic.pay.repository.PayRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 데이터 저장, 관리 클래스
public class PayController {

    // 급여 정보가 저장될 Map ( key: 사원번호, value: 급여정보 )
    private static Map<String, Pay> payMap;

    static {
        payMap = new HashMap<>();
    }

    private final PayRepository repository;

    public PayController() {
        this.repository = new PayOracleRepo();
    }

    // 사원 급여 정보 입력 기능
    public void insertEmpPay(Pay pay) {

        // 1. 메모리에 정보 저장하기
        payMap.put(pay.getEmpNum(), pay);

        // 2. DB에 저장 명령
        repository.save(pay);
    }

    // 특정 사원의 급여 조회 기능
    public Pay findOneEmpPay(String empNum) {

        return repository.findOne(empNum);
    }

    // 사원 직급 수정 기능
    public boolean updateEmpPay(String empNum, String empRank) {

        // 1. DB에서 사원정보 불러오기
//        Pay target = repository.findOne(empNum);
        Pay target = findOneEmpPay(empNum);

        if (target != null) {

            // 2. 사원 직급 수정 (직급이 수정되면 기본급, 세율, 순급여가 바뀜)
            target.setEmpRank(empRank);
            target.calc(target.getEmpRank());
            target.calcNetSalary();

            // 3. DB에 수정 반영
            repository.modify(target);
            return true;

        } else {
            return false;
        }
    }

    // 특정 사원 삭제 기능
    public boolean deleteEmpPay(String empNum) {

        return repository.remove(empNum);
    }

    // 전체 사원 급여 조회 기능
    public List<Pay> findAllEmpPay() {

        Map<String , Pay> employees = repository.findAll();
        payMap = employees;

        List<Pay> payList = new ArrayList<>();
        for (String empNum : payMap.keySet()) {
            payList.add(payMap.get(empNum));
        }

        return payList;
    }

    // 평균 월급을 구하는 메서드
    public double calsEmpPayAverage() {

        // DB에서 사원들의 평균 월급을 구한다
        return repository.getEmpPayAverage();
    }

    // 사원번호로 조회했을 때 사원 존재 유무를 리턴해주는 메서드
    public boolean hasEmp(String empNum) {
        return repository.findOne(empNum) != null;
    }
}












