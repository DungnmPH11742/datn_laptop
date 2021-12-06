package com.poly.service.impl;

import com.poly.entity.Vouchers;
import com.poly.repo.VouchersRepository;
import com.poly.service.VouchersService;
import com.poly.vo.VouchersVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class VouchersServiceImpl implements VouchersService {

    @Autowired
    private VouchersRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VouchersVO getVoucherById(String id) {
        Optional<Vouchers> entity = this.repository.findById(id);
        if (entity.isPresent()){
            System.out.println(entity.get().getEndDate());
            this.compareDate(entity.get().getEndDate());
            return modelMapper.map(entity.get(),VouchersVO.class);
        }
        return null;
    }

    @Override
    public VouchersVO getVoucherTrue(String id, String idProduct) {
        Optional<Vouchers> entity = this.repository.findById(id);
        if (entity.isPresent() && entity.get().getActived()){
            Integer checkDate = this.compareDate(entity.get().getEndDate());
            if (checkDate==0 || checkDate==1 ){
                if (entity.get().getProduct() != null){
                    if (entity.get().getProduct().getId().equals(idProduct)){
                        VouchersVO vo = modelMapper.map(entity.get(),VouchersVO.class);
                        vo.setIdProduct(entity.get().getProduct().getId());
                        return vo;
                    }
                }
            }
        }
        return null;
    }

    private Integer compareDate(Date date) {
        try {
            Date now = new Date();
            long miniS = date.getTime();
            date.setTime(miniS + (24*60*60*1000));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = df.format(now);
            String strDateEnd = df.format(date);
            Date dateEnd = df.parse(strDateEnd);
            Date dateNow = df.parse(strDate);
            return dateEnd.compareTo(dateNow);
        }catch (Exception e){
            System.out.println("Loi");
        }
        return null;
    }
}
