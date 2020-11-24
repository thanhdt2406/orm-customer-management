package repository.impl;

import model.Customer;
import repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c",Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.id=:id",Customer.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public void save(Customer model) {
        if(model.getId()!=null){
            entityManager.merge(model);
        } else{
            entityManager.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if(customer!=null){
            entityManager.remove(customer);
        }
    }
}
