package com.sample;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


/**
 * The Cucumber runner.
 * We need just one runner
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", publish = true, monochrome = true)
public class CucumberRunner {


}
