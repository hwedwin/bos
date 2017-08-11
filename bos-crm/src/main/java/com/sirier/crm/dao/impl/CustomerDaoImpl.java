package com.sirier.crm.dao.impl;

import com.sirier.crm.dao.CustomerDao;
import com.sirier.crm.domain.Customer;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sirierx on 2017/8/10.
 */

@Repository
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

    @Autowired
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<Customer> getListNotAssociation() {
        String hql = "from Customer where decidedzoneId = null";
        List<Customer> list = this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public List<Customer> getListHasAssociation(String decidezoneId) {
        String hql = "from Customer where decidedzoneId = ? ";
        List<Customer> list = this.getHibernateTemplate().find(hql, decidezoneId);
        return list;
    }

    @Override
    public void assignedCustomerToDecidedzone(String decidezoneId, Integer customerId) {
        String hql = "update Customer set  decidedzoneId = ?  where id = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,decidezoneId);
        query.setParameter(1,customerId);
        query.executeUpdate();
    }

    @Override
    public void cancleCustomerToDecidedzone(String decidezoneId) {
        String hql = "update Customer set  decidedzoneId = null  where decidedzoneId = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0,decidezoneId);
        query.executeUpdate();
    }
}
