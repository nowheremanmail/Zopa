package com.dag.tests

import com.dag.testz.LendersService
import org.scalatest.FunSuite

class LendersServiceTests extends FunSuite {

  // check we are able to load a valid file
  test("test load valid file") {
    LendersService.loadFromFile("./src/test/resources/validfile.csv")

    assert(LendersService.ListLenders.size == 7)
    // some extra checks, to see that we are loading correct data (my local settings are in spanish ... file is in english)
    assert(LendersService.ListLenders.map(a => a.Available).sum == 2330)
    assert(LendersService.ListLenders.map(a => a.Rate).sum == 0.545)
  }

  test("test possible load 100") {
    LendersService.loadFromFile("./src/test/resources/validfile.csv")

    val result = LendersService.getPossibleLenders(100)

    assert(result.lenders.size == 1)
    assert(result.lenders.filter(a => a._2 <= a._1.Available).size == 1)
    assert(result.lenders.map(a => a._2).sum == 100)
    assert(result.pendingLoan == 0)
  }

  test("test possible load 1000") {
    LendersService.loadFromFile("./src/test/resources/validfile.csv")

    val result = LendersService.getPossibleLenders(1000)

    assert(result.lenders.size == 2)
    assert(result.lenders.filter(a => a._2 <= a._1.Available).size == 2)
    assert(result.lenders.map(a => a._2).sum == 1000)
    assert(result.pendingLoan == 0)
  }

  test("test possible load 2330") {
    LendersService.loadFromFile("./src/test/resources/validfile.csv")

    val result = LendersService.getPossibleLenders(2330)

    assert(result.lenders.size == 7)
    assert(result.lenders.filter(a => a._2 <= a._1.Available).size == 7)
    assert(result.lenders.map(a => a._2).sum == 2330)
    assert(result.pendingLoan == 0)
  }


  test("test impossible load 5000") {
    LendersService.loadFromFile("./src/test/resources/validfile.csv")

    val result = LendersService.getPossibleLenders(5000)

    assert(result.lenders.size == 7)
    assert(result.pendingLoan > 0)
  }

  test("test invalid file no headers") {
    intercept[Exception] {
      LendersService.loadFromFile("./src/test/resources/invalid1.csv")
    }
  }

  test("test invalid file with invalid values") {
    intercept[Exception] {
      LendersService.loadFromFile("./src/test/resources/invalid2.csv")
    }
  }

}
