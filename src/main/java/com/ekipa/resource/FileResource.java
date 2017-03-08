package com.ekipa.resource;

import com.ekipa.domain.File;
import com.ekipa.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{id:.*}")
public class FileResource
{
	@Autowired
	private FileService fileService;

	@GetMapping
	public File getFileInfo(@PathVariable("id") final String id)
	{
		return null;
	}
}
