package com.poly.service.impl;

import com.poly.repo.report.SynthesisReportRepo;
import com.poly.service.SynthesisReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynthesisReportServiceImpl implements SynthesisReportService {
    @Autowired
    private SynthesisReportRepo synthesisReportRepo;

    @Override
    public Integer ordersQuantityByReceived(int received) {
        return synthesisReportRepo.ordersQuantityByReceived(received);
    }

    @Override
    public List<?> reportByProductType() {
        return synthesisReportRepo.reportByProductType();
    }

    @Override
    public List<?> top5BestSellingProducts() {
        return synthesisReportRepo.top5BestSellingProducts();
    }
}
