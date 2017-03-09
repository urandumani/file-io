package com.ekipa.model;


import com.ekipa.constant.Permission;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryModel extends FileModel {

	private ChildrenModel childrenModel;

	public ChildrenModel getChildrenModel() {
		return childrenModel;
	}

	public void setChildrenModel(ChildrenModel childrenModel) {
		this.childrenModel = childrenModel;
	}

	public static DirectoryModel createDirectory(String id, Path path, BasicFileAttributes attributes, ChildrenModel childrenModel) {
		DirectoryModel directoryModel = new DirectoryModel();
		directoryModel.setName(path.getFileName().toString());
		directoryModel.setId(id);
		directoryModel.setLastmodified(attributes.lastModifiedTime().toMillis());
		directoryModel.setSize(attributes.size());
		if (path.getParent() != null) {
			directoryModel.setParent(path.getParent().getFileName().toString());
		}
		directoryModel.setPermission(Permission.getPermission(path));
		directoryModel.setChildrenModel(childrenModel);
		return directoryModel;
	}
}
