package com.schoolrecruit.ov;

import lombok.Data;

@Data
public class ResponeConpanyShowRJVO {
    public ResponeConpanyShowRJVO() {
    }
    private Long studentId;
    private String studentName;
    private Long jobId;
    private String jobName;
    private String education;
    private String lengthOfSchooling;
    private String foreignLanguages;
    private String sex;
    private String resumeName;
    private String attachment;
    private String sendStatus;
    private String email;
    private Long resumeJobId;
}

