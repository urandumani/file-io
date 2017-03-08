package com.ekipa.model;


import com.ekipa.constant.Permission;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Directory extends File {
	private Children children;

	public Children getChildren() {
		return children;
	}

	public void setChildren(Children children) {
		this.children = children;
	}

	public static Directory createDirectory(String id, Path path, BasicFileAttributes attributes, Children children) {
		Directory directory = new Directory();
		directory.setName(path.getFileName().toString());
		directory.setId(id);
		directory.setLastmodified(attributes.lastModifiedTime().toMillis());
		directory.setSize(attributes.size());
		if (path.getParent() != null) {
			directory.setParent(path.getParent().getFileName().toString());
		}
		directory.setPermission(Permission.getPermission(path));
		directory.setChildren(children);
		return directory;
	}
}
