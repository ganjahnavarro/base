package xzvf.repository;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import xzvf.Utility;
import xzvf.model.IRecord;

@Transactional
public abstract class AbstractRepository<T> {

	@Autowired private SessionFactory sessionFactory;
	
	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(IRecord record) {
		record.setEntryBy(record.getEntryBy() != null ?
				record.getEntryBy() : Utility.getUser());
			
		record.setEntryDate(record.getEntryDate() != null ?
				record.getEntryDate() : new Date());
		
		getSession().persist(record);
	}

	public void merge(IRecord record) {
		getSession().merge(record);
	}

	public void delete(IRecord record) {
		getSession().delete(record);
	}
	
	public void deleteRecordById(String entityName, Integer id) {
		Query query = getSession().createSQLQuery("delete from " + entityName + " where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll(){
		return findAll(null);
	}
	
	@SuppressWarnings("rawtypes")
	public List findAll(String orderBy){
		Criteria criteria = createEntityCriteria();
		if (orderBy != null) {
			criteria.addOrder(Order.desc(orderBy));
		}
		
		return criteria.list();
	}

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

}