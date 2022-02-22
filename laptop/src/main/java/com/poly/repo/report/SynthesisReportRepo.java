package com.poly.repo.report;

import com.poly.vo.report.SynthesisReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SynthesisReportRepo extends JpaRepository<SynthesisReport, Integer> {
    @Query("select count(o) from Orders o where o.received=?1")
    Integer ordersQuantityByReceived(int received);

    @Query(value="select c.name, count(c.id) as ct, month(order_date) as m ,year(order_date) as y from category c left join products p on c.id = p.type_of_item left join products_detail p_dt on p_dt.id_product = p.id left join order_details od on od.sku = p_dt.sku left join orders o on o.id = od.id_order where c.parent_id is null group by c.id, c.name, MONTH(order_date), year(order_date)\n", nativeQuery=true)
    List<?> reportByProductType();

    @Query(value="select sum(od.quantity), p.id, p.name, p.type_of_item from products p left join products_detail p_dt on p_dt.id_product = p.id left join order_details od on od.sku = p_dt.sku left join orders o on od.id_order = o.id where active = 1 and received = 2 group by p.id, p.name, p.type_of_item\n", nativeQuery=true)
    List<?> top5BestSellingProducts();
}
