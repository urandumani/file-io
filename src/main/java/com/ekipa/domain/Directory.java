package com.ekipa.domain;


public class Directory extends File
{
	private Children children;

	public Children getChildren()
	{
		return children;
	}

	public void setChildren(Children children)
	{
		this.children = children;
	}
}
