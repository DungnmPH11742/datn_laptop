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
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByRoleName(accountVO.getNameRoles()));
        return repository.save(account);
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
        String senderName = "Vua c???a Laptop";
        String subject = "Vui l??ng x??c minh ????ng k?? c???a b???n";
        String content = "Dear [[name]],<br>"
                + "Vui l??ng nh???p v??o li??n k???t b??n d?????i ????? x??c minh ????ng k?? c???a b???n:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">B???n c???n click ????? x??c th???c th??ng tin</a></h3>"
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
        // inside your getSalesUserData() method
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    javaMailSender.send(message);
                } catch (Exception e) {
                    System.out.println("error mail");
                }
            }
        });
        emailExecutor.shutdown(); // it is very important to shutdown your non-singleton ExecutorService.

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
    public Account findByEmail(String email) {
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
            System.out.println("time: " + account.getTimeToken());
            long miliCurent = System.currentTimeMillis();
            Date date = account.getTimeToken();
            long diff = miliCurent - (date.getTime());
            long seconds = (diff / 1000);
            System.out.println(seconds);
            if (seconds > 3) {
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

    ;

    @Override
    public void upadteCustomerAfterOAuthLoginSuccess(Account account, String name, AuthenticationProvider provider) {
        account.setFullName(name);
        account.setAuthProvider(provider);
        repository.save(account);
    }

    ;
}
