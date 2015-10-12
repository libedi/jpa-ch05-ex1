package com.libedi.jpatest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
	
	@Id
	@Column(name="MEMBER_ID")
	private String id;
	private String username;
	
	// 연관관계 매핑
	@ManyToOne
	@JoinColumn(name="TEAM_ID")		// JoinColumn 은 외래키를 매핑할 때 사용
	private Team team;

	public Member(){}
	
	public Member(String id, String username) {
		this.id = id;
		this.username = username;
	}
	
	// 연관관계 설정
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Team getTeam() {
		return team;
	}
}
