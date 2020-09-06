package com.pig4cloud.pig.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * 商城业务错误
 * @author hanguilin
 */
@NoArgsConstructor
public class HmallException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public HmallException(String message) {
		super(message);
	}

	public HmallException(Throwable cause) {
		super(cause);
	}

	public HmallException(String message, Throwable cause) {
		super(message, cause);
	}

	public HmallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
