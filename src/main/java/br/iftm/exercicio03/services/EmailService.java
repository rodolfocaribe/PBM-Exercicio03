package br.iftm.exercicio03.services;

import br.iftm.exercicio03.data.vo.EmailVO;
import br.iftm.exercicio03.mapper.DozerMapper;
import br.iftm.exercicio03.models.Email;
import br.iftm.exercicio03.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;
    public List<EmailVO> findAll() {
        return DozerMapper.parseListObject(repository.findAll(), EmailVO.class);
    }
    public EmailVO findById(Long id) {
        return DozerMapper.parseObject(repository.findById(id).get(), EmailVO.class);
    }
    public EmailVO save(EmailVO emailVO) {
        if(verifyEmail(emailVO))
        {
            var email = repository.save(DozerMapper.parseObject(emailVO, Email.class));
            return DozerMapper.parseObject(email, EmailVO.class);
        }
        return null;
    }

    public EmailVO update(EmailVO emailVO) {
        var dbEmail = repository.findById(emailVO.getId());
        if(dbEmail.isPresent() && verifyEmail(emailVO)) {
            var email = repository.save(DozerMapper.parseObject(emailVO, Email.class));
            return DozerMapper.parseObject(email, EmailVO.class);
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
        if( !emailVO.getFrom().isBlank() && !emailVO.getFrom().isEmpty() &&
                !emailVO.getTo().isBlank() && !emailVO.getTo().isEmpty() &&
                !emailVO.getSubject().isBlank() && !emailVO.getSubject().isEmpty() &&
                !emailVO.getBody().isBlank() && !emailVO.getBody().isEmpty() ) {
            return true;
        }
        return false;
    }
}

