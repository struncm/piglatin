package cz.mst.app.piglatin;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import cz.mst.app.piglatin.lex.Lexan;
import cz.mst.app.piglatin.util.Transformer;

@SpringBootApplication
public class PiglatinApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PiglatinApplication.class);
	
	@Value("${piglatin.output.file}") private String outputFileName;
	
	@Autowired private Lexan lexan;
	
	public static void main(String[] args) {
		SpringApplication.run(PiglatinApplication.class, args);
	}
	
	@Bean CommandLineRunner run(final ApplicationContext ctx) {
		return args -> {
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFileName)))) {
				// get token stream
				lexan.stream()
					// debug print of tokens
					.peek(t -> LOG.info(t.toString()))
					// map token to string (with appropriate transformation)
					.map(Transformer::transform)
					// print result string to output file
					.forEach(pw::print);
			}
		};
	}

}
