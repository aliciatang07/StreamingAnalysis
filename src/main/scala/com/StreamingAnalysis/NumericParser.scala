package com.StreamingAnalysis

import java.util.StringTokenizer

import scala.collection.mutable.{ArrayBuilder, ListBuffer}

import org.apache.spark.SparkException

object NumericParser {

  /** Parses a string into a Double, an Array[Double], or a Seq[Any]. */
  def parseVectorPoint(s: String): Any = {
    val tokenizer = new StringTokenizer(s, "()[],", true)
    if (tokenizer.hasMoreTokens()) {
      val token = tokenizer.nextToken()
      if (token == "(") {
        parseTuple(tokenizer)
      } else if (token == "[") {
        parseArray(tokenizer)
      }


    } else {
      throw new SparkException(s"Cannot find any token from the input string.")
    }
  }

  private def parseArray(tokenizer: StringTokenizer): Array[Double] = {
    val values = ArrayBuilder.make[Double]
    var parsing = true
    var allowComma = false
    var token: String = null
    while (parsing && tokenizer.hasMoreTokens()) {
      token = tokenizer.nextToken()
      if (token == "]") {
        parsing = false
      } else if (token == ",") {
        if (allowComma) {
          allowComma = false
        } else {
          throw new SparkException("Found a ',' at a wrong position.")
        }
      } else {
        // expecting a number
        values += parseDouble(token)
        allowComma = true
      }
    }
    if (parsing) {
      throw new SparkException(s"An array must end with ']'.")
    }
    values.result()
  }

  private def parseTuple(tokenizer: StringTokenizer): Seq[_] = {
    val items = ListBuffer.empty[Any]
    var parsing = true
    var allowComma = false
    var token: String = null
    while (parsing && tokenizer.hasMoreTokens()) {
      token = tokenizer.nextToken()
      if (token == "(") {
        items.append(parseTuple(tokenizer))
        allowComma = true
      } else if (token == "[") {
        items.append(parseArray(tokenizer))
        allowComma = true
      } else if (token == ",") {
        if (allowComma) {
          allowComma = false
        } else {
          throw new SparkException("Found a ',' at a wrong position.")
        }
      } else if (token == ")") {
        parsing = false
      } else if (token.trim.isEmpty) {
        // ignore whitespaces between delim chars, e.g. ", ["
      } else {
        // expecting a number
        items.append(token)
        allowComma = true
      }
    }
    if (parsing) {
      throw new SparkException(s"A tuple must end with ')'.")
    }
    items
  }

  private def parseDouble(s: String): Double = {
    try {
      java.lang.Double.parseDouble(s)
    } catch {
      case e: NumberFormatException =>
        throw new SparkException(s"Cannot parse a double from: $s", e)
    }
  }
}