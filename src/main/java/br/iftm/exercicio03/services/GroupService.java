package br.iftm.exercicio03.services;

import br.iftm.exercicio03.data.vo.GroupVO;
import br.iftm.exercicio03.data.vo.UserVO;
import br.iftm.exercicio03.mapper.DozerMapper;
import br.iftm.exercicio03.models.Group;
import br.iftm.exercicio03.models.User;
import br.iftm.exercicio03.repositories.GroupRepository;
import br.iftm.exercicio03.repositories.UserRepository;
import com.mysql.cj.xdevapi.DbDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    GroupRepository repository;

    @Autowired
    UserRepository userRepository;

    public List<GroupVO> findAll() {
        var groupDbList = repository.findAll();
        return DozerMapper.parseListObject(groupDbList, GroupVO.class);
    }

    public GroupVO findById(Long id) {
        Optional<Group> groupDb = repository.findById(id);
        if(groupDb.isPresent())
            return DozerMapper.parseObject(groupDb.get(), GroupVO.class);
        return null;
    }

    public GroupVO save(GroupVO groupVO) {
        Group group = DozerMapper.parseObject(groupVO, Group.class);
        var groupDb = repository.save(group);
        return DozerMapper.parseObject(groupDb, GroupVO.class);
    }

    public GroupVO insertUsers(GroupVO groupVO) {

        List<User> users = new ArrayList<>();

        var dbGroupOp = repository.findById(groupVO.getId());
        if(dbGroupOp.isPresent())
        {
            var dbGroup = dbGroupOp.get();
            for(var user : groupVO.getUsers()) {
                Optional<User> managedUserOpt = userRepository.findById(user.getId());
                if (managedUserOpt.isPresent())
                {
                    User managedUser = managedUserOpt.get();
                    users.add(managedUser);
                    managedUser.addGroup(dbGroup); // Use o método helper para sincronizar a relação bidirecional

                }
            }

            dbGroup.setUsers(users);
            dbGroup = repository.save(dbGroup);
            return DozerMapper.parseObject(dbGroup, GroupVO.class);
        }
        return null;
    }

    public GroupVO update(GroupVO groupVO) {
        Optional<Group> groupDb = repository.findById(groupVO.getId());
        if(groupDb.isPresent())
        {
            Group group = DozerMapper.parseObject(groupVO, Group.class);
            group = repository.save(group);
            return DozerMapper.parseObject(group, GroupVO.class);
        }
        return null;
    }

    public String delete(Long id) {
        var groupDb = repository.findById(id);
        if(groupDb.isPresent()) {
            repository.deleteById(id);
            return "Group with id " + id + " has been deleted!";
        }else
            return "Group ID " + id + " not found!";
    }
}
