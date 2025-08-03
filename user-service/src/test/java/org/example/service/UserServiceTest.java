//package org.example.service;
//
//import org.example.dao.UserDAO;
//import org.example.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Disabled
//class UserServiceTest {
//
//    private UserDAO userDAO;
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    void setUp() {
//        userDAO = mock(UserDAO.class);
//        userService = new UserServiceImpl(userDAO);
//    }
//
//    @Test
//    void testRegisterUser() {
//        User user = new User();
//        user.setName("Anna");
//
//        userService.registerUser(user);
//
//        verify(userDAO, times(1)).save(user);
//    }
//
//    @Test
//    void testGetUser() {
//        User user = new User();
//        user.setId(1L);
//        user.setName("John");
//
//        when(userDAO.findById(1L)).thenReturn(user);
//
//        User result = userService.getUser(1L);
//
//        assertEquals("John", result.getName());
//        verify(userDAO).findById(1L);
//    }
//
//    @Test
//    void testGetAllUsers() {
//        List<User> users = Arrays.asList(new User(), new User());
//        when(userDAO.findAll()).thenReturn(users);
//
//        List<User> result = userService.getAllUsers();
//
//        assertEquals(2, result.size());
//        verify(userDAO).findAll();
//    }
//
//    @Test
//    void testUpdateUser() {
//        User user = new User();
//        user.setId(1L);
//
//        userService.updateUser(user);
//
//        verify(userDAO).update(user);
//    }
//
//    @Test
//    void testDeleteUser() {
//        userService.deleteUser(5L);
//
//        verify(userDAO).delete(5L);
//    }
//}
//
