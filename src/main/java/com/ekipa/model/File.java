package com.ekipa.model;

import com.ekipa.constant.Permission;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class File {
	private String id;
	private String name;
	private long size;
	private long lastmodified;
	private String parent;
	private Permission permission;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(long lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public static File createFile(String id, Path path, BasicFileAttributes attributes) {
		File file = new File();
		file.setName(path.getFileName().toString());
		file.setId(id);
		file.setLastmodified(attributes.lastModifiedTime().toMillis());
		file.setSize(attributes.size());
		if (path.getParent() != null) {
			file.setParent(path.getParent().getFileName().toString());
		}
		file.setPermission(Permission.getPermission(path));
		return file;
	}
}
