package com.libedi.jpatest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.libedi.jpatest.model.Member;
import com.libedi.jpatest.model.Team;

public class TestJpaEntity {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;
	
	@Before
	public void setup(){
		emf = Persistence.createEntityManagerFactory("jpatest");
	}

	@Test
	public void testSave(){
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		
		// 팀1 저장
		Team team1 = new Team("team1", "팀1");
		em.persist(team1);
		
		// 회원1 저장
		Member member1 = new Member("member1", "회원1");
		member1.setTeam(team1);		// 연관관계 설정 member1 -> team1
		em.persist(member1);
		
		// 회원2 저장
		Member member2 = new Member("member2", "회원2");
		member2.setTeam(team1);		// 연관관계 설정 member2 -> team1
		em.persist(member2);
		
		tx.commit();
		em.close();
	}
	
	@After
	public void afterSetup(){
		emf.close();
	}
}
