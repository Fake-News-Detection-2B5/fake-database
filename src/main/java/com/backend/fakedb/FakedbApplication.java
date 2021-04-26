package com.backend.fakedb;

import com.backend.fakedb.utilities.IngestionLinker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;

@SpringBootApplication
public class FakedbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakedbApplication.class, args);
	}

}
