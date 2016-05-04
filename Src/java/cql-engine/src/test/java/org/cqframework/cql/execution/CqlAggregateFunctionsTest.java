package org.cqframework.cql.execution;

import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@Test(groups = {"a"})
public class CqlAggregateFunctionsTest extends CqlExecutionTestBase {

    /**
     * {@link org.cqframework.cql.elm.execution.AllTrue#evaluate(Context)}
     */
    @Test
    public void testAllTrue() throws JAXBException {
        Context context = new Context(library);
        Object result = context.resolveExpressionRef(library, "AllTrueAllTrue").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AllTrueTrueFirst").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AllTrueFalseFirst").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AllTrueAllTrueFalseTrue").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AllTrueAllFalseTrueFalse").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AllTrueNullFirst").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AllTrueEmptyList").getExpression().evaluate(context);
        assertThat(result, is(nullValue()));
    }

    /**
     * {@link org.cqframework.cql.elm.execution.AnyTrue#evaluate(Context)}
     */
    @Test
    public void testAnyTrue() throws JAXBException {
        Context context = new Context(library);
        Object result = context.resolveExpressionRef(library, "AnyTrueAllTrue").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueAllFalse").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AnyTrueAllTrueFalseTrue").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueAllFalseTrueFalse").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueTrueFirst").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueFalseFirst").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueNullFirstThenTrue").getExpression().evaluate(context);
        assertThat(result, is(true));

        result = context.resolveExpressionRef(library, "AnyTrueNullFirstThenFalse").getExpression().evaluate(context);
        assertThat(result, is(false));

        result = context.resolveExpressionRef(library, "AnyTrueEmptyList").getExpression().evaluate(context);
        assertThat(result, is(nullValue()));
    }

    /**
     * {@link org.cqframework.cql.elm.execution.Avg#evaluate(Context)}
     */
    @Test
    public void testAvg() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Count#evaluate(Context)}
     */
    @Test
    public void testCount() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Max#evaluate(Context)}
     */
    @Test
    public void testMax() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Median#evaluate(Context)}
     */
    @Test
    public void testMedian() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Min#evaluate(Context)}
     */
    @Test
    public void testMin() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Mode#evaluate(Context)}
     */
    @Test
    public void testMode() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.StdDev#evaluate(Context)}
     */
    @Test
    public void testPopulationStdDev() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.PopulationVariance#evaluate(Context)}
     */
    @Test
    public void testPopulationVariance() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.StdDev#evaluate(Context)}
     */
    @Test
    public void testStdDev() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Sum#evaluate(Context)}
     */
    @Test
    public void testSum() throws JAXBException {

    }

    /**
     * {@link org.cqframework.cql.elm.execution.Variance#evaluate(Context)}
     */
    @Test
    public void testVariance() throws JAXBException {

    }
}
