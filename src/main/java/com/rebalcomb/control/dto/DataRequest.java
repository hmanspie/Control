package com.rebalcomb.control.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataRequest {

    private String p;

    private String q;

    private String e;

    private String d;

    private String M1;

    private String C;

    private String M2;

    private String S;
}
