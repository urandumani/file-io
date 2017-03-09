package com.ekipa.constant;

import java.nio.file.Files;
import java.nio.file.Path;

public enum Permission
{
	READ_ONLY,
	WRITE_ONLY,
	READ_WRITE;

	public static Permission getPermission(Path path)
	{
		if (Files.isWritable(path) && Files.isReadable(path))
			return READ_WRITE;
		else if (Files.isWritable(path))
			return WRITE_ONLY;
		else
			return READ_ONLY;
	}
}
