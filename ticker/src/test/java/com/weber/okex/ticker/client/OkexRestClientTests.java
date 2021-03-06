package com.weber.okex.ticker.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.weber.okex.ticker.client.domain.OkexDepthWarpper;
import com.weber.okex.ticker.client.domain.OkexTickerWarpper;
import com.weber.okex.ticker.client.domain.OkexTrade;
import com.weber.okex.ticker.client.impl.OkexRestClientImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OkexRestClientTests {

  @Value("${api.okex.apikey}")
  private String apiKey;

  @Value("${api.okex.secretkey}")
  private String secretKey;

  private OkexRestClient okexRestClient = new OkexRestClientImpl(null, null);

  private OkexRestClient okexRestClientPost = new OkexRestClientImpl(apiKey, secretKey);

  @Test
  public void contextLoads() {}

  @Test
  public void ticker(){
    OkexTickerWarpper okexTickerWarpper = okexRestClient.ticker("okb_usdt");
    System.out.println(okexTickerWarpper);
  }

  @Test
  public void depth(){
    OkexDepthWarpper okexDepthWarpper = okexRestClient.depth("okb_usdt", 2);
    okexDepthWarpper
        .getAsks()
        .forEach(
            ask -> {
              System.out.println(ask[0] + "," + ask[1]);
            });
    okexDepthWarpper
        .getBids()
        .forEach(
            bids -> {
              System.out.println(bids[0] + "," + bids[1]);
            });
  }

  public static void main(String[] args){
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    Iterator<Integer> iter = list.iterator();
    while (iter.hasNext()) {
      if(iter.next() % 2 == 0) {
        iter.remove();
      }
    }
    System.out.println(list);
  }

  @Test
  public void trades(){
    List<OkexTrade> list = okexRestClient.trades("okb_usdt", null);
    System.out.println(list);
    System.out.println("list.size()=" + list.size());
    List<OkexTrade> list2 = list.stream().filter(trade-> "buy".equals(trade.getType())).collect(toList());
    System.out.println("list2.size()=" + list2.size());
    Iterator<OkexTrade> iter = list.iterator();
    while (iter.hasNext()) {
      if("sell".equals(iter.next().getType())) {
        iter.remove();
      }
    }
    System.out.println("buy list.size()=" + list.size());

  }

  @Test
  public void kline(){
    List<BigDecimal[]> kline = okexRestClient.kline("okb_usdt", "1min", 50, null);
    kline.forEach(line -> System.out.println(line[0]));
  }

  @Test
  public void userInfo(){
    System.out.println(okexRestClientPost.userInfo());
  }
}
