package com.dag.testz

object Main {
  val MONTHS = 36

  def checkArguments(strings: Array[String]): Boolean = {
    // check that all required params are there
    if (strings == null || strings.length != 2) {
      return false
    }

    // and in the correct format
    try {
      val value = strings(1).toDouble
      if (value < 1000 || value > 15000) {
        System.err.println(f"loan $value%.0f is out of range [1000 ... 15000]")
        return false
      }
    }
    catch {
      case ex: NumberFormatException => return false
    }

    val fileName = strings(0)
    if (!new java.io.File(fileName).exists) {
      System.err.println(f"file $fileName doesn't exist")
      return false
    }

    return true
  }

  def processValue(fileName: String, userLoan: Double) = {
    LendersService.loadFromFile(fileName)

    val loan = Utils.fixLoan(userLoan)

    val result = LendersService.getPossibleLenders(loan)

    if (result.pendingLoan != 0) {
      System.err.println("loan is impossible")
    }
    else {
      val repayment = result.lenders.map(a => Utils.monthly_payment_formula(a._2, a._1.Rate, MONTHS)).sum
      val totalPayment = repayment * MONTHS
      val finalRate = 100.0 * result.lenders.map(a => a._1.Rate).sum / result.lenders.size // avg? not sure if this is the way how to calculate

      System.out.println(f"Requested amount: £$loan%.0f\nRate: $finalRate%.1f%%\nMonthly repayment: £$repayment%.2f\nTotal repayment: £$totalPayment%.2f\n")
    }
  }

  def main(args: Array[String]): Unit = {
    if (!checkArguments(args)) {
      System.err.println("Invalid arguments, requires [market file] [loan amount]")
      return
    }

    /*
    There is a need for a rate calculation system allowing prospective borrowers to obtain a quote
    from our pool of lenders for 36 month loans. This system will  take the form of a command-line application.

    You will be provided with a file containing a list of all the offers being made by the lenders within
    the system in CSV format, see the example market.csv file provided alongside this specification.

    You should strive to provide as low a rate to the borrower as is possible to ensure that Zopa's quotes are
    as competitive as they can be against our competitors'. You should also provide the borrower with the details
    of the monthly repayment amount and the total repayment amount.
    Repayment amounts should be displayed to 2 decimal places and the rate of the  loan should be displayed to one decimal place.

    Borrowers should be able to request a loan of any £100 increment between £1000 and £15000 inclusive. If the
    market does not have sufficient offers from lenders to satisfy the loan then the system should inform the
    borrower that it is not possible to provide a quote at that time.

    The application should take arguments in the form:
    cmd> [application] [market_file] [loan_amount]

    Example:

    cmd> quote.exe market.csv 1500

    The application should produce output in the form:
    cmd> [application] [market_file] [loan_amount]
    Requested amount: £XXXX
    Rate: X.X%
    Monthly repayment: £XXXX.XX
    Total repayment: £XXXX.XX
     */

    processValue(args(0), args(1).toDouble)

  }
}
