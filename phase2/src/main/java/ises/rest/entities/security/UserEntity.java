package ises.rest.entities.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sim_user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired = true;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked = true;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired = true;

	@Column(name = "enabled")
	private Boolean enabled = true;

}
