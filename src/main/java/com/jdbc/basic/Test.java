package com.jdbc.basic;

import java.sql.Connection;

public class Test {

    public static void main(String[] args) {

        Connection conn = Connect.makeConnection();// static은 객체 생성 안해도 호출 가능
        if (conn != null) {
            System.out.println("연결 성공!");
        } else {
            System.out.println("연결 실패!");
        }

    }
}
