package com.kafka.consumer.example.Repository;

import com.kafka.consumer.example.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	@Query(value = "SELECT * FROM user_table ORDER BY rank", nativeQuery = true)
	List<UserEntity> sortRank();
}
