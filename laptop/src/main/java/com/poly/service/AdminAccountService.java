package com.poly.service;

import com.poly.DTO.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AdminAccountService {

    List<AccountDTO> findAllAccount();

    AccountDTO findByIdA(Integer id);

    AccountDTO createA(AccountDTO dto);

    AccountDTO updateA(AccountDTO dto);

    AccountDTO deleteA(AccountDTO dto);

}
