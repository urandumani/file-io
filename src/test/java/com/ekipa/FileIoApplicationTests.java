package com.ekipa;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileIoApplicationTests {

	@Test
	public void contextLoads() throws Exception
	{
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.build();

		HttpPost post = new HttpPost("http://localhost:8090/id/content");
		post.setEntity(new ByteArrayEntity(Files.readAllBytes(Paths.get(".gitignore"))));
		post.setHeader("Content-Type", "application/octet-stream");
		CloseableHttpResponse response = httpClient.execute(post);
		String a = EntityUtils.toString(response.getEntity());
	}

	@Test
	public void test() throws Exception {
		Path path = Paths.get("abc/bca/tra/mma/test.txt");
		Files.createDirectories(path.getParent());
		FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE);
		FileLock lock = channel.tryLock();
		channel.write(ByteBuffer.wrap(Files.readAllBytes(Paths.get(".gitignore"))));
		lock.release();
	}

	@Test
	public void a() throws Exception {
		Path path = Paths.get("abc");
		Files.createDirectories(path);
	}
}
