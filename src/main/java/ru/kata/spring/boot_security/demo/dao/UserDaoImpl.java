package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }


    @Override
    public void updateUser(Long id, User user) {
        entityManager.merge(user);

    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));

    }

    @Override
    public User findByUserName(String name) {
        return entityManager.createQuery("SELECT user FROM User user WHERE user.name=:name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
