package com.finder.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailMessage implements Serializable{

    private String email;
    private String token;

}
