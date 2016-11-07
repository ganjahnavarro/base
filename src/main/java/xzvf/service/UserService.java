package xzvf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xzvf.enums.UserType;
import xzvf.model.User;
import xzvf.repository.AbstractRepository;
import xzvf.repository.UserRepository;

@Service
@Transactional
public class UserService extends AbstractService {

	@Autowired UserRepository repository;
	
	@Override
	public AbstractRepository<User> getRepository() {
		return repository;
	}
	
	public User findByUsername(String userName) {
		return repository.findByUsername(userName);
	}
	
	public void activateUser(String token) {
		repository.activateUser(token);
	}

	public List<User> findAllUsers(UserType... types) {
		return repository.findAllUsers(types);
	}

}
