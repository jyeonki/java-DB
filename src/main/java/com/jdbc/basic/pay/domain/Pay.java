package com.jdbc.basic.pay.domain;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

// 데이터베이스 pay 테이블의 행데이터를 저장할 객체
public class Pay {

    private int empNum;
    private String empName;
    private String empRank;
    private int basePay;
    private double taxRate;
    private int netSalary;


    // 직급별 기본급, 세율 설정 메서드
    public void calc(String rank) {

        switch (rank) {
            case "사원" :
                this.basePay = 2000000;
                this.taxRate = 0.10;
                break;
            case "대리" :
                this.basePay = 2500000;
                this.taxRate = 0.15;
                break;
            case "과장" :
                this.basePay = 3000000;
                this.taxRate = 0.20;
                break;
            case "부장" :
                this.basePay = 3500000;
                this.taxRate = 0.25;
                break;
        }
    }

    // 순급여 계산 메서드
    public void calcNetSalary() {
        this.netSalary = (int) (basePay * (1 - taxRate));
    }
}
