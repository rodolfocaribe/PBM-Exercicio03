package br.iftm.exercicio03.controllers;

import br.iftm.exercicio03.data.vo.UserVO;
import br.iftm.exercicio03.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<UserVO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @GetMapping("group/{name}")
    public List<UserVO> findUsersByGroupName(@PathVariable("name") String groupName) {
        return service.findByGroupName(groupName);
    }

    @PostMapping
    public UserVO save(@RequestBody UserVO userVO) {
        return service.save(userVO);
    }

    @PutMapping
    public UserVO update(@RequestBody UserVO userVO) {
        return service.update(userVO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
