package com.schoolrecruit.ov;

import lombok.Data;

@Data
public class SearchVO {
    public SearchVO() {
    }

    private Long jobId;
    private String jobName;
    private String place;
    private String education;
    private String salary;

    private Long companyId;
    private String companyName;
    private String portrait;
    private String nature;
    //规模
    private String scale;

    private int crrPage;
}

