package org.springaicommunity.mcp.provider.tool;

import java.lang.reflect.Method;
import java.util.List;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;
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


	protected ToolGroup doGetToolGroup(McpToolGroup annotation, Class<?> clazz) {
		// annotation name has highest priority
		String name = annotation.name();
		if (!Utils.hasText(name)) {
			// If none is specified in the annotation, use fully qualified type name
			name = clazz.getName();
		}
		String[] segments = name.split("\\.");
		ToolGroup parent = null;
		for (int i = 0; i < segments.length; i++) {
			if (i == (segments.length - 1)) {
				String description = annotation.description();
				String title = annotation.title();
				parent = new ToolGroup(segments[i], parent, Utils.hasText(description)?description:null, Utils.hasText(title)?title:null, null);
			}
			else {
				parent = new ToolGroup(segments[i], null, null, null, null);
			}
		}
		return parent;
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
