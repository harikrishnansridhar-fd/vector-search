package com.fanduel.vectorsearch.secondary.entity;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "person_details_mini_llm")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
public class PersonDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_details_id_seq")
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "ssn4")
	private String ssn4;
	@Column(name = "ssn9")
	private String ssn9;
	@Column(name = "birth_date")
	private String birthDate;
	@Column(name = "zip")
	private String zip;
	@Column(name = "embeddings" )
	@JdbcTypeCode(SqlTypes.VECTOR)
	@Array(length = 384)
	private float[] embeddings;
	private Long similarityScore;

	@Override
	public final boolean equals( final Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( o == null ) {
			return false;
		}
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ( (HibernateProxy) o ).getHibernateLazyInitializer()
				.getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ( (HibernateProxy) this ).getHibernateLazyInitializer()
				.getPersistentClass() : this.getClass();
		if ( thisEffectiveClass != oEffectiveClass ) {
			return false;
		}
		final PersonDetailsEntity that = (PersonDetailsEntity) o;
		return getId() != null && Objects.equals( getId(), that.getId() );
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ( (HibernateProxy) this ).getHibernateLazyInitializer()
				.getPersistentClass()
				.hashCode() : getClass().hashCode();
	}
}
