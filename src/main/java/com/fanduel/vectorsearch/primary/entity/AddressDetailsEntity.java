package com.fanduel.vectorsearch.primary.entity;

import java.util.Objects;

import org.hibernate.proxy.HibernateProxy;

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
@Table(name = "address")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class AddressDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "address_line1")
	private String address;

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
