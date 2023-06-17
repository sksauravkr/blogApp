package com.blog.api.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//MultipartFile me file ka data hai jisko nikal k path me rkhna hai
		//file lene hai
		String name = file.getOriginalFilename();
		
		//random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//path banayenge fie tak ka like full path
		String fullPath = path+ File.separator +name;
		
		//create folder for image if not created
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
		//file ko copy karenge multipart se full path me ya upload karenge
		Files.copy(file.getInputStream(), Paths.get(fullPath));
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
