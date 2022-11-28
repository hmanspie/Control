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
public class ElgamalRequest {

    private String p;

    private String g;

    private String x;

    private String k1;

    private String k2;

    private String M1;

    private String M2;

}
