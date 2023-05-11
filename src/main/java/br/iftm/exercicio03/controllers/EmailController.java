package br.iftm.exercicio03.controllers;

import br.iftm.exercicio03.data.vo.EmailVO;
import br.iftm.exercicio03.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/email")
public class EmailController {
    @Autowired
    private EmailService service;

    @GetMapping
    public List<EmailVO> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public EmailVO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    @PostMapping
    public EmailVO save(@RequestBody EmailVO emailVO) {
        return service.save(emailVO);
    }
    @PutMapping
    public EmailVO update(@RequestBody EmailVO emailVO) {
        return service.update(emailVO);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}

