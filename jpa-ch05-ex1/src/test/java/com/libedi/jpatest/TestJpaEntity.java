package com.libedi.jpatest;

import java.util.List;

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

	/**
	 * 1. 회원과 팀을 저장
	 */
//	@Test
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
	
	/**
	 * 2. 조회 : 객체 그래프 탐색 사용
	 */
//	@Test
	public void testSelectByObjectGraph(){
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		
		Member member = em.find(Member.class, "member1");
		Team team = member.getTeam();		// 객체 그래프 탐색
		System.out.println("팀 이름 = " + team.getName());
		
		tx.commit();
		em.close();
	}
	
	/**
	 * 3. 조회 : 객체지향 쿼리 사용 (JPQL)
	 */
	@Test
	public void testSelectByJPQL(){
		em = emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
		
		/*
		 * 회원이 팀과 관계를 가지고 있는 필드(m.team)를 통해 Member와 Team 을 조인.
		 * 조인한 t.name을 검색조건으로 "팀1"의 회원을 검색
		 */
		String jpq1 = "select m from Member m join m.team t where t.name=:teamName";
		
		List<Member> resultList = em.createQuery(jpq1, Member.class).setParameter("teamName", "팀1").getResultList();
		
		for(Member member : resultList){
			System.out.println("[query] member.username = " + member.getUsername());
		}
		
		tx.commit();
		em.close();
	}
	
	@After
	public void afterSetup(){
		emf.close();
	}
}
