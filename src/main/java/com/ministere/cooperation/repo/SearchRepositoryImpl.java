package com.ministere.cooperation.repo;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ministere.cooperation.model.International.DocumentFiltre;
import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.model.International.Etat;
import com.ministere.cooperation.model.International.TypeDocumentInter;
import com.ministere.cooperation.model.National.DocumentNational;
import com.ministere.cooperation.model.National.TypeNational;

@Repository
public class SearchRepositoryImpl implements SearchRepository {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public List<DocumentInter> search(DocumentFiltre filtre) {
		org.hibernate.Session currentSession = getSession();
		String queryString = "from DocumentInter d";
		Field[] fields = filtre.getClass().getDeclaredFields();
		int[] states = new int[fields.length];
		int i = 0;
		for (Field field : fields) {

			try {
				if (field.get(filtre) != null && !(field.get(filtre).toString().trim().isEmpty())) {
						if (queryString.contains("WHERE"))
							queryString = queryString + " AND d." + field.getName() + " LIKE :" + field.getName();
						else
							queryString = queryString + " WHERE d." + field.getName() + " LIKE :" + field.getName();
						states[i] = 1;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		Query<DocumentInter> query = currentSession.createQuery(queryString, DocumentInter.class);
		i = 0;
		for (Field field : fields) {
			if (states[i] == 1) {
				try {
					
					if( !field.getType().isEnum())
						query.setParameter(field.getName(), field.get(filtre));
					else {
						if(field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1).equals("Etat"))
							query.setParameter(field.getName(),Etat.valueOf(field.get(filtre).toString()));
						if(field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1).equals("TypeDocumentInter"))
							query.setParameter(field.getName(),TypeDocumentInter.valueOf(field.get(filtre).toString()));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			i++;
		}
		System.out.println(query.getQueryString());

		return query.getResultList();
	}
	
	
	@Override
	public List<DocumentNational> search(DocumentNational filtre) {
		org.hibernate.Session currentSession = getSession();
		String queryString = "from DocumentNational d";
		Field[] fields = filtre.getClass().getDeclaredFields();
		int[] states = new int[fields.length];
		int i = 0;
		for (Field field : fields) {

			try {
				if (field.get(filtre) != null && !(field.get(filtre).toString().trim().isEmpty())  && field.getName()!="id") {
						if (queryString.contains("WHERE"))	
							queryString = queryString + " AND d." + field.getName() + " LIKE :" + field.getName();
						else
							queryString = queryString + " WHERE d." + field.getName() + " LIKE :" + field.getName();
						states[i] = 1;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		Query<DocumentNational> query = currentSession.createQuery(queryString, DocumentNational.class);
		i = 0;
		for (Field field : fields) {
			if (states[i] == 1) {
				try {
					
					if( !field.getType().isEnum()  && field.getName()!="id")
						query.setParameter(field.getName(), field.get(filtre));
					else {
						if(field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1).equals("Etat"))
							query.setParameter(field.getName(),Etat.valueOf(field.get(filtre).toString()));
						if(field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1).equals("TypeNational"))
							query.setParameter(field.getName(),TypeNational.valueOf(field.get(filtre).toString()));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			i++;
		}
		System.out.println(query.getQueryString());
		return query.getResultList();
	}

}
