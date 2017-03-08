package com.ekipa.model;

import java.util.Set;

public class Children
{
	private Set<String> files;
	private Set<String> directories;

	public Children(Set<String> files, Set<String> directories) {
		this.files = files;
		this.directories = directories;
	}

	public Set<String> getFiles()
	{
		return files;
	}

	public void setFiles(Set<String> files)
	{
		this.files = files;
	}

	public Set<String> getDirectories()
	{
		return directories;
	}

	public void setDirectories(Set<String> directories)
	{
		this.directories = directories;
	}
}
