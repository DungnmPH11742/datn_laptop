package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.OrderDetails;
import com.poly.entity.Orders;
import com.poly.repo.AccountRepository;
import com.poly.repo.OrdersRepository;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.service.ProductService;
import com.poly.vo.*;
import com.poly.vo.response.OrderDetailResponseVO;
import com.poly.vo.response.OrderResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserDetailsServiceImpl detailsService;

    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public List<OrdersVO> getAllOrders() {
        List<OrdersVO> vos = new ArrayList<>();
        repository.findAll().forEach(orders -> {
            vos.add(modelMapper.map(orders, OrdersVO.class));
        });
        return vos;
    }


    @Override
    public OrdersVO findIdOrder(Integer id) {
//        Optional<Orders> orders = repository.findById(id);
//        if (orders.isPresent()){
//            OrdersVO ordersVO = modelMapper.map(orders,OrdersVO.class);
//            ordersVO.setOrderCode(orders.get().getOrderCode());
//            ordersVO.setPaymentMethods(orders.get().getPaymentMethods());
//            AccountVO accountVO = modelMapper.map(orders.get().getAccount(),AccountVO.class);
//            if (orders.get().getReceived()==1 || orders.get().getReceived()==2 || orders.get().getReceived()==3 || orders.get().getReceived()==0 || orders.get().getReceived()==-1){
//                ordersVO.setAccount(accountVO);
//                List<OrderDetailsVO> orderDetailsVOList = new ArrayList<>();
//                orders.get().getOrderDetails().forEach(detail ->{
//                    if (detail.getOrder().getId() == orders.get().getId()){
//                        ProductsVO productsVO = modelMapper.map(detail.getProduct(),ProductsVO.class);
//                        CategoryVO categoryVO = this.categoryService.getOne(detail.getProduct().getCategory().getId());
//                        productsVO.setCategory(categoryVO);
//                        OrderDetailsVO orderDetailsVO = modelMapper.map(detail,OrderDetailsVO.class);
//                        orderDetailsVO.setProduct(productsVO);
//                        if (ordersVO.getPriceOrder()!=null){
//                            ordersVO.setPriceOrder(ordersVO.getPriceOrder()+detail.getPrice());
//                        }else {
//                            ordersVO.setPriceOrder(detail.getPrice());
//                        }
//                        orderDetailsVOList.add(orderDetailsVO);
//                    }
//                });
//                ordersVO.setOrderDetails(orderDetailsVOList);
//            }
//            return ordersVO;
//        }
        return null;
    }

    @Override
    public OrdersVO findOrderByIdAndAccount(Integer id, Integer idAccount) {
//        Optional<Orders> orders = repository.findById(id);
//        if (orders.isPresent() && orders.get().getAccount().getId()==idAccount){
//            OrdersVO ordersVO = new OrdersVO();
//            BeanUtils.copyProperties(orders.get(),ordersVO);
//            ordersVO.setOrderCode(orders.get().getOrderCode());
//            ordersVO.setPaymentMethods(orders.get().getPaymentMethods());
//            ordersVO.setReceived(orders.get().getReceived());
//            AccountVO accountVO = modelMapper.map(orders.get().getAccount(),AccountVO.class);
//            if (orders.get().getReceived()==1 || orders.get().getReceived()==2 || orders.get().getReceived()==3 || orders.get().getReceived()==0 || orders.get().getReceived()==-1){
//                ordersVO.setAccount(accountVO);
//                List<OrderDetailsVO> orderDetailsVOList = new ArrayList<>();
//                orders.get().getOrderDetails().forEach(detail ->{
//                    if (detail.getOrder().getId() == orders.get().getId()){
//                        ProductsVO productsVO = modelMapper.map(detail.getProduct(),ProductsVO.class);
//                        CategoryVO categoryVO = this.categoryService.getOne(detail.getProduct().getCategory().getId());
//                        productsVO.setCategory(categoryVO);
//                        OrderDetailsVO orderDetailsVO = modelMapper.map(detail,OrderDetailsVO.class);
//                        orderDetailsVO.setProduct(productsVO);
//                        if (ordersVO.getPriceOrder()!=null){
//                            ordersVO.setPriceOrder(ordersVO.getPriceOrder()+detail.getPrice());
//                        }else {
//                            ordersVO.setPriceOrder(detail.getPrice());
//                        }
//                        orderDetailsVOList.add(orderDetailsVO);
//                    }
//                });
//                ordersVO.setOrderDetails(orderDetailsVOList);
//            }
//            return ordersVO;
//        }
        return null;
    }

    @Override
    public OrdersVO saveOrders(OrdersVO vo) {
        Orders orders = modelMapper.map(vo,Orders.class);
        Optional<Account> account = this.accountRepository.findById(vo.getAccount().getId());
        if (account.isPresent()){
            orders.setAccount(account.get());
            repository.save(orders);
            vo.setId(orders.getId());
            return vo;
        }
        return null;
    }

    @Override
    public OrdersVO getByOrderCode(String code) throws Exception {
        List<Orders> orders = repository.findOrderByCodeOrder(code);
        /*System.out.println("Code orderservice: "+code);
        System.out.println("Size OrderService: "+orders.size() + orders.get(0).getId());*/
        if (orders != null && orders.size() == 1){
            return modelMapper.map(orders.get(0),OrdersVO.class);
        }else {
            return null;
        }
    }

    @Override
    public OrdersVO updateOrders(OrdersVO vo) {
        Optional<Orders> orders = this.repository.findById(vo.getId());
        vo.setAccount(modelMapper.map(orders.get().getAccount(),AccountVO.class));
        if (orders.isPresent()){
            if(vo.getReceived() == 1) {
                vo.setAuthenticator(detailsService.getAccount().getFullName());
            }
            modelMapper.map(vo,orders.get());
            repository.save(orders.get());
            return vo;
        }
        return null;
    }


    @Override
    public void deleteById(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrdersVO ordersVO =  findOrdersByAccountCart(auth.getName());
        if(ordersVO.getOrderDetails()!= null){
            ordersVO.getOrderDetails().forEach(vo -> {
                orderDetailService.deleteOrderDetail(vo.getProductsDetailVO().getSku());
            });
        }
        repository.deleteById(id);
    }

    @Override
    public List<OrdersVO> findOrdersByAccount(Integer idAccount) {
        List<OrdersVO> listVO = new ArrayList<>();
        List<Orders> ordersList = this.repository.findByIdAccount(idAccount);
        if(ordersList != null){
            repository.findByIdAccount(idAccount).forEach(orders -> {
                OrdersVO ordersVO = modelMapper.map(orders,OrdersVO.class);
                AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
                if (orders.getReceived()==1 || orders.getReceived()==2 || orders.getReceived()==3 || orders.getReceived()==0 || orders.getReceived()==-1){
                    ordersVO.setAccount(accountVO);
                    List<OrderDetailsVO> orderDetailsVOList = new ArrayList<>();
                    orders.getOrderDetails().forEach(detail ->{
                        if (detail.getOrder().getId() == orders.getId()){
                            ProductsDetailVO productsDetailVO = modelMapper.map(detail.getProductsDetail(),ProductsDetailVO.class);
                            productsDetailVO.setNameProduct(detail.getProductsDetail().getProduct().getName());
                            OrderDetailsVO orderDetailsVO = modelMapper.map(detail,OrderDetailsVO.class);
                            orderDetailsVO.setProductsDetailVO(productsDetailVO);
                            orderDetailsVOList.add(orderDetailsVO);
                        }
                    });
                    ordersVO.setOrderDetails(orderDetailsVOList);
                    listVO.add(ordersVO);
                }
            });
            return listVO;
        }
        return null;
    }

    //Lấy ra thằng order Received -2 theo account
    @Override
    public OrdersVO findOrdersByAccountCart(String email) {
        OrdersVO vo = new OrdersVO();
        Orders order1 = repository.findByEmailAccount(email);
        if(order1 != null){
            System.out.println(order1.getId());
            Orders  orders = repository.findByEmailAccount(email);
            vo = modelMapper.map(orders,OrdersVO.class);
            AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
            List<OrderDetailsVO> detailsVOList = new ArrayList<>();
            orders.getOrderDetails().forEach(orderDetails -> {
                OrderDetailsVO orderDetailsVO = modelMapper.map(orderDetails,OrderDetailsVO.class);
                detailsVOList.add(orderDetailsVO);
                ProductsDetailVO productsDetailVO = new ProductsDetailVO();
                if(orderDetails.getProductsDetail()!= null){
                    productsDetailVO = modelMapper.map(orderDetails.getProductsDetail(),ProductsDetailVO.class);
                }

                if(orderDetails.getProductsDetail().getSaleProduct() != null){
                    SaleProductVO saleProductVO = modelMapper.map(orderDetails.getProductsDetail().getSaleProduct(),SaleProductVO.class);
                    productsDetailVO.setSaleProduct(saleProductVO);
                }
//                if(orderDetails.getVouchers()!= null){
//                    VouchersVO vouchersVO = modelMapper.map(orderDetails.getVouchers(),VouchersVO.class);
//                    orderDetailsVO.setVoucher(vouchersVO);
//                }
                orderDetailsVO.setProductsDetailVO(productsDetailVO);
            });
            vo.setAccount(accountVO);
            vo.setOrderDetails(detailsVOList);
            return vo;
        }
        return null;

    }

    @Override
    public List<OrdersVO> findOrdersByAccountAndReceived(Integer id, Integer received) {
        List<OrdersVO> ordersByAccounts = this.findOrdersByAccount(id);
        List<OrdersVO> voList = ordersByAccounts.stream().filter(o -> o.getReceived() == received).collect(Collectors.toList());
        return voList;
    }

    @Override
    public Map<String, Object> getAllOrderPaging(Integer idAccount,int page, int size) {
        List<OrdersVO> voList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Orders> ordersPage = this.repository.findAllByAccountPage(idAccount,pageable);
        ordersPage.getContent().forEach(orders ->{
            OrdersVO ordersVO = modelMapper.map(orders,OrdersVO.class);
            AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
            if (orders.getReceived()==1 || orders.getReceived()==2 || orders.getReceived()==3 || orders.getReceived()==0 || orders.getReceived()==-1){
                ordersVO.setAccount(accountVO);
                List<OrderDetailsVO> orderDetailsVOList = new ArrayList<>();
                orders.getOrderDetails().forEach(detail ->{
                    if (detail.getOrder().getId() == orders.getId()){
                        ProductsDetailVO productsVO = modelMapper.map(detail.getProductsDetail(),ProductsDetailVO.class);
                        productsVO.setNameProduct(detail.getProductsDetail().getProduct().getName());
                        OrderDetailsVO orderDetailsVO = modelMapper.map(detail,OrderDetailsVO.class);
                        orderDetailsVO.setProductsDetailVO(productsVO);
                        orderDetailsVOList.add(orderDetailsVO);
                    }
                });
                ordersVO.setOrderDetails(orderDetailsVOList);
                voList.add(ordersVO);
            }
        });
        List<OrdersVO> listOrder = voList.stream().sorted(Comparator.comparing(OrdersVO::getId).reversed()).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("listOrder",listOrder);
        map.put("currentPage",page);
        map.put("totalPage",ordersPage.getTotalPages());
        return map;
    }

    @Override
    public Map<String, Object> getOrderByReceivedPaging(Integer idAccount,Integer received, int page, int size) {
        List<OrdersVO> voList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Orders> ordersPage = this.repository.findOrderByReceivedOrderPage(received,idAccount,pageable);
        ordersPage.getContent().forEach(orders ->{
            OrdersVO ordersVO = modelMapper.map(orders,OrdersVO.class);
            AccountVO accountVO = modelMapper.map(orders.getAccount(),AccountVO.class);
            ordersVO.setAccount(accountVO);
            List<OrderDetailsVO> orderDetailsVOList = new ArrayList<>();
            orders.getOrderDetails().forEach(detail ->{
                if (detail.getOrder().getId() == orders.getId()){
                    ProductsDetailVO productsVO = modelMapper.map(detail.getProductsDetail(),ProductsDetailVO.class);
                    productsVO.setNameProduct(detail.getProductsDetail().getProduct().getName());
                    OrderDetailsVO orderDetailsVO = modelMapper.map(detail,OrderDetailsVO.class);
                    orderDetailsVO.setProductsDetailVO(productsVO);
                    orderDetailsVOList.add(orderDetailsVO);
                }
            });
            ordersVO.setOrderDetails(orderDetailsVOList);
            voList.add(ordersVO);
        });
        List<OrdersVO> listOrder = voList.stream().sorted(Comparator.comparing(OrdersVO::getId).reversed()).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("listOrder",listOrder);
        map.put("currentPage",page);
        map.put("totalPage",ordersPage.getTotalPages());
        return map;
    }

    @Override
    public List<OrderResponseVO> findAllOrdersByReceived(Integer received){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<OrderResponseVO> vos = new ArrayList<>();
        repository.findAllOrdersByReceived(received).forEach(orders -> {
            OrderResponseVO vo = modelMapper.map(orders, OrderResponseVO.class);
            for(int i = 0; i< orders.getOrderDetails().size(); i++) {
                OrderDetails orderDetails = orders.getOrderDetails().get(i);
                OrderDetailResponseVO detailResponseVO = vo.getOrderDetails().get(i);
                detailResponseVO.setProductsReponse(productService.getProductBySku(orderDetails.getProductsDetail().getSku()));
            }
            vos.add(vo);
        });
        return vos;
    }
}
