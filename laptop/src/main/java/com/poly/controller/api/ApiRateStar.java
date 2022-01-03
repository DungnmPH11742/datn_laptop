package com.poly.controller.api;

import com.poly.entity.*;
import com.poly.repo.OrderDetailsRepository;
import com.poly.repo.OrdersRepository;
import com.poly.repo.ProductRatingRepository;
import com.poly.service.AccountService;
import com.poly.service.ContactService;
import com.poly.service.ProductRatingService;
import com.poly.service.ProductService;
import com.poly.vo.AccountVO;
import com.poly.vo.ContactVO;
import com.poly.vo.ProductRatingVO;
import com.poly.vo.ProductsVO;
import com.poly.vo.response.ReponseObjectRate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiRateStar {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ProductRatingService productRatingService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;


    public ReponseObjectRate starRatingNotAccout(String productId, Double total, Integer count) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();
        List<ProductRatingVO> productRatingVOList = productRatingService.findAllByProduct_Id(productId);
        for (ProductRatingVO x : productRatingVOList) {
            System.err.println(x.getStarRating());
            total += x.getStarRating();
            count++;
        }
        reponseObjectRate.setStar(roundToHalf(total / count));
        reponseObjectRate.setStarText(roundTo2DecimalPlaces(total / count));
        return reponseObjectRate;
    }

    public ReponseObjectRate starRatingNotNullAccout(String productId, Double total, Integer count, Double starTextAccount) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();

        List<ProductRatingVO> productRatingVOList = productRatingService.findAllByProduct_Id(productId);
        for (ProductRatingVO x : productRatingVOList) {
            System.err.println(x.getStarRating());
            total += x.getStarRating();
            count++;
        }
        reponseObjectRate.setStar(roundToHalf(total / count));
        reponseObjectRate.setStarText(roundTo2DecimalPlaces(total / count));
        reponseObjectRate.setStarTextAccount(starTextAccount);

        return reponseObjectRate;
    }

    @GetMapping("/rateStar")
    public ReponseObjectRate createProductRatingVO(@RequestParam(name = "product", defaultValue = "") String productId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Double total = 0.0d;
        Integer count = 0;
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            return starRatingNotAccout(productId, total, count);
        }

        List<Orders> ordersList = ordersRepository.findAllByAccount_EmailAndCompletionDateNotNull(auth.getName());
        if (ordersList.isEmpty()) {

            return starRatingNotAccout(productId, total, count);
        }

        boolean booleanOrder = false;

        for (Orders orders : ordersList) {
            if (orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId) != null) {
                booleanOrder = true;
                break;
            } else {
                booleanOrder = false;
            }
        }
        if (booleanOrder == true) {
            ProductRating productRating = productRatingService.findProductRatingByAccountAndProduct(auth.getName(), productId);

            if (productRating != null) {
                Double star = productRating.getStarRating();
                return starRatingNotNullAccout(productId, total, count, star);
            }

            return starRatingNotNullAccout(productId, total, count, null);
        }

        return starRatingNotAccout(productId, total, count);
    }


    public ReponseObjectRate starRatingNotAccoutPost(String productId, Double total, Integer count, String message, String errors) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();

        List<ProductRatingVO> productRatingVOList = productRatingService.findAllByProduct_Id(productId);
        for (ProductRatingVO x : productRatingVOList) {
            total += x.getStarRating();
            count++;
        }
        reponseObjectRate.setStar(roundToHalf(total / count));
        reponseObjectRate.setStarText(roundTo2DecimalPlaces(total / count));
        reponseObjectRate.setMessage(message);
        reponseObjectRate.setErrors(errors);
        return reponseObjectRate;
    }

    public ReponseObjectRate starRatingNotNullAccoutPost(String productId, Double total, Integer count, Double starTextAccount, String message, String errors, ProductRatingVO productRatingVO) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();
        List<ProductRatingVO> productRatingVOList = productRatingService.findAllByProduct_Id(productId);

        for (ProductRatingVO x : productRatingVOList) {
            total += x.getStarRating();
            count++;
        }
        reponseObjectRate.setStar(roundToHalf(total / count));
        reponseObjectRate.setStarText(roundTo2DecimalPlaces(total / count));
        reponseObjectRate.setStarTextAccount(starTextAccount);
        reponseObjectRate.setMessage(message);
        reponseObjectRate.setErrors(errors);
        reponseObjectRate.setData(productRatingVO);
        return reponseObjectRate;
    }

    @PostMapping("/rateStar")
    public ReponseObjectRate createProductRatingVO(@RequestParam(name = "product", defaultValue = "") String productId,
                                                   @RequestParam(name = "rate", defaultValue = "") Double rate,
                                                   @RequestParam(name = "comment_content", defaultValue = "") String comment_content
    ) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Double total = 0.0d;
        Integer count = 0;
        String errors;
        String message;
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            errors = "Để đánh giá hãy đăng nhập và mua sản phẩm của chúng tôi";
            return starRatingNotAccoutPost(productId, total, count, null, errors);
        }
        if (comment_content.isEmpty()) {
            errors = "Nội dung bình luận không được để trống";
            reponseObjectRate.setErrors(errors);
            return reponseObjectRate;
        }
        List<Orders> ordersList = ordersRepository.findAllByAccount_EmailAndCompletionDateNotNull(auth.getName());

        if (ordersList.isEmpty()) {
            errors = "Rất tiếc bạn không thể đánh giá hoặc bình luận sản phẩm nếu chưa mua ";
            return starRatingNotAccoutPost(productId, total, count, null, errors);
        }

        ProductRatingVO productRatingVO = new ProductRatingVO();
        //   OrderDetails orderDetails = new OrderDetails();
        boolean booleanOrder = false;

        for (Orders orders : ordersList) {
            if (orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId) != null) {
                booleanOrder = true;
                //  orderDetails = orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId);
                break;
            } else {
                booleanOrder = false;
            }
        }

        if (booleanOrder == true) {
            ProductRating productRating = productRatingService.findProductRatingByAccountAndProduct(auth.getName(), productId);
            AccountVO accountVO = accountService.findByEmailVO(auth.getName());
            ProductsVO productsVO = productService.getOne(productId);
            productRatingVO.setStartComment(new Date());
            message = "Cám ơn bạn đã đánh giá và bình luận. Nếu sản phẩm có gì sai sót vui lòng liên hệ chúng tôi để được hỗ trợ";
            if (productRating != null) {
                errors = "Bạn đã đánh giá và bình luận sản phẩm rồi không thể tiếp tục";
                return starRatingNotAccoutPost(productId, total, count, null, errors);
            }
            productRatingVO.setId(productRating.getId());
            productRatingVO.setAccount(accountVO);
            productRatingVO.setProduct(productsVO);
            productRatingVO.setStarRating(rate);
            productRatingService.create(productRatingVO);
            return starRatingNotNullAccoutPost(productId, total, count, rate, message, null, productRatingService.create(productRatingVO));


        }
        errors = "Rất tiếc bạn không thể đánh giá sản phẩm nếu chưa mua từ phía chúng tôi";
        return starRatingNotAccoutPost(productId, total, count, null, errors);


    }

    public Double roundToHalf(Double star) {
        return Math.round(star * 2) / 2.0;
    }

    public static double roundTo2DecimalPlaces(double d) {
        return Math.round(d * 100.0) / 100.0;
    }

    public ReponseObjectRate starRating(String errors) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();
        reponseObjectRate.setErrors(errors);

        return reponseObjectRate;
    }

    @GetMapping("/checkRateStar")
    public ReponseObjectRate checkProductRatingVO(@RequestParam(name = "product", defaultValue = "") String productId
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String errors;
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            errors = "Để đánh giá hãy đăng nhập và mua sản phẩm của chúng tôi";
            return starRating(errors);
        }

        List<Orders> ordersList = ordersRepository.findAllByAccount_EmailAndCompletionDateNotNull(auth.getName());

        if (ordersList.isEmpty()) {
            errors = "Rất tiếc bạn không thể đánh giá hoặc bình luận sản phẩm nếu chưa mua từ phía chúng tôi";
            return starRating(errors);
        }

        boolean booleanOrder = false;

        for (Orders orders : ordersList) {
            if (orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId) != null) {
                booleanOrder = true;
                //  orderDetails = orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId);
                break;
            } else {
                booleanOrder = false;
            }
        }

        if (booleanOrder == true) {
            ProductRating productRating = productRatingService.findProductRatingByAccountAndProduct(auth.getName(), productId);
            if (productRating != null) {
                errors = "Bạn đã đánh giá và bình luận sản phẩm rồi không thể tiếp tục";
                return starRating(errors);
            }
            return starRating(null);
        }

        return starRating(null);

    }


    @PostMapping("/commentProduct")
    public ReponseObjectRate createCommentProductVO(@RequestParam(name = "product", defaultValue = "") String productId,
                                                    @RequestParam(name = "comment_content", defaultValue = "") String comment_content,
                                                    @RequestParam(name = "rate", defaultValue = "") Double rate
    ) {
        ReponseObjectRate reponseObjectRate = new ReponseObjectRate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String errors;
        String message;
        if (comment_content.isEmpty()) {
            errors = "Nội dung bình luận không được để trống";
            reponseObjectRate.setErrors(errors);
            return reponseObjectRate;
        }
        if (auth == null || auth.getPrincipal() == "anonymousUser") {
            errors = "Hãy đăng nhập để đánh giá và bình luận";
            reponseObjectRate.setErrors(errors);
            return reponseObjectRate;
        }

        List<Orders> ordersList = ordersRepository.findAllByAccount_EmailAndCompletionDateNotNull(auth.getName());

        if (ordersList.isEmpty()) {
            errors = "Rất tiếc bạn không thể đánh giá và bình luận sản phẩm chưa mua";
            reponseObjectRate.setErrors(errors);
            return reponseObjectRate;
        }

        ProductRatingVO productRatingVO = new ProductRatingVO();
        //   OrderDetails orderDetails = new OrderDetails();
        boolean booleanOrder = false;

        for (Orders orders : ordersList) {
            if (orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId) != null) {
                booleanOrder = true;
                //  orderDetails = orderDetailsRepository.findByOrder_IdAndProduct_Id(orders.getId(), productId);
                break;
            } else {
                booleanOrder = false;
            }
        }

        if (booleanOrder == true) {
            ProductRating productRating = productRatingService.findProductRatingByAccountAndProduct(auth.getName(), productId);
            AccountVO accountVO = accountService.findByEmailVO(auth.getName());
            ProductsVO productsVO = productService.getOne(productId);
            if (productRating != null) {
                errors = "Bạn đã đánh giá và bình luận sản phẩm rồi không thể tiếp tục";
                reponseObjectRate.setErrors(errors);
                return reponseObjectRate;
            }

            productRatingVO.setAccount(accountVO);
            productRatingVO.setProduct(productsVO);
            productRatingVO.setComment(comment_content);
            productRatingVO.setStartComment(new Date());
            productRatingVO.setStarRating(rate);
            reponseObjectRate.setData(productRatingService.create(productRatingVO));
            message = "Bình luận thành công";
            reponseObjectRate.setMessage(message);
            return reponseObjectRate;
        }
        errors = "Rất tiếc bạn không thể bình luận sản phẩm nếu chưa mua từ phía chúng tôi";
        reponseObjectRate.setErrors(errors);
        return reponseObjectRate;
    }


    @GetMapping("/showComment/{page}")
    public Page<ProductRatingVO> showCommentProductRatingVO(@PathVariable(name = "page") Integer page,@RequestParam(name = "product", defaultValue = "") String productId) {

        return productRatingService.getAllProductRatingVOByPageNumber(page, 5,productId);
    }
}
