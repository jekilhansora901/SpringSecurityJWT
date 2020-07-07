package com.killer.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.killer.data.UserData;

public interface UserRepository extends JpaRepository<UserData, Long> {
	
	public Optional<UserData> findByUserName(String userName);

}
