package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Role;
import com.poly.repo.AccountRepository;
import com.poly.repo.ProductsRepository;
import com.poly.repo.RoleRepository;
import com.poly.service.AccountService;
import com.poly.service.ProductService;
import com.poly.vo.AccountVO;
import com.poly.vo.ProductsVO;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    private @Value("${spring.mail.username}") String nameMail;
    @Override
    public List<AccountVO> getList() {
        List<AccountVO> vos = new ArrayList<>();
        repository.findAll().forEach(accouts -> {
            vos.add(modelMapper.map(accouts, AccountVO.class));
        });
        return vos;
    }

    @Override
    public Account createAccountUser(AccountVO accountVO) {
        Account account = new Account();
        account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
        account.setFullName(accountVO.getFullName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());
        account.setActived(accountVO.getActived());
        account.setDateOfBirth(accountVO.getDateOfBirth());
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByRoleName(accountVO.getNameRoles()));
        return repository.save(account);
    }

    @Override
    public void createAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException {
            Account account = new Account() ;
            account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
            account.setFullName(accountVO.getFullName());
            account.setEmail(accountVO.getEmail());
            account.setPhone(accountVO.getPhone());
            account.setActived(false);
            account.setDateOfBirth(accountVO.getDateOfBirth());
            String randomCode = RandomString.make(64);
            account.setVerificationCode(randomCode);
            account.setTimeToken(new Date());
            repository.save(account);
            sendVerificationEmail(account,siteURL);

    }
    @Override
    public void updateAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException {
        Account account = new Account() ;
        account.setId(accountVO.getId());
        account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
        account.setFullName(accountVO.getFullName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());
        account.setActived(false);
        account.setDateOfBirth(accountVO.getDateOfBirth());
        String randomCode = RandomString.make(64);
        account.setVerificationCode(randomCode);
        account.setTimeToken(new Date());
        repository.save(account);
        sendVerificationEmail(account,siteURL);

    }


    private void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = account.getEmail();
        String fromAddress = nameMail;
        String senderName = "Vua của Laptop";
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String content = "Dear [[name]],<br>"
                + "Vui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Bạn cần click để xác thực thông tin</a></h3>"
                + "Thank you,<br>"
                + "Kingdom of Laptop.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", account.getFullName());
        String verifyURL = siteURL + "/verify?token=" + account.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    @Override
    public Account updateAccountUser(AccountVO accountVO) {
        Account account = new Account();
        account.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
        account.setFullName(accountVO.getFullName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());
        account.setTimeToken(new Date());
        account.setActived(accountVO.getActived());
        account.setDateOfBirth(accountVO.getDateOfBirth());
        return repository.save(account);
    }

    @Override
    public void deleteAccountById(Integer id) {
        repository.deleteById(id);
    };

    @Override
    public Account findByEmail(String email){
        return repository.findUserAccount(email);
    }

    @Override
    public Account findUserAccountByEmailFalse(String email) {
        return repository.findUserAccountByEmailFalse(email);
    }

    @Override
    public Account findUserAccountByToken(String token) {
        return repository.findUserAccountByToken(token);
    }

    @Override
    public boolean verify(String verificationCode) {
        Account account = repository.findByVerificationCode(verificationCode);
        if (account == null) {
            return false;
        } else {
            System.out.println("time: "+account.getTimeToken());
            long miliCurent = System.currentTimeMillis();
            Date date = account.getTimeToken();
            long diff = miliCurent - (date.getTime());
            long minutes = (diff / 1000) / 60;
            if(minutes > 1){
                return false;
            }
            else {
                account.setVerificationCode(null);
                account.setActived(true);
                List<Role> roles = new ArrayList<>();
                roles.add(roleRepository.findByRoleName("ROLE_USER"));
                account.setRoles(roles);
                account.setTimeToken(null);
                repository.save(account);
                return true;
            }
        }
    }

}
