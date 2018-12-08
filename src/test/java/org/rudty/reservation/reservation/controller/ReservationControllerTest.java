package org.rudty.reservation.reservation.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rudty.reservation.ReservationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = ReservationApplication.class)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Autowired
    MockMvc mvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void 예약다음날00시까지_가능() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 23:30:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 24:00:00", "UTF-8"))
                .append("&")
                .append("roomName=")
                .append(URLEncoder.encode("테스트", "UTF-8"))
                .append("&")
                .append("userName=")
                .append(URLEncoder.encode("테스트 유저", "UTF-8"))
                .append("&")
                .append("repeat=0");
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/reservation")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),"{\"result\":\"OK\"}");
        jdbcTemplate.execute("delete from reservation " +
                "where beginTime='2017-12-07 23:30:00' and endTime='2017-12-08 00:00:00'" +
                "and roomsn=1");
    }

    @Test
    public void 같은_날짜로는_예약할_수_없어요() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();

        LocalDateTime beginDateTime = LocalDateTime.parse("2017-12-07 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse("2017-12-07 00:00:00", formatter);
        try {
            controller.doReservation(beginDateTime, endDateTime, "테스트", "테스트 유저", 1);
            Assert.fail("throw IllegalArgumentException 나옴");
        }catch (Exception ignore) {

        }
    }

    @Test
    public void 같은_날짜로는_예약할_수_없어요2() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("roomName=")
                .append(URLEncoder.encode("테스트", "UTF-8"))
                .append("&")
                .append("userName=")
                .append(URLEncoder.encode("테스트 유저", "UTF-8"))
                .append("&")
                .append("repeat=0");
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/reservation")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),"{\"result\":\"ERROR\", \"reason\":\"check beginTime == endTime\"}");
    }

    @Test
    public void 올바르지_않은_날짜값() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-0abc", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("roomName=")
                .append(URLEncoder.encode("테스트", "UTF-8"))
                .append("&")
                .append("userName=")
                .append(URLEncoder.encode("테스트 유저", "UTF-8"))
                .append("&")
                .append("repeat=0");
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/reservation")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),"{\"result\":\"ERROR\", \"reason\":\"date convert fail\"}");
    }


    @Test
    public void 예약_성공() throws Exception {
        StringBuffer content = new StringBuffer()
                .append("beginDate=")
                .append(URLEncoder.encode("2017-12-07 00:00:00", "UTF-8"))
                .append("&")
                .append("endDate=")
                .append(URLEncoder.encode("2017-12-07 20:00:00", "UTF-8"))
                .append("&")
                .append("roomName=")
                .append(URLEncoder.encode("테스트", "UTF-8"))
                .append("&")
                .append("userName=")
                .append(URLEncoder.encode("테스트 유저", "UTF-8"))
                .append("&")
                .append("repeat=0");
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/reservation")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .content(content.toString()))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),"{result:\"OK\"}");
        jdbcTemplate.execute("delete from reservation where beginTime < '2018-01-01'");
    }


    @Test
    public void 예약_인자_없음() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/reservation"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(),"{\"result\":\"FAIL\"}");
    }
}
