package com.example.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

@RestController
public class TestController {
	
	private final String bucketName = "yomikoyomi";
	private final String filePath = "imgs/";
	private final String fileName = "test.png";
	private final String accessKey = "abc";
	private final String secretKey = "def";
	
	
	
	@RequestMapping("/hello")
	public Map<String, String> hello() {
		return Collections.singletonMap("content", "Hello, Spring Boot!!");
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(HttpServletResponse response, @RequestParam MultipartFile file) throws IOException{
		if( file.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
		AmazonS3 s3 = new AmazonS3Client(credentials);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		
		s3.putObject(new PutObjectRequest(bucketName, filePath + fileName, file.getInputStream(), objectMetadata));

		return;
	}
	

}
