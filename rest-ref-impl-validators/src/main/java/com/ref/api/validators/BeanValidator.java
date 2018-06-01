package com.ref.api.validators;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class BeanValidator implements org.springframework.validation.Validator, InitializingBean,
		ApplicationContextAware, ConstraintValidatorFactory {

	private Validator validator;

	private ApplicationContext applicationContext;

	private final AutowireCapableBeanFactory beanFactory;

	/**
	 * Create a new SpringConstraintValidatorFactory for the given BeanFactory.
	 * 
	 * @param beanFactory
	 *            the target BeanFactory
	 */
	public BeanValidator(AutowireCapableBeanFactory beanFactory) {
		Assert.notNull(beanFactory, "BeanFactory must not be null");
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().constraintValidatorFactory(this)
				.buildValidatorFactory();
		validator = validatorFactory.usingContext().getValidator();
	}

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {

		Map<String, T> beansByNames = applicationContext.getBeansOfType(key);
		if (beansByNames.isEmpty()) {
			try {
				return key.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'",
						e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Could not instantiate constraint validator class '" + key.getName() + "'",
						e);
			}
		}
		if (beansByNames.size() > 1) {
			throw new RuntimeException(
					"Only one bean of type '" + key.getName() + "' is allowed in the application context");
		}
		return beansByNames.values().iterator().next();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// return true;
		return (this.validator != null);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			String message = constraintViolation.getMessage();
			errors.rejectValue(propertyPath, "", message);
		}
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		this.beanFactory.destroyBean(instance);
	}

}
