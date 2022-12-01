package com.schoolrecruit.ov;

import lombok.Data;

@Data
public class StudentShowJRVO {
    public StudentShowJRVO() {
    }

    private Long companyId;
    private String companyName;
    private String portrait;
    private String checkStatus;

    private Long jobId;
    private String jobName;
    private String recruitmentStatus;

    private String resumeName;
    private String attachment;
    private String sendStatus;
}

