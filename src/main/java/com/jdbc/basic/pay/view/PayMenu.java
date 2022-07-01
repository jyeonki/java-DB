package com.jdbc.basic.pay.view;

import com.jdbc.basic.pay.controller.PayController;
import com.jdbc.basic.pay.domain.Pay;
import com.jdbc.basic.score.domain.Score;

import java.util.List;
import java.util.Scanner;

public class PayMenu {

    private final PayController payController;
    private final Scanner sc;

    public PayMenu() {
        payController = new PayController();
        sc = new Scanner(System.in);
    }


    public void mainMenu() {

        while (true) {
            System.out.println("\n============================= 급여 관리 프로그램 =============================");
            System.out.println("# 1. 사원 정보 입력");
            System.out.println("# 2. 사원 전체 조회");
            System.out.println("# 3. 사원 개별 조회");
            System.out.println("# 4. 사원 정보 수정");
            System.out.println("# 5. 사원 정보 삭제");
            System.out.println("# 9. 끝내기");

            int menu = inputNum("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    // 사원 정보 입력
                    insertMenu();
                    break;
                case 2:
                    // 사원 전체 조회
                    findAllMenu();
                    break;
                case 3:
                    // 특정 사원 급여 조회
                    findOneMenu();
                    break;
                case 4:
                    // 사원 급여 정보 수정 (직급 수정)
                    modifyMenu();
                    break;
                case 5:
                    break;
                case 9:
                    System.out.println("\n# 프로그램을 종료합니다.");
                    System.exit(0); // 프로세스 종료
                    return;
                default:
                    System.out.println("\n# 없는 메뉴 번호입니다.\n# 다시 입력하세요.");
            }
        }
    }



    // 1번 메뉴
    private void insertMenu() {

        System.out.println("\n# 사원 정보 입력을 시작합니다.");

//        System.out.print("- 사원번호: ");
//        String targetEmpNum = inputStr("- 사원번호: ");

        String empName = inputStr("- 이름: ");
        String empRank = inputStr("- 직급 [사원, 대리, 과장, 부장]: ");

        Pay pay = new Pay();
        pay.setEmpName(empName);

        if (empRank.equals("사원") || empRank.equals("대리") || empRank.equals("과장") || empRank.equals("부장"))
        {

        } else
        {
            while (true) {
                System.out.println("\n# [사원, 대리, 과장, 부장]만 입력이 가능합니다.");
                System.out.println("\n# 다시 입력하세요");
                empRank = inputStr("- 직급 [사원, 대리, 과장, 부장]: ");
                if (empRank.equals("사원") || empRank.equals("대리") || empRank.equals("과장") || empRank.equals("부장")) {
                    break;
                }
            }
        }

        pay.setEmpRank(empRank);
        pay.calc(empRank);
        pay.calcNetSalary();

        payController.insertEmpPay(pay);

        System.out.printf("\n# %s님의 정보가 저장되었습니다.\n", empName);
    }


    // 2번 메뉴
    private void findAllMenu() {

        List<Pay> employees = payController.findAllEmpPay();

        System.out.printf("\n=================== 전직원 급여 정보 (급여 평균: %.0f원) ===================\n", payController.calsEmpPayAverage());

        System.out.print("\n|   사원번호   |    이름    |    직급    |   기본급   |    세율    |   순급여   |\n");
//        System.out.printf("\n  %10s%8s%8s%9s%8s%9s\n", "사원번호", "이름", "직급", "기본급", "세율", "순급여");

        for (Pay employee : employees) {
            System.out.printf("%11s %9s %9s %13d %10.2f %13d\n", employee.getEmpNum(), employee.getEmpName(), employee.getEmpRank(), employee.getBasePay(), employee.getTaxRate(), employee.getNetSalary());
        }
    }


    // 3번 메뉴
    private void findOneMenu() {

        System.out.println("\n# 조회할 사원의 사원번호를 입력하세요.");
        String empNum = inputStr(">>> ");

        Pay employee = payController.findOneEmpPay(empNum);

        if (employee != null)
        {
            System.out.println("\n=============================== 직원 급여 정보 ===============================\n");
            System.out.print("|   사원번호   |    이름    |    직급    |   기본급   |    세율    |   순급여   |\n");
            System.out.printf("%11s %9s %9s %13d %10.2f %13d\n\n", employee.getEmpNum(), employee.getEmpName(), employee.getEmpRank(), employee.getBasePay(), employee.getTaxRate(), employee.getNetSalary());

        } else
        {
            System.out.println("\n# 해당 사원번호는 존재하지 않습니다.");
        }
    }


    // 4번 메뉴
    private void modifyMenu() {

        System.out.println("\n# 수정할 사원의 사원번호를 입력하세요.");
        String empNum = inputStr(">>> ");

        if (payController.hasEmp(empNum))
        {
            String oldRank = payController.findOneEmpPay(empNum).getEmpRank();
            System.out.println("\n# 해당 사원의 직급을 수정하세요 [사원, 대리, 과장, 부장]");
            String newRank = inputStr("- 새로운 직급 (현재 직급: " + oldRank + "): ");

            if (newRank.equals("사원") || newRank.equals("대리") || newRank.equals("과장") || newRank.equals("부장"))
            {
                boolean flag = payController.updateEmpPay(empNum, newRank);
                if (flag)
                {
                    System.out.println("\n# 수정이 완료되었습니다.");

                } else
                {
                    System.out.println("\n# 수정이 실패했습니다.");
                }
            } else
            {

            }

        } else
        {
            System.out.println("\n# 해당 사원번호는 존재하지 않습니다.");
        }
    }




    // 문자 입력 메서드
    private String inputStr(String msg) {
        System.out.printf(msg);
        return sc.nextLine();
    }

    // 숫자 입력 메서드
    private int inputNum(String msg) {

        String value;

        while (true) {
            try {
                System.out.print(msg);
                value = sc.nextLine();
                int nValue = Integer.parseInt(value);

                return nValue;
            } catch (NumberFormatException ex) {
//                sc.nextLine();
                System.out.println("\n정수로만 입력하세요.");
            }
        }
    }
}
