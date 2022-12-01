package com.schoolrecruit.ov;

import com.schoolrecruit.entity.Collection;
import lombok.Data;

@Data
public class CollectionResponeVO extends Collection {
    private Long companyId;
    private String companyName;
    private String jobName;
    private String portrait;
    private String checkStatus;
    private String recruitmentStatus;
    private String majors;
    private String tip;
}
