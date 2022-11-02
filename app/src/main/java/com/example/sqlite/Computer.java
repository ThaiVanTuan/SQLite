package com.example.sqlite;

public class Computer {
    private int IdPC;
    private String TenPC;

    public Computer(int idPC, String tenPC) {
        IdPC = idPC;
        TenPC = tenPC;
    }

    public int getIdPC() {
        return IdPC;
    }

    public void setIdPC(int idPC) {
        IdPC = idPC;
    }

    public String getTenPC() {
        return TenPC;
    }

    public void setTenPC(String tenPC) {
        TenPC = tenPC;
    }
}
