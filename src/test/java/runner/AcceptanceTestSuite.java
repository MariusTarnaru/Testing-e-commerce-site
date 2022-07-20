package runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/JSONReports/cucumberreports.json", "html:target/HTMLReports/HTMLReports.html"},
        features = {"src/test/resources/features/test.feature"
        },
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = {"steps"},

        monochrome = true,
        tags = "@smoke",
        dryRun = false
)
public class AcceptanceTestSuite {
}
