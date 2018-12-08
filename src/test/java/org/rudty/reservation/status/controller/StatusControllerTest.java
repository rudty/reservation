package org.rudty.reservation.status.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rudty.reservation.ReservationApplication;
import org.rudty.reservation.status.model.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReservationApplication.class)
@AutoConfigureMockMvc
public class StatusControllerTest {

    @Autowired
    StatusController statusController;

    @Autowired
    MockMvc mvc;

    LocalDateTime newLocalDateTime(String time){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        return LocalDateTime.parse(time, formatter);
    }

    @Test
    public void ok() {
        List<ReservationStatus> r = statusController.status(newLocalDateTime("2018-12-04 23:00:00"), newLocalDateTime("2018-12-08 00:00:00"));
        for(ReservationStatus e : r) {
            System.out.println(e);
        }
    }

    @Test
    public void ok2() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-08 00:00:00", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
    }


    @Test
    public void 날짜형식이_맞지_않음() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("eclair", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),
                "{\"result\":\"ERROR\", \"reason\":\"date convert fail\"}");
    }


    @Test
    public void 날짜형식이_맞지_않음2() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("Froyo", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),
                "{\"result\":\"ERROR\", \"reason\":\"date convert fail\"}");
    }

    @Test
    public void 날짜형식이_맞지_않음_둘다() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("gingerbread", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("honeycomb", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),
                "{\"result\":\"ERROR\", \"reason\":\"date convert fail\"}");
    }

    @Test
    public void 같은_날짜는_볼_수_없어요() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),
                "{\"result\":\"ERROR\", \"reason\":\"check beginTime == endTime\"}");
    }

    @Test
    public void end_보다_begin_이_더_큼() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-08 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/status")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),
                "{\"result\":\"ERROR\", \"reason\":\"check beginTime < endTime\"}");
    }
}
