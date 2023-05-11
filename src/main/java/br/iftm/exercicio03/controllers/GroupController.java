package br.iftm.exercicio03.controllers;

import br.iftm.exercicio03.data.vo.GroupVO;
import br.iftm.exercicio03.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    @Autowired
    GroupService service;

    @GetMapping
    public List<GroupVO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GroupVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public GroupVO save(@RequestBody GroupVO groupVO) {
        return service.save(groupVO);
    }

    @PostMapping("insert-users")
    public GroupVO insertUsers(@RequestBody GroupVO groupVO) {
        return service.insertUsers(groupVO);
    }

    @PutMapping
    public GroupVO update(@RequestBody GroupVO groupVO) {
        return service.update(groupVO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
