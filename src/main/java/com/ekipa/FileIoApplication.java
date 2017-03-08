package com.ekipa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileIoApplication
{

	public static void main(String[] args) {
		// add the path variable to configure tomcat to allow encoded slashes
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(FileIoApplication.class, args);
	}
}
