package com.ekipa.domain;

import com.ekipa.constant.Permission;

public class File
{
	private String id;
	private String name;
	private long size;
	private long lastmodified;
	private String parent;
	private Permission permission;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public long getLastmodified()
	{
		return lastmodified;
	}

	public void setLastmodified(long lastmodified)
	{
		this.lastmodified = lastmodified;
	}

	public String getParent()
	{
		return parent;
	}

	public void setParent(String parent)
	{
		this.parent = parent;
	}

	public Permission getPermission()
	{
		return permission;
	}

	public void setPermission(Permission permission)
	{
		this.permission = permission;
	}
}
