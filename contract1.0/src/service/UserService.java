package service;

import java.util.ArrayList;
import java.util.List;

import dao.RightDao;
import dao.RoleDao;
import dao.UserDao;
import dao.impl.RightDaoImpl;
import dao.impl.RoleDaoImpl;
import dao.impl.UserDaoImpl;
import model.Role;
import model.User;
import utils.AppException;

/*
 * *�û�ҵ���߼���
 */
public class UserService {
	
	private  UserDao userDao = null;//����һ��userDao�ӿڶ���
	private RoleDao roleDao = null;// Define a roleDao interface object
	private RightDao rightDao = null;// Define a userDao rightDao object
	/*
	 * �޲ι��췽��������ʼ��UserDaoʵ��
	 */
	public UserService(){
		userDao = new UserDaoImpl();
		roleDao = new RoleDaoImpl();
		rightDao = new RightDaoImpl();
	}
	
	
	
	
	
	/*
	 * �û�ע��
	 * user �û�����
	 * return ע��ɹ�����true�����򷵻�false
	 * throws Appexception
	 */
	public boolean register(User user) throws AppException{
		boolean flag = false;//�����ʶ
		
		try{
			if(!userDao.isExist(user.getName())){//���û�������ʱ����ִ�б������
				flag = userDao.add(user);//������������ظ�flag
			}
		}catch(AppException e){
			e.printStackTrace();
			throw new AppException("servce.UserService.register");
		}
		
		//����flag
		return flag;
	}
	
	
	/*
	 * �����û���¼
	 * name �û���
	 * password ����
	 * return ����������ѯƥ����û���ţ����򷵻�0
	 * throws AppException
	 */
	public int login(String name,String password) throws AppException{
		int userId = -1;//�����û����
		try{
			//��ȡ�û����
			userId = userDao.login(name, password);
		}catch(AppException e){
			e.printStackTrace();
			throw new AppException("servce.UserService.login");
		}
		//�����û����
		return userId;
	}
	
	
	/**
	 * Get the role information that corresponding to the user
	 * 
	 * @param userId 
	 * @return Role object
	 * @throws AppException
	 */
	public Role getUserRole(int userId) throws AppException {	
		Role role = null;// Declare role
		int roleId = -1; // Initialize  roleId
		try {
			//  Get the roleId that corresponding to the user
			roleId = rightDao.getRoleIdByUserId(userId);
			if(roleId > 0){
				// Get role information
				role = roleDao.getById(roleId); 
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("service.UserService.getUserRole");
		}
		return role;
	}
	
	/**
	 * Get user list that corresponding to the role
	 * 
	 * @param roleId 
	 * @return User list
	 * @throws AppException
	 */
	public List<User> getUserListByRoleId(int roleId) throws AppException {
		// Initialize  userList
		List<User> userList = new ArrayList<User>();
		// Declare userIds
		List<Integer> userIds = null; 
		
		try {
			/*
			 * 1.Get designated user's userIds
			 */
			userIds = rightDao.getUserIdsByRoleId(roleId);
			
			/*
			 * 2.Get user information list according to userIds
			 */ 
			for (int userId : userIds) {
				// Get user's information
				User user = userDao.getById(userId);
				if (user != null) {
					userList.add(user); 
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("service.UserService.getUserList");
		}	
		// Return userList
		return userList;
	}
}
	
	
	
	
	
	
	
	
	
	

