package com.poly.vo.report;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SynthesisReport {
    @Id
    private Integer id;
}
