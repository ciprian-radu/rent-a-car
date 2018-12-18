# How to Contribute
When you have something to contribute, submit a *Pull Request*. Your code will be reviewed. After it passes **review**, it will be accepted.
The following sections describe the criteria that are applied.

## Code Style
We use the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## Code Quality
Make sure you check the code quality using [SonarLint](https://www.sonarlint.org/).

## Unit Tests
You **must** write unit tests for the code you write. We use *Code Coverage* to understand how well our unit tests cover our code.
Note that for method names for unit tests we adopt the following pattern: *test_methodUnderTest_stateUnderTest_expectedBehavior*

Typically we test a public method but, *methodUnderTest* may also stand for example for a Constructor. In general, *methodUnderTest* is a relevant name for the tested unit.

Then, we must a consider the state under test, therefore the given conditions under which the unit tests runs. Typically this represents the input parameters of the tested method. You may omit this if the state under test is implicit.

Finally, we must mention the expected behavior for the unit test. For example, this is a return value or a thrown *Exception* that is expected as an output of the unit test.

## Commit Messages
Please follow the following seven rules for your commit messages:

1. Separate subject from body with a blank line;
1. Limit the subject line to 50 characters;
1. Capitalize the subject line;
1. Do not end the subject line with a period;
1. Use the imperative mood in the subject line;
1. Wrap the body at 72 characters;
1. Use the body to explain what and why vs. how.

We **must** care about our code history.
