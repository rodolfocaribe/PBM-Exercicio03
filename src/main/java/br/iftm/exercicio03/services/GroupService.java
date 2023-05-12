package br.iftm.exercicio03.services;

import br.iftm.exercicio03.controllers.GroupController;
import br.iftm.exercicio03.data.vo.GroupVO;
import br.iftm.exercicio03.mapper.DozerMapper;
import br.iftm.exercicio03.models.Group;
import br.iftm.exercicio03.models.User;
import br.iftm.exercicio03.repositories.GroupRepository;
import br.iftm.exercicio03.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        var groups = DozerMapper.parseListObject(groupDbList, GroupVO.class);
        groups.stream().forEach(group -> {
            group.add(linkTo(methodOn(GroupController.class).findById(group.getId()))
                    .withSelfRel()
            );
        });
        return groups;
    }

    public GroupVO findById(Long id) {
        Optional<Group> groupDb = repository.findById(id);
        if(groupDb.isPresent())
        {
            var group = DozerMapper.parseObject(groupDb.get(), GroupVO.class);
            group.add(linkTo(methodOn(GroupController.class).findById(id)).withSelfRel());
            return group;
        }
        return null;
    }

    public GroupVO save(GroupVO groupVO) {
        if(verifyGroup(groupVO)) {
            Group group = DozerMapper.parseObject(groupVO, Group.class);
            var groupDb = repository.save(group);
            groupVO = DozerMapper.parseObject(groupDb, GroupVO.class);
            groupVO.add(linkTo(methodOn(GroupController.class).findById(groupVO.getId())).withSelfRel());
        }
        return null;
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
                    managedUser.addGroup(dbGroup);

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
        if(groupDb.isPresent() && verifyGroup(groupVO))
        {
            Group group = DozerMapper.parseObject(groupVO, Group.class);
            group = repository.save(group);
            groupVO = DozerMapper.parseObject(group, GroupVO.class);
            groupVO.add(linkTo(methodOn(GroupController.class).findById(groupVO.getId())).withSelfRel());
            return groupVO;
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
    private boolean verifyGroup(GroupVO groupVO) {
        return !groupVO.getName().isEmpty() && !groupVO.getCode().isEmpty() && !groupVO.getLink().isEmpty();
    }
}
