package com.bike.rent.kelly

import com.bike.rent.kelly.model.tickets.Ticket
import org.junit.*

class TicketTests {
    val ticket = Ticket("1 Day Pass", "This ticket info", 5.00F, "02/12/19")

    @Test
    fun textCreate(){
        Assert.assertEquals("1 Day Pass", ticket.title)
        Assert.assertEquals("This ticket info", ticket.info)
        Assert.assertEquals(5.00F, ticket.price)
        Assert.assertEquals("02/12/19", ticket.expires)
    }

    @Test
    fun testEquals() {
        val dayTicket = Ticket("1 day pass", "more info", 5.00F, "01/19/19")
        val monthTicket = Ticket("1 month pass", "month info",17.00F, "01/01/20")
        Assert.assertEquals(dayTicket, dayTicket)
        Assert.assertEquals(monthTicket, monthTicket)
        Assert.assertNotEquals(dayTicket, monthTicket)
    }
}