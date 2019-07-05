package com.dag.testz

case class Accumulator (pendingLoan: Double, lenders: List[(Lender, Double)] = List())