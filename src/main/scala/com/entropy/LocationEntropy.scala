package com.entropy

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.count
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.functions.log2
import org.apache.spark.sql.DataFrame

class LocationEntropy {

}

object LocationEntropy {
	def main(args: Array[String]) {

		val spark = org.apache.spark.sql.SparkSession.builder
				.master("local")
				.appName("Entropy")
				.getOrCreate;

		val airportData = spark.read
				.format("csv")
				.option("header", "true") //reading the headers
				.option("mode", "DROPMALFORMED")
				.load("airport.csv")

				airportData.filter("Country is null")

		import spark.implicits._
		val total = airportData.count();
		val countCountry = airportData.groupBy("Country").count()
				val countryEntropy = countCountry
				.withColumn("p", $"count" / total)
				.groupBy($"Country").agg((-sum($"p" * log2($"p"))).alias("entropy")).orderBy("entropy")

				countryEntropy.show()
	}

}


