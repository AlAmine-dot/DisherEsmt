//package com.example.esmt.cours.disher.core.util
//
//import org.apache.commons.math3.distribution.NormalDistribution
//import java.io.InputStreamReader
//import kotlin.random.Random
//
//object Ratings {
//    private val distribution = NormalDistribution(3.5, 0.5) // moyenne 3.5, variance 0.5
//    private var value: Double? = null // variable pour stocker la valeur générée
//
//    fun get(): Double {
//        if (value == null) {
//            // si la valeur n'a pas encore été générée, on en génère une nouvelle
//            value = distribution.sample()
//        }
//        return value!!
//    }
//
//    fun reset() {
//        // méthode pour réinitialiser la valeur générée
//        value = null
//    }
//}
