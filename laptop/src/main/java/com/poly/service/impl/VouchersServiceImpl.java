package com.poly.service.impl;

import com.poly.DTO.CategorySelectOption;
import com.poly.DTO.ProductSelectOption;
import com.poly.DTO.VouchersDTO;
import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.entity.Vouchers;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.repo.VouchersRepository;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.service.VouchersService;
import com.poly.vo.VouchersVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VouchersServiceImpl implements VouchersService {

    @Autowired
    private VouchersRepository repository;

    @Autowired
    private CategoryRepository categoryService;

    @Autowired
    private ProductsRepository productService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VouchersVO getVoucherById(String id) {
        Optional<Vouchers> entity = this.repository.findById(id);
        if (entity.isPresent()){
            this.compareDate(entity.get().getEndDate());
            return modelMapper.map(entity.get(), VouchersVO.class);
        }
        return null;
    }

    @Override
    public VouchersVO getVoucherTrue(String id, String idProduct) {
        /*Optional<Vouchers> entity = this.repository.findById(id);
        if (entity.isPresent() && entity.get().getActived()){
            Integer checkDate = this.compareDate(entity.get().getEndDate());
            if (checkDate==0 || checkDate==1 ){
                if (entity.get().getProduct() != null){
                    if (entity.get().getProduct().getId().equals(idProduct)){
                        VouchersVO vo = modelMapper.map(entity.get(), VouchersVO.class);
                        vo.setIdProduct(entity.get().getProduct().getId());
                        return vo;
                    }
                }
            }
        }*/
        return null;
    }

    @Override
    public List<VouchersDTO> findAll() {
        List<VouchersDTO> dtoList = new ArrayList<>();
        /*List<Vouchers> vouchersList = repository.findAllVoucher();
        vouchersList.forEach(vouchers ->{
            VouchersDTO dto = new VouchersDTO();
            modelMapper.map(vouchers,dto);
            if(vouchers.getProduct() != null){
                dto.setIdProduct(vouchers.getProduct().getId());
                dto.setNameProduct(vouchers.getProduct().getName());
//                dto.setTypeOfItem(vouchers.getProduct().getTypeOfItem());
            }

            if(vouchers.getCategory()!= null){
                if(vouchers.getCategory().getParentId()!= null){
                    dto.setIdCategory(vouchers.getCategory().getId());
                    dto.setNameCategory(vouchers.getCategory().getName());
//                    dto.setTypeOfItem(vouchers.getCategory().getParentId());
                }else{
                    dto.setTypeOfItem(vouchers.getCategory().getId());
                    dto.setNameCategory(vouchers.getCategory().getName());
                }

            }
            dtoList.add(dto);
        });*/

        return dtoList;
    }

    @Override
    public VouchersDTO findById(String id) {
        VouchersDTO dto = new VouchersDTO();
        if(id != null || !id.equals("")){
            if(repository.findById(id) != null){
               dto  = modelMapper.map(repository.findById(id),VouchersDTO.class);
            }
        }
        return dto;
    }

    @Override
    public VouchersDTO create(VouchersDTO dto) {
        Vouchers vouchers = modelMapper.map(dto,Vouchers.class);

        /*vouchers.setId(RandomStringUtils.randomAlphanumeric(10));
        if(dto.getIdCategory()!= null){
            Category category = categoryService.findById(dto.getIdCategory()).get();
            vouchers.setCategory(category);
        }
        if(dto.getTypeOfItem()!= null){
            Category category = categoryService.findById(dto.getTypeOfItem()).get();
            vouchers.setCategory(category);
        }
        if(dto.getIdProduct()!= null){
            Products products = productService.getById(dto.getIdProduct());
            vouchers.setProduct(products);
        }
        vouchers.setActived(true);
        dto = modelMapper.map(repository.save(vouchers),VouchersDTO.class);
        System.out.println("create ImpVoucher: " + dto);*/
        return dto;
    }

    @Override
    public VouchersDTO update(VouchersDTO dto) {
        /*Vouchers vouchers = modelMapper.map(dto,Vouchers.class);
        System.out.println(dto);
        if(dto.getIdCategory()!= null){
            Category category = categoryService.findById(dto.getIdCategory()).get();
            vouchers.setCategory(category);
        }
        if(dto.getTypeOfItem()!= null){
            Category category = categoryService.findById(dto.getTypeOfItem()).get();
            vouchers.setCategory(category);
        }
        if(dto.getIdProduct()!= null){
            Products products = productService.getById(dto.getIdProduct());
            vouchers.setProduct(products);
        }
        dto = modelMapper.map(repository.save(vouchers),VouchersDTO.class);
        System.out.println("update ImpVoucher: " + dto);*/
        return dto;
    }

    @Override
    public Boolean delete(String id) {

        if(repository.getById(id)!= null){
//            repository.updateStatusVoucher(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CategorySelectOption> cateSelectOption() {
//        repository.cateSelectOption();
        return null;
    }

    @Override
    public List<ProductSelectOption> productsSelectOption() {
//        repository.productsSelectOption()
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
