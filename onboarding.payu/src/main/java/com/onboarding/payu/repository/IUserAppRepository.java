package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link UserApp} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IUserAppRepository extends JpaRepository<UserApp, Integer> {
}