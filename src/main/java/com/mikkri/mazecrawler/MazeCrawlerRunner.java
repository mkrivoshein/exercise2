package com.mikkri.mazecrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Profile({"standalone", "production"}) // this is needed so that run() is not executed during tests
public class MazeCrawlerRunner implements CommandLineRunner {
    private final MazeCrawler mazeCrawler;

    public MazeCrawlerRunner(@Autowired MazeCrawler mazeCrawler) {
        this.mazeCrawler = mazeCrawler;
    }


    @Override
    public void run(String... args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage:\njava -jar maze-crawler.jar inputfile.txt");
            throw new IllegalArgumentException("Exactly one argument is expected");
        }

        Path inputPath = Paths.get(args[0]);

        // assuming that throwing an exception is ok if file is not present
        List<String> input = Files.readAllLines(inputPath);

        // execution of the maze crawler logic
        List<String> output = mazeCrawler.execute(input);

        // printing out results into the output
        output.forEach(System.out::println);
    }
}
