package com.ekipa.resource;

import com.ekipa.exception.MessageModel;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/{id:.*}/content")
public class FileContentResource
{
	@GetMapping
	public ResponseEntity<InputStream> getFileContent(@PathVariable("id") String id)
	{
		//return content type binary/octet-stream if content is available
		//return application/json if content not available
		//return application/json if file could not be found
		return null;
	}

	@PostMapping
	public MessageModel createDirectory(@PathVariable("id") String id)
	{
		return null;
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public MessageModel createFile(@PathVariable("id") String id, @RequestParam("file") MultipartFile file)
	{
		return null;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public MessageModel createFile(@PathVariable("id") String id, @RequestBody byte[] entity)
	{
		return null;
	}

	@PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public MessageModel updateFile(@PathVariable("id") String id, @RequestParam("file") MultipartFile file)
	{
		return null;
	}

	@PutMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public MessageModel updateFile(@PathVariable("id") String id, RequestEntity<InputStream> entity)
	{
		return null;
	}
}
