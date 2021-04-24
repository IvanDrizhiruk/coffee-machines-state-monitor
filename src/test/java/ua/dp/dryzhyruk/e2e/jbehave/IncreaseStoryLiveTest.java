package ua.dp.dryzhyruk.e2e.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.StoryReporterBuilder.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.CoffeeMachinesStateMonitorApplication;
import ua.dp.dryzhyruk.e2e.jbehave.steps.EventRegistrarSteps;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = CoffeeMachinesStateMonitorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncreaseStoryLiveTest extends JUnitStories {

    private CrossReference xref = new CrossReference();

//    public IncreaseStoryLiveTest() {
//        super();
//        this.configuredEmbedder().candidateSteps().add(new IncreaseSteps());
//    }

    @Override
    public Configuration configuration() {
//        return new MostUsefulConfiguration()
//                .useStoryLoader(new LoadFromClasspath(this.getClass()))
//                .useStoryReporterBuilder(new StoryReporterBuilder()
//                        .withCodeLocation(codeLocationFromClass(this.getClass()))
//                        .withFormats(CONSOLE));

        StoryReporterBuilder storyReporterBuilder = new StoryReporterBuilder()
                .withFormats(Format.CONSOLE, Format.HTML, Format.XML)
                .withCrossReference(xref);

        return new MostUsefulConfiguration()             // CONSOLE and HTML reporting
                .useStoryReporterBuilder(storyReporterBuilder);
        //.withDefaultFormats().
		/*.withCrossReference(xref))
        .useStepMonitor(xref.getStepMonitor());*/
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new EventRegistrarSteps());
    }

    @Override
    protected List<String> storyPaths() {
//        String searchInDirectory = codeLocationFromPath("../trader/src/main/java").getFile();
//        return new StoryFinder().findPaths(searchInDirectory, Collections.singletonList("**/*.story"), null);
        return Arrays.asList("stories/fullCycle.story");
    }
}