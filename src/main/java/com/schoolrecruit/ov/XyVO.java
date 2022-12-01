package com.schoolrecruit.ov;

import lombok.Data;

import java.util.List;

@Data
public class XyVO {
    private List<String> x;
    private List<Long> y;
    private List<Long[]> xy;
}

