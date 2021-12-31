package com.oscarvj.pruebanach.data.model

data class ResponseModel (
	val description : String,
	val id : String,
	val name : String,
	val results : List<ResultsModel>,
	val total_pages : Int,
	val total_results : Int
)