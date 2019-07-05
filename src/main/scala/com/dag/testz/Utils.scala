package com.dag.testz

object Utils {
  val monthly_payment_formula = (Pv: Double, R: Double, n: Double) => (Pv * (R / 12)) / (1 - Math.pow(1 + (R / 12), -n))

  def fixLoan (loan: Double) = Math.ceil(loan / 100.0) * 100.0
}
