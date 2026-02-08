package com.aws.app.springdbcross.Repo;

import com.aws.app.springdbcross.Eo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
