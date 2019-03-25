import org.testng.TestNG;
import org.testng.collections.Lists;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestNG testSuite = new TestNG();
        testSuite.setOutputDirectory("./test_results");
        List<String > suites = Lists.newArrayList();
        suites.add("./test_suite/test.xml");
        testSuite.setTestSuites(suites);
        testSuite.run();
    }

}