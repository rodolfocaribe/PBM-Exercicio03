package br.iftm.exercicio03.services;

import br.iftm.exercicio03.controllers.EmailController;
import br.iftm.exercicio03.data.vo.EmailVO;
import br.iftm.exercicio03.mapper.DozerMapper;
import br.iftm.exercicio03.models.Email;
import br.iftm.exercicio03.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;
    public List<EmailVO> findAll() {
        var emailDbList = repository.findAll();
        var emails = DozerMapper.parseListObject(emailDbList, EmailVO.class);
        emails.stream().forEach(email ->{
            email.add(linkTo(methodOn(EmailController.class).findById(email.getId()))
                    .withSelfRel()
            );
        });
        return emails;
    }

    public EmailVO findById(Long id) {
        Optional<Email> emailDb = repository.findById(id);
        if(emailDb.isPresent())
        {
            var email = DozerMapper.parseObject(emailDb.get(), EmailVO.class);
            email.add(linkTo(methodOn(EmailController.class).findById(id)).withSelfRel());
            return email;
        }
        return null;
    }

    public EmailVO save(EmailVO emailVO) {
        if(verifyEmail(emailVO))
        {
            Email email = DozerMapper.parseObject(emailVO, Email.class);
            var emailDb = repository.save(email);
            emailVO = DozerMapper.parseObject(emailDb, EmailVO.class);
            emailVO.add(linkTo(methodOn(EmailController.class).findById(emailVO.getId())).withSelfRel());
        }
        return null;
    }

    public EmailVO update(EmailVO emailVO) {
        Optional<Email> dbEmail = repository.findById(emailVO.getId());
        if(dbEmail.isPresent() && verifyEmail(emailVO)) {
            Email email = DozerMapper.parseObject(emailVO, Email.class);
            email = repository.save(email);
            emailVO = DozerMapper.parseObject(email, EmailVO.class);
            emailVO.add(linkTo(methodOn(EmailController.class).findById(emailVO.getId())).withSelfRel());
            return emailVO;
        }
        return null;
    }

    public String delete(Long id) {
        var dbEmail = repository.findById(id);
        if(dbEmail.isPresent()) {
            repository.deleteById(id);
            return "Email with id " + id + " has been deleted!";
        }
        return "ID " + id + " not found!";
    }

    private boolean verifyEmail(EmailVO emailVO) {
        return !emailVO.getFrom().isBlank() && !emailVO.getFrom().isEmpty() &&
                !emailVO.getTo().isBlank() && !emailVO.getTo().isEmpty() &&
                !emailVO.getSubject().isBlank() && !emailVO.getSubject().isEmpty() &&
                !emailVO.getBody().isBlank() && !emailVO.getBody().isEmpty();
    }
}

