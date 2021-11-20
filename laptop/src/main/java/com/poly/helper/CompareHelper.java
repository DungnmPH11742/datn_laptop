package com.poly.helper;

import com.poly.service.SessionService;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompareHelper {

    @Autowired
    private SessionService sessionService;

    public List<ProductsVO> getAllProductVo() {
        List<ProductsVO> vos = new ArrayList<>();
        vos.add(this.getProductVo("p1"));
        vos.add(this.getProductVo("p2"));
        sessionService.setAttribute("list_compare", vos);
        return vos;
    }

    public ProductsVO getProductVo(String name) {
        return sessionService.getAttribute(name);
    }

    public boolean removeCompare(String name) {
        if (this.getProductVo(name) != null) {
            this.sessionService.removeAttribute(name);
            return true;
        } else {
            return false;
        }
    }

    public MessageHelper setProductVo(ProductsVO vo) {
        MessageHelper messageHelper = new MessageHelper();
        if (this.getProductVo("p1") == null) {
            sessionService.setAttribute("p1", vo);
            messageHelper.setKey("000");
            messageHelper.setMessage("Thành công");
        } else if (this.getProductVo("p2") == null) {
            if (this.getProductVo("p1").getId().equalsIgnoreCase(vo.getId())) {
                this.removeCompare("p1");
                messageHelper.setKey("001");
                messageHelper.setMessage("Đã xóa");
            } else if (this.getProductVo("p1").getTypeOfItem() == vo.getTypeOfItem()) {
                sessionService.setAttribute("p2", vo);
                messageHelper.setKey("000");
                messageHelper.setMessage("Thành công");
            } else {
                messageHelper.setKey("002");
                messageHelper.setMessage("Lỗi khác loại sản phẩm");
            }
        } else if (this.getProductVo("p2").getId().equalsIgnoreCase(vo.getId())) {
            this.removeCompare("p2");
            messageHelper.setKey("001");
            messageHelper.setMessage("Đã xóa");
        } else if (this.getProductVo("p1").getId().equalsIgnoreCase(vo.getId())) {
            this.removeCompare("p1");
            messageHelper.setKey("001");
            messageHelper.setMessage("Đã xóa");
        } else {

            messageHelper.setKey("003");
            messageHelper.setMessage("Vui lòng xóa để thêm");
        }
        return messageHelper;
    }


}
