package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.AuthenticationProvider;
import com.poly.entity.Role;
import com.poly.repo.AccountRepository;
import com.poly.repo.RoleRepository;
import com.poly.service.AccountService;
import com.poly.vo.AccountVO;
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
import javax.security.auth.login.AccountNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private @Value("${spring.mail.username}")
    String nameMail;

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
        return repository.save(account);
    }

    @Override
    public AccountVO findAccountById(Integer id) {
        Optional<Account> account = this.repository.findById(id);
        if (account.isPresent()){
            return modelMapper.map(account.get(),AccountVO.class);
        }
        return null;
    }
    @Override
    public AccountVO updateAccount(AccountVO accountVO) {
        Optional<Account> account = this.repository.findById(accountVO.getId());
        if (account.isPresent()){
            if (accountVO.getActived() == null){
                accountVO.setActived(account.get().getActived());
            }
            if (accountVO.getPassword()==null){
                accountVO.setPassword(account.get().getPassword());
            }else {
                accountVO.setPassword(bCryptPasswordEncoder.encode(accountVO.getPassword()));
            }
            if (accountVO.getEmail()==null){
                accountVO.setEmail(account.get().getEmail());
            }
            if (accountVO.getImgUrl()==null){
                accountVO.setImgUrl(account.get().getImgUrl());
            }
            Date dateSql = accountVO.getDateOfBirth();
            java.util.Date date = new java.util.Date(dateSql.getTime());
            this.modelMapper.map(accountVO,account.get());
            account.get().setDateOfBirth(date);
            this.repository.save(account.get());
            return accountVO;
        }
        return null;
    }

    @Override
    public void createAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException {
        Account account = new Account();
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
        sendVerificationEmail(account, siteURL);

    }

    @Override
    public void updateAccountByRegister(AccountVO accountVO, String siteURL) throws UnsupportedEncodingException, MessagingException {
        Account account = new Account();
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
        sendVerificationEmail(account, siteURL);

    }


    private void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = account.getEmail();
        String fromAddress = nameMail;
        String senderName = "Kingdom of Computer";
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String content = "Dear [[name]],<br>"
                + "Vui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Bạn cần click để xác thực thông tin</a></h3>"
                + "Cám ơn ,<br>"
                + "Kingdom of Computer.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", account.getFullName());
        String verifyURL = siteURL + "/verify?token=" + account.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    javaMailSender.send(message);
                } catch (Exception e) {
                }
            }
        });
        emailExecutor.shutdown();
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
    }

    ;

    @Override
    public AccountVO findByEmailUser(String email) {
        return modelMapper.map(repository.findUserAccount(email), AccountVO.class);
    }
    @Override
    public Account findByEmail(String email) {
        return repository.findUserAccount(email);
    }

    @Override
    public AccountVO findByEmailVO(String email) {
        return convertAccountToDtoById(email);
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
            long miliCurent = System.currentTimeMillis();
            Date date = account.getTimeToken();
            Date date2 = new Date(System.currentTimeMillis());
            long seconds = miliCurent - (date.getTime());
            if (seconds > 900000) {
                return false;
            } else {
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

    public void createNewCustomerAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider provider) {
        AccountVO accountVO = new AccountVO();
        accountVO.setEmail(email);
        accountVO.setFullName(name);
        accountVO.setAuthProvider(provider);
        repository.save(modelMapper.map(accountVO, Account.class));
    }


    @Override
    public void upadteCustomerAfterOAuthLoginSuccess(Account account, String name, AuthenticationProvider provider) {
        account.setFullName(name);
        account.setAuthProvider(provider);
        repository.save(account);
    }
    @Override
    public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException {
        Account account = repository.findUserAccount(email);
        if (account != null) {
            account.setResetPasswordToken(token);
            repository.save(account);
        } else {
            throw new AccountNotFoundException("Không thể tìm thấy bất kỳ tài khoản nào có email là:  " + email);
        }
    }

    @Override
    public Account getByResetPasswordToken(String token) {
        return repository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        repository.save(account);
    }


    @Override
    public void sendForgotPasswordEmail(String recipientEmail, String link) throws
            MessagingException, UnsupportedEncodingException {
        String toAddress = recipientEmail;
        String fromAddress = nameMail;
        String senderName = "Kingdom of Computer";
        String subject = "Nhấp vào link dưới đây để reset mật khẩu";
        String content = "Xin chào ,<br>"
                + "Bạn đã yêu cầu đặt lại mật khẩu của mình<br>" +
                "Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:"
                + "<p><a href=\"" + link + "\">Đổi mật khẩu</a></p>"
                + "Bỏ qua email này nếu bạn nhớ mật khẩu của mình,<br>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    javaMailSender.send(message);
                } catch (Exception e) {
                    System.out.println("Gửi email thất bại. Quý khách vui lòng kiểm tra lại");
                }
            }
        });
        emailExecutor.shutdown();

    }

    public AccountVO convertAccountToDtoById(String email) {
        AccountVO accountVO = new AccountVO();
        Account account = repository.findUserAccount(email);
        return modelMapper.map(account, AccountVO.class);
    }
}
