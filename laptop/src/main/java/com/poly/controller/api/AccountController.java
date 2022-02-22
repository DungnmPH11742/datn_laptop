package com.poly.controller.api;

import com.poly.DTO.AccountDTO;
import com.poly.entity.Account;
import com.poly.service.AccountService;
import com.poly.service.AdminAccountService;
import com.poly.service.RoleService;
import com.poly.vo.AccountVO;
import com.poly.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin("http://localhost:4200")
@RequestMapping("/admin/account")
public class AccountController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService service;

    @Autowired
    private AdminAccountService adminAccountService;

    @GetMapping("/role")
    public ResponseEntity<List<RoleVO>> getAllRole(){
        List<RoleVO> list = roleService.getAllRole();
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.ok(list);
        }

    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> findAll(){
        return ResponseEntity.ok(adminAccountService.findAllAccount());
    }

    @PutMapping()
    public ResponseEntity<AccountDTO> save(@RequestBody AccountDTO accountVO){

        if(accountVO.getId() == null){
            accountVO = adminAccountService.createA(accountVO);
            System.out.println(accountVO);
            return ResponseEntity.ok(accountVO);
        }else if(accountVO.getId() != null){
            accountVO = adminAccountService.updateA(accountVO);
            System.out.println(accountVO);
            return ResponseEntity.ok(accountVO);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> delete(@PathVariable("id") Integer id){
        System.out.println("Delete" + id);
        if(id.equals("") || id == null){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else{
            System.out.println("2");
            if(adminAccountService.findByIdA(id) != null){
                System.out.println("3");
                System.out.println(adminAccountService.findByIdA(id));
                return ResponseEntity.ok(adminAccountService.deleteA(adminAccountService.findByIdA(id)));
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
