package org.springaicommunity.mcp.provider.tool;

import java.lang.reflect.Method;
import java.util.List;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;
import io.modelcontextprotocol.spec.McpSchema.ToolGroupName;
import io.modelcontextprotocol.util.Assert;
import io.modelcontextprotocol.util.Utils;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolGroup;

public abstract class AbstractMcpToolProvider {

	protected final List<Object> toolObjects;

	protected McpJsonMapper jsonMapper = McpJsonMapper.createDefault();

	public AbstractMcpToolProvider(List<Object> toolObjects) {
		Assert.notNull(toolObjects, "toolObjects cannot be null");
		this.toolObjects = toolObjects;
	}

	protected Method[] doGetClassMethods(Object bean) {
		return bean.getClass().getDeclaredMethods();
	}

	protected McpTool doGetMcpToolAnnotation(Method method) {
		return method.getAnnotation(McpTool.class);
	}

	protected McpToolGroup doGetMcpToolGroupAnnotation(Class<?> clazz) {
		return clazz.getAnnotation(McpToolGroup.class);
	}

	protected ToolGroupName doGetToolGroupName(McpToolGroup annotation, Class<?> clazz) {
		// annotation name has highest priority
		String name = annotation.name();
		if (!Utils.hasText(name)) {
			// If none is specified in the annotation, use fully qualified type name
			name = clazz.getName();
		}
		return ToolGroupName.parseName(name);
	}

	protected ToolGroup doGetToolGroup(Class<?> toolGroupClass) {
		McpToolGroup toolGroupAnnotation = doGetMcpToolGroupAnnotation(toolGroupClass);
		if (toolGroupAnnotation != null) {
			return new ToolGroup(doGetToolGroupName(toolGroupAnnotation, toolGroupClass), toolGroupAnnotation.title(),
					toolGroupAnnotation.description());
		}
		else {
			return null;
		}
	}

	protected Class<? extends Throwable> doGetToolCallException() {
		return Exception.class;
	}

	public void setJsonMapper(McpJsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public McpJsonMapper getJsonMapper() {
		return this.jsonMapper;
	}

}
