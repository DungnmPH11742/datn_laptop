package com.poly.helper;

import com.poly.entity.Products;
import com.poly.service.SessionService;
import com.poly.vo.ProductsVO;
import com.poly.vo.response.ProductsReponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompareHelper {
    private final int size = 2;

    @Autowired
    private SessionService sessionService;

    public List<ProductsReponseVO> getAllProductVo() {
        List<ProductsReponseVO> vos = new ArrayList<>();
        vos.add(this.getProductResponseVo("p1"));
        vos.add(this.getProductResponseVo("p2"));
        sessionService.setAttribute("list_compare", vos);
        return vos;
    }

    public ProductsReponseVO getProductResponseVo(String name) {
        return sessionService.getAttribute(name);
    }

    public boolean removeCompare(String name) {
        if (this.getProductResponseVo(name) != null) {
            this.sessionService.removeAttribute(name);
            return true;
        } else {
            return false;
        }
    }

    public MessageHelper setCompareProductVo(ProductsReponseVO vo) {
        MessageHelper messageHelper = new MessageHelper();
        String check = "000";
        int count = 0;
        int count2 = 0;
        for (int i = 1; i <= size; i++) {
            if (this.getProductResponseVo("p" + i) != null) {
                count+=1;
                if (!vo.getTypeOfItem().equalsIgnoreCase(getProductResponseVo("p" + i).getTypeOfItem())) {
                    check = "002";
                }
            } else {
                for (int j = 1; j <= size; j++) {
                    count2 ++;
                    if (getProductResponseVo("p" + j) != null && i != j) {
                        if (getProductResponseVo("p" + j).getProductsDetail().getSku().equalsIgnoreCase(vo.getProductsDetail().getSku())) {
                            check = "001";
                        }
                    }
                    if(count2 == size){
                        break;
                    }
                }
                if (count2 == size) {
                    if(check.equalsIgnoreCase("001")){
                        messageHelper.setKey(check);
                        messageHelper.setMessage("Không thể so sánh sản phẩm giống nhau!");
                    }else if(check.equalsIgnoreCase("002")){
                        messageHelper.setKey(check);
                        messageHelper.setMessage("Không thể so sánh sản phẩm khác loại!");
                    }else if(check.equalsIgnoreCase("000")){
                        messageHelper.setKey(check);
                        messageHelper.setMessage("Thành công!");
                        sessionService.setAttribute("p"+i, vo);
                    }
                }
            }
        }
        if (count == size) {
            messageHelper.setKey("003");
            messageHelper.setMessage("Danh sách xóa đã đầy!");
        }
        /*
        if (this.getProductVo("p1") == null) {
            if (this.getProductVo("p2") != null) {
                if (this.getProductVo("p2").getId().equalsIgnoreCase(vo.getId())) {
                    messageHelper.setKey("004");
                    messageHelper.setMessage("Trùng");
                } else {
                    sessionService.setAttribute("p1", vo);
                    messageHelper.setKey("000");
                    messageHelper.setMessage("Thành công");
                }
            } else {
                sessionService.setAttribute("p1", vo);
                messageHelper.setKey("000");
                messageHelper.setMessage("Thành công");
            }
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
        }*/
        return messageHelper;
    }


}
