package com.schoolrecruit.ov;

import lombok.Data;

@Data
public class RequestConpanySearchRJVO {
    public RequestConpanySearchRJVO() {
    }
    private Long companyId;
    private String studentName;
    private String selectStatus;
    private String selectJob;
    private String education;
    private String lengthOfSchooling;
    private String foreignLanguages;
    private String sex;
    private int crrPage;
}

