package com.dag.tests

import com.dag.testz.Utils
import org.scalatest.FunSuite

class UtilsTests extends FunSuite {

  // check we are able to load a valid file
  test("round load to upper 100 fraction") {
    assert( Utils.fixLoan (1000) == 1000)
    assert( Utils.fixLoan (2000) == 2000)
    assert( Utils.fixLoan (3400) == 3400)
    assert( Utils.fixLoan (5450) == 5500)

    assert( Utils.fixLoan (5001) == 5100)
    assert( Utils.fixLoan (5099) == 5100)
  }
}
