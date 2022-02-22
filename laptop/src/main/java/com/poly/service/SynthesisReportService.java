package com.poly.service;

import java.util.List;

public interface SynthesisReportService {

    Integer ordersQuantityByReceived(int received);

    List<?> reportByProductType();

    List<?> top5BestSellingProducts();
}
