package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getListUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }


   ////// rev
   @Override
   public User find(String model, int series) {
      /*
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from User u where u.car.model =: model and u.car.series =: series")
              .setParameter("model", model)
              .setParameter("series", series);
       */

      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("select id from Car where model =: model and series =: series")
              .setParameter("model", model)
              .setParameter("series", series);
      return sessionFactory.getCurrentSession().find(User.class, query.getSingleResult());
   }
}
