package com.schoolrecruit.ov;

import lombok.Data;

@Data
public class SendEmailVO {
    public SendEmailVO() {
    }
    private  Long id;
    private String email;
    private String title;
    private String content;
}

