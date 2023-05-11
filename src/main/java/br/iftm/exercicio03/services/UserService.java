package br.iftm.exercicio03.services;

import br.iftm.exercicio03.data.vo.UserVO;
import br.iftm.exercicio03.mapper.DozerMapper;
import br.iftm.exercicio03.models.User;
import br.iftm.exercicio03.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserVO> findAll() {
        var users = repository.findAll();
        var usersVO = DozerMapper.parseListObject(users, UserVO.class);
        return usersVO;
    }

    public UserVO findById(Long id) {
        return DozerMapper.parseObject(repository.findById(id).get(), UserVO.class);
    }

    public List<UserVO> findByGroupName(String groupName) {
        List<User> users = repository.findUsersByGroupName(groupName);
        return DozerMapper.parseListObject(users, UserVO.class);
    }

    public UserVO save(UserVO userVO) {
        if(verifyUser(userVO)) {
            var user = repository.save(DozerMapper.parseObject(userVO, User.class));
            return DozerMapper.parseObject(user, UserVO.class);
        }
        return null;
    }

    public UserVO update(UserVO userVO) {
        var dbUser = repository.findById(userVO.getId());
        if(dbUser.isPresent() && verifyUser(userVO))
        {
            var user = DozerMapper.parseObject(userVO, User.class);
            return DozerMapper.parseObject(repository.save(user), UserVO.class);
        }
        return null;
    }

    public String delete(Long id) {
        var dbUser = repository.findById(id);
        if(dbUser.isPresent()) {
            repository.deleteById(id);
            return "User with id " + id + " has been deleted!";
        }else
            return "ID " + id + " not found!";
    }

    private boolean verifyUser(UserVO userVO) {
        if( !userVO.getFirstName().isBlank() && !userVO.getFirstName().isEmpty() &&
                !userVO.getLastName().isBlank() && !userVO.getLastName().isEmpty()) {
            return true;
        }
        return false;
    }
}
