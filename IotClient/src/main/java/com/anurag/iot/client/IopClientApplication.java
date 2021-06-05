package com.anurag.iot.client;

import com.anurag.iot.client.data.InputData;
import com.anurag.iot.client.data.IotData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class IopClientApplication{

	public static void main(String[] args) {
		SpringApplication.run(IopClientApplication.class, args);
	}
}
