package com.dag.tests

import com.dag.testz.{Main, Utils}
import org.scalatest.FunSuite

class MainTests extends FunSuite {

  // check we are able to load a valid file
  test("no arguments") {
    assert(!Main.checkArguments(null))
  }

  test("empty arguments") {
    assert(!Main.checkArguments(Array[String]()))
  }

  test("required arguments") {
    assert(Main.checkArguments(Array[String]("./src/test/resources/validfile.csv", "1.0")))
  }

  test("unexisting file argument") {
    assert(!Main.checkArguments(Array[String]("./src/test/resources/notfound.csv", "1.0")))
  }

  test("invalid loan value") {
    assert(!Main.checkArguments(Array[String]("./src/test/resources/validfile.csv", "value")))
  }

  test("invalid loan value < 1000") {
    assert(!Main.checkArguments(Array[String]("./src/test/resources/validfile.csv", "100")))
  }

  test("invalid loan value > 15000") {
    assert(!Main.checkArguments(Array[String]("./src/test/resources/validfile.csv", "16000")))
  }
}
