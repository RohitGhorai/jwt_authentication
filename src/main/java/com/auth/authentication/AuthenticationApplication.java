package com.auth.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth.authentication.config.AppConstant;
import com.auth.authentication.entity.Role;
import com.auth.authentication.repo.RoleRepo;

import java.util.List;

@SpringBootApplication
public class AuthenticationApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Rohit"));
		try{
			Role role = new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("USER");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> System.out.println(r.getName()));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}