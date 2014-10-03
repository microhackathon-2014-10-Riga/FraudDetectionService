package com.ofg.microservice.fraud;

public enum JobPosition {
    
    OTHER("OTHER"),
    IT("IT"),
    FINANCE_SECTOR("FINANCE SECTOR");
    
    final String name;
    
    JobPosition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


