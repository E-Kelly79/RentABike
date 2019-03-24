package com.bike.rent.kelly.model.tickets

import org.joda.time.DateTime

class Ticket (var title: String? = null,
              var info: String? = null,
              var price: Float = 0.0f,
              var expires: String? = null)