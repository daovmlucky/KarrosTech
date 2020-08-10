package com.example.demo.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class AmazonClient {

    private AmazonS3 s3client;
    
    private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

    @Value("${endpointUrl}")
    private String endpointUrl;
    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${region}")
    private String region;

    @SuppressWarnings("deprecation")
	@PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(this.region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file, multipartFile.getSize());
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
    
    public ByteArrayOutputStream downloadFile(String keyName) {
    	try {
	    	S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
	    	InputStream is = s3object.getObjectContent();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        int len;
	        byte[] buffer = new byte[4096];
	        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
	            baos.write(buffer, 0, len);
	        }
	        
	        return baos;
    	}catch (IOException ioe) {
    		logger.error("IOException: " + ioe.getMessage());
        }catch (AmazonServiceException ase) {
        	logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
	        logger.info("Error Message:    " + ase.getMessage());
	        logger.info("HTTP Status Code: " + ase.getStatusCode());
	        logger.info("AWS Error Code:   " + ase.getErrorCode());
	        logger.info("Error Type:       " + ase.getErrorType());
	        logger.info("Request ID:       " + ase.getRequestId());
	        throw ase;
        }catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
    
    	return null;
    }

    private void uploadFileTos3bucket(String fileName, File file, long size) {
    	try {
	    	ObjectMetadata metadata = new ObjectMetadata();
	        metadata.setContentLength(size);
	    	final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
	    	s3client.putObject(putObjectRequest);
    	} catch (AmazonServiceException ase) {
        	  logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
              logger.info("Error Message:    " + ase.getMessage());
              logger.info("HTTP Status Code: " + ase.getStatusCode());
              logger.info("AWS Error Code:   " + ase.getErrorCode());
              logger.info("Error Type:       " + ase.getErrorType());
              logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
              logger.info("Caught an AmazonClientException: ");
              logger.info("Error Message: " + ace.getMessage());
        }
    }
    
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }


}
