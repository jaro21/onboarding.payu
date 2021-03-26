package com.onboarding.payu.repository;

import com.onboarding.payu.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link UserApp} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IUserAppRepository extends JpaRepository<UserApp, Integer> {
}