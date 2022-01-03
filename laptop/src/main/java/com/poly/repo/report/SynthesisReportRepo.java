package com.poly.repo.report;

import com.poly.vo.report.SynthesisReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SynthesisReportRepo extends JpaRepository<SynthesisReport, Integer> {
    @Query("select count(o) from Orders o where o.received=?1")
    Integer ordersQuantityByReceived(int received);
}
