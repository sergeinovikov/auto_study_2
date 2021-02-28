package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import examples.Calculator;
import org.testng.Assert;

public class CalculatorSteps {

    private int result;

    @Если("В калькуляторе сложить числа 3 и 5")
    public void calculateSum() {
        int first = 3;
        int second = 5;
        result = Calculator.sum(first, second);
    }

    @То("Сумма будет равна 8")
    public void assertResult() {
        Assert.assertEquals(8, result);
    }
}
