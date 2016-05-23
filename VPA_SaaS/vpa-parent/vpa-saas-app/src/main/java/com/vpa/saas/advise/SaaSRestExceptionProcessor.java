package com.vpa.saas.advise;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vpa.core.exceptions.DuplicateRecordException;
import com.vpa.core.exceptions.ResourceAlreadyExistException;
import com.vpa.core.exceptions.ResourceNotFoundException;
import com.vpa.core.exceptions.ResourcePreconditionException;
import com.vpa.core.exceptions.UnauthorizedResourceException;
import com.vpa.core.exceptions.UnprocessableResourceException;
import com.vpa.core.exceptions.VPASaaSSystemException;
import com.vpa.core.services.EmailSender;
import com.vpa.saas.errors.VPASaaSError;

@ControllerAdvice
@PropertySources({
		@PropertySource("classpath:vpa_saas_email_${vpa.saas.profile:dev}.properties"),
		@PropertySource("classpath:message.properties") })
public class SaaSRestExceptionProcessor {
	@Autowired
	EmailSender emailSender;
	@Autowired
	private Environment env;

	private static final String VPA_ERROR_PREFIX = "*********** VPA Error Error in SaaS request ";
	private static final Logger logger = Logger
			.getLogger(SaaSRestExceptionProcessor.class);

	@ExceptionHandler(VPASaaSSystemException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public VPASaaSError vpaSaaSSystemExceptionError(VPASaaSSystemException e) {

		VPASaaSError error = new VPASaaSError(500, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public VPASaaSError resourceAlreadyExistExceptionError(
			ResourceAlreadyExistException e) {

		VPASaaSError error = reportError(e);
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public VPASaaSError resourceNotFoundExceptionError(
			ResourceNotFoundException e) {

		VPASaaSError error = new VPASaaSError(404, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}

	@ExceptionHandler(UnauthorizedResourceException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public VPASaaSError unauthorizedResourceExceptionError(
			UnauthorizedResourceException e) {

		VPASaaSError error = new VPASaaSError(403, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}

	@ExceptionHandler(UnprocessableResourceException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public VPASaaSError unprocessableRequestExceptionError(
			UnprocessableResourceException e) {

		VPASaaSError error = new VPASaaSError(422, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		emailSender.sendErrorMail("Error Log", e.getMessage());

		return error;
	}

	@ExceptionHandler(ResourcePreconditionException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ResponseBody
	public VPASaaSError resourcePreconditionExceptionError(
			ResourcePreconditionException e) {

		VPASaaSError error = new VPASaaSError(412, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}

	private VPASaaSError reportError(NestedRuntimeException e) {
		VPASaaSError error = new VPASaaSError(409, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}
	
	@ExceptionHandler(DuplicateRecordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public VPASaaSError duplicateRecord(
			DuplicateRecordException e) {

		VPASaaSError error = new VPASaaSError(400, e.getMessage());
		logger.error(VPA_ERROR_PREFIX + e.getMessage(),
				e.getMostSpecificCause());
		return error;
	}
}
