package com.math012.autoresobras.infra.exceptions.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StructException {
    private Date timestamp;
    private String msg;
    private String detail;
}