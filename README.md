# ![Karumi logo](https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png) KotlinSnapshot [![Build Status](https://travis-ci.org/Karumi/KotlinSnapshot.svg?branch=master)](https://travis-ci.org/Karumi/KotlinSnapshot) 

Snapshot Testing framework for Kotlin.

## What is this?

Snapshot testing is an assertion strategy based on the comparision of the instances serialized using a human readable format we version as part of the repository source code.

## Getting started

Add the dependency to your ```build.gradle``` file: 

``` gradle
  testImplementation 'com.karumi:kotlinsnapshot:0.0.1'
```

Invoke the extension function named ``matchWithSnapshot`` from any instance. The name of the snapshot is not mandatory, if you don't specify it as the first ``matchWithSnapshot`` param the library will try to infer it from the test execution context. Example:

``` kotlin
class NetworkTest {

    private val networkClient = MyNetworkClient()

    @Test
    fun shouldFetchDataFromNetwork() {
        val myData = networkClient.fetchData()
        myData.matchWithSnapshot()
    }

    @Test
    fun shouldFetchDataFromNetworkWithSpecificSnapshotName() {
        val myData = networkClient.fetchData()
        myData.matchWithSnapshot("should fetch the data from the network")
    }

```

If you need to customize the snapshots folder path you can create an instance of `KotlinSnapshot` in your test file and use the method `matchWithSnapshot`, which takes 2 arguments: A string with the name of the snapshot and a `Any` object to be shot using its `toString()` implementation.

``` kotlin
    val kotlinSnapshot = KotlinSnapshot(relativePath = "src/test/kotlin/com/my/package")
``` 

After you run the test for the first time, a new  snapshot will be written in the `__snapshot__` directory of the root of your project. The written snapshot for this example would look like this:

```bash
$ cat __snapshot__/should\ fetch\ data\ from\ network.snap 
{"name":"gabriel","id":5}
```

On subsequent runs, the value will be compared with the snapshot stored in the filesystem if the are not equal, your test will fail. To see the detailed error you may need to run your tests with `./gradlew test --info`. You should see something like this:

![Snapshot Error](https://user-images.githubusercontent.com/5729175/37878769-98ef26ae-3033-11e8-8066-ea1e49630de3.png)

## Updating Snapshots

In order to update and purge snapshots from the command line, you should add a new system property called "updateSnapshots" to your `build.gradle` file:

``` gradle

test {
   // update snapshots via command line
   systemProperty "updateSnapshots", System.getProperty("u")
}
```

On Android projects you might need to do this instead: 

```build.gradle
    testOptions {
        unitTests.all {
            // update snapshots via command line
            systemProperty "updateSnapshots", System.getProperty("u")
        }
    }
```

Then, when you want to update the snapshot for a specific test: 
``` bash
./gradlew test -Du=1 --info --tests *MyTest.someTestINeedToUpdate
```

Please note that in this example I used "u" but, it can be any flag, depending on how you configure your gradle file.

On android projects the `test` task might not support the `--tests` flag to filter tests, you might need to run something like `testDebug` instead. It is not necessary to filter tests when updating, but you should do it to avoid inconsistencies.

## Purging Snapshots

As you rename snapshots, old unused snapshots may remain in your project. You can delete all existing snapshots and rebuild the ones that are actually used using the "purgeSnapshots" system property. The setup is identical to the one used for updating snapshots.

``` gradle

test {
   // purge snapshots via command line
   systemProperty "purgeSnapshots", System.getProperty("p")
}
```

Again android projects may need to handle this differently:

```build.gradle
    testOptions {
        unitTests.all {
            // update snapshots via command line
            systemProperty "purgeSnapshots", System.getProperty("p")
        }
    }
```

Then, when you want to delete the snapshots: 
``` bash
./gradlew test -Dp=1 --info
```

## Linting and formatting

This repository uses [ktlint](https://github.com/shyiko/ktlint). This Gradle plugin ensures the code style is homogeneous and always correct thanks to the evaluation of the code during the build execution. You can use these commands in order to check if the code changes passes the repository codestyle and to format the code automatically:

```
./gradlew ktlint //Checks if the project passes the checkstyle.
./gradlew ktlintFormat //Formats the code for you
```

## Executing tests

This project contains some tests written using JUnit. You can easily run the tests by executing the following commands:

```
./gradlew test //Run every test.
./gradlew test -t //Run every test using the watch mode.
./gradlew test --tests "com.xyz.b.module.TestClass.testToRun" //Run a single test
```

## Contributing

If you would like to contribute code to this repository you can do so through GitHub by creating a new branch in the repository and sending a pull request or opening an issue. Please, remember that there are some requirements you have to pass before accepting your contribution:

* Write clean code and test it.
* The code written will have to match the product owner requirements.
* Follow the repository code style.
* Write good commit messages.
* Do not send pull requests without checking if the project build is OK in the CI environment.
* Review if your changes affects the repository documentation and update it.
* Describe the PR content and don't hesitate to add comments to explain us why you've added or changed something.

License
-------

    Copyright 2018 Karumi

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.