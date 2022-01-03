package com.poly.service.impl;

import com.poly.repo.report.SynthesisReportRepo;
import com.poly.service.SynthesisReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynthesisReportServiceImpl implements SynthesisReportService {
    @Autowired
    private SynthesisReportRepo synthesisReportRepo;

    @Override
    public Integer ordersQuantityByReceived(int received) {
        return synthesisReportRepo.ordersQuantityByReceived(received);
    }
}
