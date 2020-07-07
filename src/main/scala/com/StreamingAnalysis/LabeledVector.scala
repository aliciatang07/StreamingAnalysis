package com.StreamingAnalysis


  import org.apache.spark.mllib.linalg.{Vector, Vectors}

  import org.apache.spark.SparkException


  /**
   * Class that represents the features and labels of a data point.
   *
   * @param label    Label for this data point.
   * @param features List of features fotringr this data point.
   */

  case class LabeledVector ( label: String, features: Vector) {

    def getLabel: String = label

    def getFeatures: Vector = features

    override def toString: String = {
      s"($label,$features)"
    }


  }

  /**
   * Parser for [[org.apache.spark.mllib.regression.LabeledPoint]].
   *
   */

  object LabeledVector {
    /**
     * Parses a string resulted from `LabeledPoint#toString` into
     * an [[org.apache.spark.mllib.regression.LabeledPoint]].
     *
     */

    def main(args: Array[String]): Unit = {
        val test = "( sfjiosdnfsa, [2.2,3.3, 4.4])"
        val v = parse(test)

    }

    def parseNumeric(any: Any): Vector = {
      any match {
        case values: Array[Double] =>
          Vectors.dense(values)
        case Seq(size: Double, indices: Array[Double], values: Array[Double]) =>
          Vectors.sparse(size.toInt, indices.map(_.toInt), values)
        case other =>
          throw new SparkException(s"Cannot parse $other.")
      }
    }

    def parse(s: String): LabeledVector = {
      if (s.startsWith("(")) {
        NumericParser.parseVectorPoint(s) match {
          case Seq(label: String, numeric: Any) =>
            LabeledVector(label, parseNumeric(numeric))
          case other =>
            throw new SparkException(s"Cannot parse $other")
        }
      } else { // dense format used before v1.0
        val parts = s.split(',')
        val label = parts(0)
        val features = Vectors.dense(parts(1).trim().split(' ').map(java.lang.Double.parseDouble))
        LabeledVector(label, features)
      }
    }


  }

