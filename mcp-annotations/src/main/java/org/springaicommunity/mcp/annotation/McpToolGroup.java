/*
 * Copyright 2025-2025 the original author or authors.
 */
package org.springaicommunity.mcp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface McpToolGroup {

	/**
	 * The name of the toolgroup. If not provided, the fully qualified ElementType.TYPE
	 * will be used.
	 */
	String name() default "";

	/**
	 * The title of the toolgroup. If not provided, will be empty.
	 */
	String title() default "";

	/**
	 * The description of the tool. If not provided, will be empty.
	 */
	String description() default "";

}
