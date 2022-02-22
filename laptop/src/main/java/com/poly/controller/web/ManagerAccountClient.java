package com.poly.controller.web;

import com.poly.helper.HeaderHelper;
import com.poly.payload.UploadFileResponse;
import com.poly.service.AccountService;
import com.poly.service.FileStorageService;
import com.poly.vo.AccountVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ManagerAccountClient {

    @Autowired private HeaderHelper headerHelper;
    @Autowired private AccountService accountService;
    @Autowired private FileStorageService fileStorageService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/view-account")
    public String viewForm(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            headerHelper.setHeaderSession(model);
            String[] birthday = accountVO.getDateOfBirth().toString().split("-");
            Integer dayBird = Integer.parseInt(birthday[2]);
            Integer monthBird = Integer.parseInt(birthday[1]);
            Integer yearBird = Integer.parseInt(birthday[0]);

            model.addAttribute("accountVO",accountVO);
            model.addAttribute("dayBird",dayBird);
            model.addAttribute("monthBird",monthBird);
            model.addAttribute("yearBird",yearBird);
            model.addAttribute("avatar","http://localhost:8080/viewFile/11840aac.jpg");
            return "user/form-account-manager";
        }else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/save-user")
    public String saveUser(@RequestParam("fileImageUser") MultipartFile file, @ModelAttribute AccountVO accountVO, HttpServletRequest request) throws IOException, ParseException {
        String imageUrlAccount = null;
        if (!file.isEmpty()){
            String fileName = fileStorageService.storeFileAccount(file);
            imageUrlAccount = "http://localhost:8080/viewFileAccount/"+fileName;
        }
        String day = request.getParameter("dayBird");
        String month = request.getParameter("monthBird");
        String year = request.getParameter("yearBird");
        AccountVO vo = this.accountService.findAccountById(accountVO.getId());

        accountVO.setImgUrl(imageUrlAccount);
        String birthDay = year+"-"+month+"-"+day;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = df.parse(birthDay);
        java.sql.Date dateSql = new java.sql.Date(birthDate.getTime());
        accountVO.setDateOfBirth(dateSql);

        this.accountService.updateAccount(accountVO);
        return "redirect:/view-account";
    }


    //Thay đổi mật khẩu user
    @RequestMapping(value = "/view-change-password")
    public String viewFormPass(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            headerHelper.setHeaderSession(model);
            if (session.getAttribute("passwordOldError") != null){
                model.addAttribute("passwordOldError","Mật khẩu cũ không chính xác vui lòng thử lại");
            }else if (session.getAttribute("errorRePassword") != null){
                model.addAttribute("passwordOldError","Mật khẩu mới không khớp nhau hãy nhập lại");
            }else if (session.getAttribute("successChangePass") != null){
                model.addAttribute("success","Chúc mừng bạn đã thay đổi mật khẩu thành công");
            }
            session.removeAttribute("passwordOldError");
//            session.removeAttribute("success");
            session.removeAttribute("successChangePass");
            session.removeAttribute("errorRePassword");
            return "user/form-change-password";
        }else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/change-password")
    public String savePassword(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            HttpSession session = request.getSession();
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            String oldPassword = request.getParameter("oldpassword");
            String newPassword = request.getParameter("newpassword");
            String reNewPassword = request.getParameter("renewpassword");
            boolean check = bCryptPasswordEncoder.matches(oldPassword,accountVO.getPassword());
            boolean checkRepeatPassword = newPassword.equals(reNewPassword);
            if (!check){
                session.setAttribute("passwordOldError",true);
            }else if (!checkRepeatPassword){
                session.setAttribute("errorRePassword",true);
            }else {
                accountVO.setPassword(newPassword);
                this.accountService.updateAccount(accountVO);
                session.setAttribute("successChangePass",true);
            }
        }
        return "redirect:/view-change-password";
    }
}
