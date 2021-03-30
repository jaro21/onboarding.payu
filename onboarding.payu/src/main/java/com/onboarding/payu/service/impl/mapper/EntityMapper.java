package com.onboarding.payu.service.impl.mapper;

/**
 * Interface for mapping between entities and models
 *
 * @param <T> model class
 * @param <P> entity class
 */
public interface EntityMapper<T, P>  {
	/**
	 * Convert entity to model
	 *
	 * @param entity instance
	 * @return model instance
	 */
	T toModel(final P entity);

	/**
	 * Convert model to entity
	 *
	 * @param model instance
	 * @return entity instance
	 */
	P toEntity(final T model);
}