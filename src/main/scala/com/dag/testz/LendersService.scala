package com.dag.testz

object LendersService {

  var ListLenders: List[Lender] = null

  def loadFromFile(fileName: String): Unit = {
    // load all file
    val tmpLines = scala.io.Source.fromFile(fileName).getLines().toList

    // first line contains header, check and ignore
    if (tmpLines
      .take(1)
      .map(a => a.split(","))
      .filter(a => a(0).trim.equalsIgnoreCase("Lender") && a(1).trim.equalsIgnoreCase("Rate") && a(2).trim.equalsIgnoreCase("Available"))
      .isEmpty) {
      throw new Exception(f"file $fileName has invalid format")
    }

    ListLenders = tmpLines
      .drop(1)
      .map(a => {
        val line = a.split(",")
        Lender(line(0).trim, line(1).trim.toDouble, line(2).trim.toDouble)
      })
      .toList
  }

  def getPossibleLenders(loadAmount: Double) = ListLenders
    .sortBy(a => a.Rate)
    .foldLeft(Accumulator(loadAmount))((accumulator, lender) => if (accumulator.pendingLoan > 0) Accumulator(Math.max(0, accumulator.pendingLoan - lender.Available), (lender, Math.min(accumulator.pendingLoan, lender.Available)) :: accumulator.lenders ) else accumulator)

}
